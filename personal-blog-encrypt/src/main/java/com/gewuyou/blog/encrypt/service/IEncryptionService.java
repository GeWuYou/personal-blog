package com.gewuyou.blog.encrypt.service;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Ras加密服务
 *
 * @author gewuyou
 * @since 2024-04-14 下午7:14:56
 */
public interface IEncryptionService {
    /**
     * 加密数据
     *
     * @param encryptData 待加密数据
     * @param publicKey 公钥
     *
     * @return java.lang.String 加密后的数据
     * @apiNote
     * @since 2024/4/14 下午8:57
     */
    String encryptData(String encryptData, PublicKey publicKey);


    /**
     * 解密数据
     *
     * @param  encryptData 加密数据
     * @param privateKey 私钥
     * @return java.lang.String 解密后的数据
     * @apiNote
     * @since 2024/4/14 下午8:58
     */
    String decryptData(String  encryptData, PrivateKey privateKey);
}
