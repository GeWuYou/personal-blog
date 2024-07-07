package com.gewuyou.blog.common.utils;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * 秘钥生成
 *
 * @author gewuyou
 * @since 2024-04-16 下午10:32:01
 */
@Slf4j
public class KeyGenerator {
    private static String getTokens() {
        // 生成一个256位的秘钥
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        // 将秘钥编码为Base64字符串
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        log.info("Generating Token Key(Base64): {}", getTokens());
    }
}
