package com.gewuyou.blog.encrypt.service.impl;

import com.gewuyou.blog.encrypt.service.IEncryptionService;
import com.gewuyou.blog.encrypt.util.EncryptionUtil;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * 加密服务类
 *
 * @author gewuyou
 * @since 2024-04-14 下午7:17:15
 */
@Service
public class EncryptionServiceImpl implements IEncryptionService {

    /**
     * 加密数据
     *
     * @param encryptData 待加密数据
     * @param publicKey   公钥
     * @return java.lang.String 加密后的数据
     * @apiNote
     * @since 2024/4/14 下午8:57
     */
    @Override
    public String encryptData(String encryptData, PublicKey publicKey) {
        byte[] encryptedData = EncryptionUtil.encrypt(encryptData.getBytes(StandardCharsets.UTF_8), publicKey);
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 解密数据
     *
     * @param encryptData 待解密数据
     * @return java.lang.String 解密后的数据
     * @apiNote
     * @since 2024/4/14 下午8:58
     */
    @Override
    public String decryptData(String encryptData, PrivateKey privateKey) {
        byte[] decodedData = Base64.getDecoder().decode(encryptData);
        byte[] decryptedData = EncryptionUtil.decrypt(decodedData, privateKey);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
