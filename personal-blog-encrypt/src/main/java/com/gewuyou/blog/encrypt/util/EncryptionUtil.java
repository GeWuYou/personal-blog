package com.gewuyou.blog.encrypt.util;

import com.gewuyou.blog.common.exception.GlobalException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

import static com.gewuyou.blog.common.enums.ResponseInformation.*;

/**
 * RSA加密工具
 *
 * @author gewuyou
 * @since 2024-04-14 下午8:35:30
 */
public class EncryptionUtil {
    private EncryptionUtil() {
    }
    /*
     *数据流程示例：
        1.客户端生成密钥对，私钥加密，公钥解密
        2.服务端接收客户端公钥，生成密钥对，私钥加密，公钥解密
        3.客户端使用服务端公钥加密数据，服务端使用服务端私钥解密数据
        4.服务端使用客户端公钥加密数据，客户端使用客户端私钥解密数据
        5.服务端使用客户端公钥签名数据，客户端使用服务端公钥验证签名
     */

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final KeyPair keyPair;

    static {
        try {
            keyPair = generateKeyPair();
        } catch (Exception e) {
            throw new GlobalException(FAILED_TO_INITIALIZE_THE_KEY_PAIR);
        }
    }

    private static KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        } catch (Exception e) {
            throw new GlobalException(FAILED_TO_INITIALIZE_THE_KEY_PAIR);
        }
        // Key size: 2048 bits
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static String getPublicKeyAsBase64() {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    public static String getPrivateKeyAsBase64() {
        return Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }

    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new GlobalException(ERROR_OCCURRED_WHILE_ENCRYPTING_THE_DATA);
        }
    }

    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            throw new GlobalException(ERROR_OCCURRED_WHILE_ENCRYPTING_THE_DATA);
        }
    }

    public static byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA", "BC");
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new GlobalException(FAILED_TO_GENERATE_THE_DIGITAL_SIGNATURE);
        }
    }

    public static boolean verify(byte[] data, byte[] signatureBytes, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA", "BC");
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            throw new GlobalException(FAILED_TO_VERIFY_THE_DIGITAL_SIGNATURE);
        }
    }
}
