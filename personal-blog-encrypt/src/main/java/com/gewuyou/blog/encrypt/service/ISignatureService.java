package com.gewuyou.blog.encrypt.service;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 签名服务
 *
 * @author gewuyou
 * @since 2024-04-14 下午9:16:27
 */
public interface ISignatureService {
    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return String 签名字符串
     */
    String sign(String data, PrivateKey privateKey);


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
    boolean verify(String data, String signature, PublicKey publicKey);
}
