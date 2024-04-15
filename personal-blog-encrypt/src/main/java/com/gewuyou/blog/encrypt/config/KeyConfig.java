package com.gewuyou.blog.encrypt.config;

import com.gewuyou.blog.common.exception.GlobalException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 秘钥配置
 *
 * @author gewuyou
 * @since 2024-04-14 下午9:37:37
 */
@ConfigurationProperties(prefix = "encryption")
@Configuration
@Data
public class KeyConfig {
    @Value("${encryption.public-key-string}")
    private String publicKeyString;

    @Value("${encryption.private-key-string}")
    private String privateKeyString;

    public PublicKey getPublicKey() {
        try {
            byte[] publicBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new GlobalException(500, "Failed to load public key", e);
        }
    }

    public PrivateKey getPrivateKey() {
        try {
            byte[] privateBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new GlobalException(500, "Failed to load private key", e);
        }
    }
}
