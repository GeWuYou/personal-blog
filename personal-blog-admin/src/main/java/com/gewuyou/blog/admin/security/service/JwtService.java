package com.gewuyou.blog.admin.security.service;

import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.enums.TokenType;
import com.gewuyou.blog.common.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * 访问令牌过期时间（单位：毫秒）
     */
    @Value("${jwt.expirationTime.access}")
    private long accessTokenExpiration;

    /**
     * 刷新令牌过期时间（单位：毫秒）
     */
    @Value("${jwt.expirationTime.refresh}")
    private long refreshTokenExpirationTime;

    /**
     * 访问令牌签发者
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

    /**
     * Jwt创建时间字段
     */
    private static final String JWT_CREATE_TIME = "Jwt_creat_time";

    /**
     * token类型
     */
    private static final String TOKEN_TYPE = "TokenType";

    /**
     * token类型与过期时间映射
     */
    private final Map<TokenType, Long> tokenTypeExpirationDateMapping;

    private JwtService() {
        tokenTypeExpirationDateMapping = Map.of(
                TokenType.AccessToken, accessTokenExpiration,
                TokenType.RefreshToken, refreshTokenExpirationTime
        );
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
     * @param userDetails 用户信息
     * @param tokenType   令牌类型
     * @return java.lang.String
     * @apiNote
     * @since 2023/7/2 19:45
     */

    public String generateToken(UserDetails userDetails, TokenType tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, userDetails.getUsername());
        // claims.put("Authorities",userDetails.getAuthorities());
        claims.put(TOKEN_TYPE, tokenType.getValue());
        // 创建jwt创建时间
        claims.put(JWT_CREATE_TIME, new Date());
        // 创建token
        return createToken(claims, tokenTypeExpirationDateMapping.get(tokenType));
    }

    /**
     * 从数据声明中生成令牌
     *
     * @param claims     数据键值对
     * @param extendTime 延长时间
     * @return java.lang.String
     * @apiNote
     * @since 2023/7/2 19:50
     */
    public String createToken(Map<String, Object> claims, long extendTime) {
        return Jwts.builder()
                .claims()
                .add(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                // 可以在未登陆前作为身份标识使用
                // .id(UUID.randomUUID().toString().replace("-", ""))
                // 设置颁发者
                .issuer(issuer)
                .issuedAt((Date) claims.get(JWT_CREATE_TIME))
                .notBefore(generateExpirationTime((Date) claims.get(JWT_CREATE_TIME), extendTime))
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
            throw new GlobalException(ResponseInformation.INSUFFICIENT_PERMISSIONS);
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
     * 生成token的失效时间
     *
     * @param startTime  开始时间
     * @param extendTime 延长时间
     * @return java.util.Date token到期时间
     * @apiNote
     * @since 2023/7/2 19:44
     */
    private Date generateExpirationTime(Date startTime, long extendTime) {
        return new Date(startTime.getTime() + extendTime);
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
        Date expiredDate = getExpiredDate(token);
        return expiredDate.before(new Date());
    }


    /**
     * 从token中解析过期时间
     *
     * @param token 令牌
     * @return java.util.Date 过期时间
     * @apiNote
     * @since 2023/7/2 20:37
     */
    private Date getExpiredDate(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            throw new IllegalArgumentException();
        }
        return claims.getExpiration();
    }


    /**
     * 刷新token，重新构建jwt
     *
     * @param token 令牌
     * @return java.lang.String
     * @apiNote
     * @since 2023/7/2 20:46
     */
    public String refreshToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            throw new IllegalArgumentException();
        }
        claims.put(JWT_CREATE_TIME, new Date());
        return createToken(claims, tokenTypeExpirationDateMapping.get(getValue(claims, TOKEN_TYPE)));
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
     * 从token中获取token类型
     *
     * @param token 令牌
     * @return java.lang.String
     * @apiNote
     * @since 2023/7/17 21:43
     */
    public String getTokenType(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            throw new IllegalArgumentException();
        }
        return getValue(claims, TOKEN_TYPE);
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
}
