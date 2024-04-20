package com.gewuyou.blog.admin.service.impl;

import com.gewuyou.blog.admin.mapper.UserMapper;
import com.gewuyou.blog.admin.service.IAdminService;
import com.gewuyou.blog.common.dto.RegisterDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.User;
import com.gewuyou.blog.common.utils.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 管理服务实现类
 *
 * @author gewuyou
 * @since 2024-04-19 下午8:15:37
 */
@Service
public class AdminServiceImpl implements IAdminService {

    private final UserMapper userMapper;

    private final EmailSender emailSender;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Redis字符串操作
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 随机生成器
     */
    private final Random random = new Random();

    @Autowired
    public AdminServiceImpl(
            UserMapper userMapper,
            EmailSender emailSender,
            StringRedisTemplate stringRedisTemplate,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userMapper = userMapper;
        this.emailSender = emailSender;
        this.stringRedisTemplate = stringRedisTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * 发送邮件
     *
     * @param email      邮件地址
     * @param sessionId  会话ID
     * @param isRegister (邮箱)是否注册
     * @return 是否发送成功
     */
    @Override
    public boolean sendEmail(String email, String sessionId, boolean isRegister) {
        // 判断邮箱是否存在
        // 如果邮箱已注册，但是数据库中没有该邮箱，则抛出异常
        Optional<User> optionalUser = userMapper.selectByEmail(email);
        if (isRegister && optionalUser.isEmpty()) {
            throw new GlobalException(ResponseInformation.USER_EMAIL_NOT_REGISTERED);
        }
        // 如果邮箱未注册，但是数据库中存在该邮箱，则抛出异常
        if (!isRegister && optionalUser.isPresent()) {
            throw new GlobalException(ResponseInformation.USER_EMAIL_HAS_BEEN_REGISTERED);
        }
        // 设置redis缓存key，用于验证邮箱
        String redisKey = "email:" + email + ":sessionId:" + sessionId + ":isRegister:" + isRegister;
        // 先判断redis中是否存在该key
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(redisKey))) {
            // 获取指定键的剩余过期时间
            long expireTime = Optional
                    .ofNullable(stringRedisTemplate
                            .getExpire(redisKey, TimeUnit.SECONDS))
                    .orElse(0L);
            // 判断过期时间
            if (expireTime > 240) {
                throw new GlobalException(ResponseInformation.TOO_MANY_REQUESTS);
            }
            // 如果过期时间小于四分钟则允许重新发送邮件，删除原先的键
            stringRedisTemplate.delete(redisKey);
        }
        // 生成对应的验证码
        int code = random.nextInt(899999) + 100000;
        // 发送邮件
        boolean successfullySent = emailSender.sendSimpleEmail(
                email,
                "验证码",
                "您的验证码是：" + code + "\n验证码有效期为五分钟\n如果不是您获取的验证码，请忽略该邮件!");
        if (successfullySent) {
            // 将验证码存入redis
            stringRedisTemplate.opsForValue().set(redisKey, String.valueOf(code), 300, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证邮箱并注册
     *
     * @param registerDTO 注册数据传输类
     * @param sessionId   会话ID
     * @return 是否注册成功
     */
    @Override
    public boolean verifyEmailAndRegister(RegisterDTO registerDTO, String sessionId) {
        // 验证验证码
        if (!verifyCode(registerDTO.getEmail(), registerDTO.getVerifyCode(), sessionId, false)) {
            return false;
        }
        // 创建用户对象
        User user = User.builder()
                .userName(registerDTO.getUsername())
                .passWord(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .createTime(LocalDateTime.now())
                .build();
        // 插入用户
        userMapper.insert(user);
        return true;
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
        if (!Boolean.TRUE.equals(stringRedisTemplate.hasKey(redisKey))) {
            throw new GlobalException(ResponseInformation.VERIFY_CODE_EXPIRED);
        }
        // 获取验证码
        String code = stringRedisTemplate.opsForValue().get(redisKey);
        // 取出来后需要判断是否为null即刚好过期
        if (code == null) {
            throw new GlobalException(ResponseInformation.VERIFY_CODE_EXPIRED);
        }
        // 验证验证码
        if (!verifyCode.equals(code)) {
            return false;
        }
        // 验证码验证成功，删除redis缓存
        stringRedisTemplate.delete(redisKey);
        return true;
    }

    /**
     * 重置密码
     *
     * @param email    邮箱
     * @param password 密码
     * @return 是否重置成功
     */
    @Override
    public boolean resetPassword(String email, String password) {
        try {
            userMapper.updatePasswordByEmail(email, bCryptPasswordEncoder.encode(password));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public boolean checkUserName(String username) {
        return userMapper.selectByUserName(username).isPresent();
    }

}
