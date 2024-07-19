package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.client.ServerClient;
import com.gewuyou.blog.admin.mapper.UserAuthMapper;
import com.gewuyou.blog.admin.mapper.UserRoleMapper;
import com.gewuyou.blog.admin.service.IUserAuthService;
import com.gewuyou.blog.admin.strategy.context.LoginStrategyContext;
import com.gewuyou.blog.common.constant.CommonConstant;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.enums.LoginTypeEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.enums.RoleEnum;
import com.gewuyou.blog.common.enums.UserAreaTypeEnum;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.UserAuth;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.model.UserRole;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.CommonUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.LoginVO;
import com.gewuyou.blog.common.vo.QQLoginVO;
import com.gewuyou.blog.common.vo.RegisterVO;
import com.gewuyou.blog.security.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.RabbitMQConstant.EMAIL_EXCHANGE;
import static com.gewuyou.blog.common.constant.RedisConstant.USER_AREA;
import static com.gewuyou.blog.common.constant.RedisConstant.VISITOR_AREA;

/**
 * <p>
 * 用户认证信息表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
@Slf4j
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements IUserAuthService {

    private final UserRoleMapper userRoleMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Redis服务
     */
    private final IRedisService redisService;

    /**
     * 随机生成器
     */
    private final Random random = new Random();

    private final ServerClient serverClient;

    private final LoginStrategyContext loginStrategyContext;
    private final JwtService jwtService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    @Autowired
    public UserAuthServiceImpl(
            IRedisService redisService,
            UserRoleMapper userRoleMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ServerClient serverClient,
            LoginStrategyContext loginStrategyContext,
            JwtService jwtService, RabbitTemplate rabbitTemplate, @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.redisService = redisService;
        this.userRoleMapper = userRoleMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.serverClient = serverClient;
        this.loginStrategyContext = loginStrategyContext;
        this.jwtService = jwtService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 获取用户区域分布
     *
     * @param conditionVO 条件
     * @return 用户区域分布
     */
    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO conditionVO) {
        List<UserAreaDTO> userAreaDTOS = List.of();
        try {
            // 根据类型获取用户区域分布
            switch (Objects.requireNonNull(UserAreaTypeEnum.getUserAreaType(conditionVO.getType()))) {
                case USER_TYPE:
                    // 从redis中获取用户区域分布
                    userAreaDTOS = redisService.getObject(USER_AREA, new TypeReference<>() {
                    });
                    return userAreaDTOS;
                case VISITOR_TYPE:
                    Map<String, Object> visitorArea = redisService.hGetAll(VISITOR_AREA);
                    if (Objects.nonNull(visitorArea)) {
                        userAreaDTOS = visitorArea
                                .entrySet()
                                .stream()
                                .map(item ->
                                        UserAreaDTO.builder()
                                                .name(item.getKey())
                                                .value(Long.valueOf(item.getValue().toString()))
                                                .build()
                                ).collect(Collectors.toList());
                    }
                default:
                    log.error("未知的用户区域类型");
                    break;
            }
        } catch (Exception e) {
            log.error("获取用户区域分布失败", e);
        }
        return userAreaDTOS;
    }

    /**
     * 发送邮件
     *
     * @param email     邮件地址
     * @param sessionId 会话ID
     */
    @Override
    public void sendCodeToEmail(String email, String sessionId) {
        // 先判断邮箱是否合法
        // 设置redis缓存key，用于验证邮箱
        String redisKey = "email:" + email + ":sessionId:" + sessionId;
        // 先判断redis中是否存在该key
        if (Boolean.TRUE.equals(redisService.hasKey(redisKey))) {
            // 获取指定键的剩余过期时间
            long expireTime = Optional
                    .ofNullable(redisService
                            .getExpire(redisKey))
                    .orElse(0L);
            // 判断过期时间
            if (expireTime > 240) {
                throw new GlobalException(ResponseInformation.TOO_MANY_REQUESTS);
            }
            // 如果过期时间小于四分钟则允许重新发送邮件，删除原先的键
            redisService.delete(redisKey);
        }
        var code = CommonUtil.getRandomCode(6);
        var emailDTO = EmailDTO
                .builder()
                .email(email)
                .subject("验证码")
                .template("common.html")
                .contentMap(Map.of("content", "您的验证码是：" + code + "\n验证码有效期为五分钟\n如果不是您获取的验证码，请忽略该邮件!"));
        try {
            // 发送邮件
            rabbitTemplate
                    .convertAndSend(EMAIL_EXCHANGE, "*",
                            new Message(
                                    objectMapper.writeValueAsBytes(emailDTO), new MessageProperties()));
        } catch (JsonProcessingException e) {
            log.error("发送邮件失败", e);
            throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
        }
        // 将验证码存入redis
        redisService.set(redisKey, code, 300, TimeUnit.SECONDS);
    }

    /**
     * 验证邮箱并注册
     *
     * @param registerVO 注册数据传输类
     * @param sessionId  会话ID
     */
    @Override
    @Transactional
    public void verifyEmailAndRegister(RegisterVO registerVO, String sessionId) {
        Optional<UserAuth> optionalUser = baseMapper.selectByEmail(registerVO.getEmail());
        // 如果邮箱已注册，则抛出异常
        if (optionalUser.isPresent()) {
            throw new GlobalException(ResponseInformation.USER_EMAIL_HAS_BEEN_REGISTERED);
        }
        // 验证验证码
        if (!verifyCode(registerVO.getEmail(), registerVO.getVerifyCode(), sessionId, false)) {
            throw new GlobalException(ResponseInformation.VERIFY_CODE_ERROR);
        }
        // 创建用户数据对象
        UserInfo userInfo = UserInfo.builder()
                .nickName(CommonConstant.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(serverClient.getWebsiteConfig().getData().getUserAvatar())
                .build();
        serverClient.userInfoInsert(userInfo);
        // 创建用户角色对象
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);

        // 创建用户认证信息对象
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(registerVO.getUsername())
                .password(bCryptPasswordEncoder.encode(registerVO.getPassword()))
                .email(registerVO.getEmail())
                .createTime(LocalDateTime.now())
                .build();
        baseMapper.insert(userAuth);
    }


    /**
     * 验证验证码
     *
     * @param email      邮箱
     * @param verifyCode 验证码
     * @param sessionId  会话ID
     * @param isRegister (邮箱)是否注册
     * @return 是否验证成功
     */
    public boolean verifyCode(String email, String verifyCode, String sessionId, boolean isRegister) {
        // 获取redis缓存key
        String redisKey = "email:" + email + ":sessionId:" + sessionId + ":isRegister:" + isRegister;
        // 判断redis中是否存在该key
        if (!Boolean.TRUE.equals(redisService.hasKey(redisKey))) {
            throw new GlobalException(ResponseInformation.VERIFY_CODE_EXPIRED);
        }
        // 获取验证码
        String code = (String) redisService.get(redisKey);
        // 取出来后需要判断是否为null即刚好过期
        if (code == null) {
            throw new GlobalException(ResponseInformation.VERIFY_CODE_EXPIRED);
        }
        // 验证验证码
        if (!verifyCode.equals(code)) {
            return false;
        }
        // 验证码验证成功，删除redis缓存
        redisService.delete(redisKey);
        return true;
    }

    /**
     * 重置密码
     *
     * @param email    邮箱
     * @param password 密码
     */
    @Override
    public void resetPassword(String email, String password) {
        Optional<UserAuth> optionalUser = baseMapper.selectByEmail(email);
        // 如果邮箱未注册，则抛出异常
        if (optionalUser.isEmpty()) {
            throw new GlobalException(ResponseInformation.USER_EMAIL_HAS_BEEN_REGISTERED);
        }
        baseMapper.updatePasswordByEmail(email, bCryptPasswordEncoder.encode(password));
    }

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public boolean checkUserName(String username) {
        return baseMapper.selectByUsername(username).isPresent();
    }

    /**
     * 获取用户列表
     *
     * @param conditionVO 条件
     * @return 用户列表
     */
    @Override
    public PageResultDTO<UserAdminDTO> listUsers(ConditionVO conditionVO) {
        Integer count = baseMapper.countUsers(conditionVO);
        if (count == 0) {
            return new PageResultDTO<>();
        }
        Page<UserAdminDTO> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        List<UserAdminDTO> userAdminDTOS = baseMapper.listUsers(page, conditionVO).getRecords();
        return new PageResultDTO<>(userAdminDTOS, count.longValue());
    }

    /**
     * 登录
     *
     * @param loginVO 登录信息
     * @return 用户信息
     */
    @Override
    public UserInfoDTO usernameOrEmailPasswordLogin(LoginVO loginVO) {
        return loginStrategyContext.executeLoginStrategy(loginVO, LoginTypeEnum.USERNAME_OR_EMAIL);
    }

    /**
     * 退出登录
     *
     * @return 是否退出成功
     */
    @Override
    public ResponseInformation logout() {
        jwtService.deleteLoginUser(UserUtil.getUserDetailsDTO().getUserAuthId());
        jwtService.deleteToken(UserUtil.getUserDetailsDTO().getUserAuthId());
        return ResponseInformation.LOGOUT_SUCCESS;
    }

    /**
     * QQ登录
     *
     * @param qqLoginVO QQ登录信息
     * @return 用户信息
     */
    @Override
    public UserInfoDTO qqLogin(QQLoginVO qqLoginVO) {
        return loginStrategyContext.executeLoginStrategy(qqLoginVO, LoginTypeEnum.QQ);
    }

}
