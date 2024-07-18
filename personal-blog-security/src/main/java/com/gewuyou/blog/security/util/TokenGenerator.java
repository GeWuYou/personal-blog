package com.gewuyou.blog.security.util;

import com.gewuyou.blog.common.dto.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rabbitmq.client.ConnectionFactoryConfigurator.USERNAME;

/**
 * 令牌生成器
 *
 * @author gewuyou
 * @since 2024-07-18 下午6:25:05
 */
public class TokenGenerator {

    public static void main(String[] args) {
        var userDetailsDTO = UserDetailsDTO.builder()
                .userAuthId(1L)
                .userInfoId(1L)
                .username("admin")
                .roles(List.of("admin"))
                .build();
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, userDetailsDTO.getUsername());
        claims.put("userId", userDetailsDTO.getUserAuthId());
        claims.put("Authorities", userDetailsDTO.getAuthorities());
        // 创建token
        var token = Jwts.builder()
                .claims()
                .add(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                // 可以在未登陆前作为身份标识使用
                // .id(UUID.randomUUID().toString().replace("-", ""))
                // 设置颁发者
                .issuer("personal-blog")
                // 登录状态由redis缓存，所以不需要设置过期时间
                // .expiration(generateExpirationTime((Date) claims.get(JWT_CREATE_TIME), extendTime))
                .and()
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode("dtPhqyaNgQZlFqmTTG9IVKR7923QLmm3R7uAi3OKSeo=")))
                .compact();
        System.out.println(token);
    }
}
