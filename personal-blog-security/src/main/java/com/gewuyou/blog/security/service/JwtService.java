package com.gewuyou.blog.security.service;

import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.service.IRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.gewuyou.blog.common.constant.RedisConstant.LOGIN_USER;
import static com.gewuyou.blog.common.constant.RedisConstant.TOKEN;

/**
 * Jwt服务
 *
 * @author GeWuYou
 * @version 0.1.0
 * @since 2023/7/2 17:31
 */
@Component
@Slf4j
public class JwtService {
    /**
     * JWT密钥
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 令牌过期时间（单位：秒）
     */
    @Value("${jwt.expirationTime}")
    private long tokenExpiration;

    @Value("${jwt.refreshTokenTime}")
    private long refreshTokenTime;


    /**
     * 令牌签发者
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * 用户信息-id
     */
    private static final String USER_ID = "userId";
    /**
     * 用户信息-用户名
     */
    private static final String USERNAME = "userName";

    private final IRedisService redisService;

    @Autowired
    public JwtService(IRedisService redisService) {
        this.redisService = redisService;
    }


    /**
     * 对令牌进行加密
     *
     * @param token 令牌
     * @return java.lang.String
     * @apiNote
     * @since 2023/7/20 15:04
     */
    public String encryptToken(String token) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(token.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new GlobalException(ResponseInformation.ENCRYPTION_FAILED);
        }
    }

    /**
     * 根据用户信息生成token
     *
     * @param userDetailsDTO 用户信息DTO
     * @return java.lang.String
     * @apiNote
     * @since 2023/7/2 19:45
     */

    public String generateToken(UserDetailsDTO userDetailsDTO) {
        cacheUserDetailsDTO(userDetailsDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, userDetailsDTO.getUsername());
        claims.put(USER_ID, userDetailsDTO.getUserAuthId());
        claims.put("Authorities", userDetailsDTO.getAuthorities());
        // 创建token
        return createToken(claims);
    }


    /**
     * 删除登录token
     *
     * @param userAuthId 用户认证id
     */
    public void deleteToken(Long userAuthId) {
        redisService.hDel(TOKEN, userAuthId.toString());
    }

    /**
     * 从数据声明中生成令牌
     *
     * @param claims     数据键值对
     * @return java.lang.String
     * @apiNote
     * @since 2023/7/2 19:50
     */
    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims()
                .add(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                // 可以在未登陆前作为身份标识使用
                // .id(UUID.randomUUID().toString().replace("-", ""))
                // 设置颁发者
                .issuer(issuer)
                // 登录状态由redis缓存，所以不需要设置过期时间
                // .expiration(generateExpirationTime((Date) claims.get(JWT_CREATE_TIME), extendTime))
                .and()
                .signWith(getKey())
                .compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return io.jsonwebtoken.Claims
     * @apiNote
     * @since 2023/7/2 20:02
     */
    public Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("解析token失败：{}", e.getMessage());
            throw new GlobalException(ResponseInformation.RESPONSE_INFORMATION);
        }
        return claims;
    }

    /**
     * 根据定义的密钥生成加密的key
     *
     * @return java.security.Key
     * @apiNote
     * @since 2023/7/2 19:53
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    /**
     * 判断token是否失效
     *
     * @param token 令牌
     * @return boolean 未失效
     * @apiNote 失效返回false反之为true
     * @since 2023/7/2 20:32
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            // 验证颁发者
            return claims.getIssuer().equals(issuer);
        } catch (Exception e) {
            log.error("验证token失败：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 判断token是否过期
     *
     * @param token 令牌
     * @return boolean 未失效
     * @apiNote 过期返回true反之false
     * @since 2023/7/2 20:33
     */
    public boolean isTokenExpired(String token) {
        var userDetailsDTO = getUserDetailsDTOFromToken(token);
        LocalDateTime expireTime = userDetailsDTO.getExpireTime();
        LocalDateTime currentTime = LocalDateTime.now();
        // 如果不设置过期时间，则永不过期
        if (expireTime == null) {
            return false;
        }
        return currentTime.isAfter(expireTime);
    }


    /**
     * 从token中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsername(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            throw new IllegalArgumentException();
        }
        return getValue(claims, USERNAME);
    }

    /**
     * 从token中获取用户Id
     *
     * @param token 令牌
     * @return java.lang.String 用户id
     * @apiNote
     * @since 2023/7/17 14:19
     */
    public String getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            throw new IllegalArgumentException();
        }
        return getValue(claims, USER_ID);
    }


    /**
     * 从claims中获取值
     *
     * @param claims 数据声明
     * @param key    键
     * @return java.lang.String 值
     * @apiNote
     * @since 2023/7/17 21:43
     */
    private String getValue(Claims claims, String key) {
        Object o = claims.get(key);
        if (o != null) {
            return o.toString();
        } else {
            return null;
        }
    }

    /**
     * 判断token是否快过期，如果快过期则刷新token
     *
     * @param userDetailsDTO 用户信息DTO
     */
    public void refreshToken(UserDetailsDTO userDetailsDTO) {
        LocalDateTime expireTime = userDetailsDTO.getExpireTime();
        LocalDateTime currentTime = LocalDateTime.now();
        if (Duration.between(currentTime, expireTime).getSeconds() <= refreshTokenTime) {
            cacheUserDetailsDTO(userDetailsDTO);
        }
    }

    /**
     * 缓存用户信息DTO
     *
     * @param userDetailsDTO 用户信息DTO
     */
    public void cacheUserDetailsDTO(UserDetailsDTO userDetailsDTO) {
        LocalDateTime currentTime = LocalDateTime.now();
        userDetailsDTO.setExpireTime(currentTime.plusSeconds(tokenExpiration));
        String userId = userDetailsDTO.getUserAuthId().toString();
        redisService.hSet(LOGIN_USER, userId, userDetailsDTO, tokenExpiration);
    }

    /**
     * 删除登录用户缓存
     *
     * @param userAuthId 用户认证id
     */
    public void deleteLoginUser(Long userAuthId) {
        redisService.hDel(LOGIN_USER, String.valueOf(userAuthId));
    }

    /**
     * 从缓存中获取用户信息DTO
     *
     * @param token 令牌
     * @return UserDetailsDTO 用户信息DTO
     */
    public UserDetailsDTO getUserDetailsDTOFromToken(String token) {
        if (StringUtils.hasText(token) && !token.equals("null")) {
            String userAuthId = getUserId(token);
            return (UserDetailsDTO) redisService.hGet(LOGIN_USER, userAuthId);
        }
        return null;
    }
}
