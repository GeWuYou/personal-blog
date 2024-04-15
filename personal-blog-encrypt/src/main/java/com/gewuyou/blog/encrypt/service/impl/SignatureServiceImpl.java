package com.gewuyou.blog.encrypt.service.impl;

import com.gewuyou.blog.encrypt.service.ISignatureService;
import com.gewuyou.blog.encrypt.util.EncryptionUtil;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * 签名服务实现类
 *
 * @author gewuyou
 * @since 2024-04-14 下午9:18:54
 */
public class SignatureServiceImpl implements ISignatureService {
    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return String 签名字符串
     */
    @Override
    public String sign(String data, PrivateKey privateKey) {
        byte[] signature = EncryptionUtil.sign(data.getBytes(StandardCharsets.UTF_8), privateKey);
        return Base64.getEncoder().encodeToString(signature);
    }

    /**
     * 验签
     *
     * @param data      待验签数据
     * @param signature 签名字符串
     * @param publicKey 公钥
     * @return boolean 验签结果
     * @apiNote
     * @since 2024/4/14 下午9:20
     */
    @Override
    public boolean verify(String data, String signature, PublicKey publicKey) {
        byte[] decodedSignature = Base64.getDecoder().decode(signature);
        return EncryptionUtil.verify(data.getBytes(StandardCharsets.UTF_8), decodedSignature, publicKey);
    }
}
