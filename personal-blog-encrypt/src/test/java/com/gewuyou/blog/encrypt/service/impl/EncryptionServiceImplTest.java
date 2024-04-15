package com.gewuyou.blog.encrypt.service.impl;

import com.gewuyou.blog.encrypt.service.IEncryptionService;
import com.gewuyou.blog.encrypt.util.EncryptionUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.PublicKey;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EncryptionServiceImplTest {
    @Resource
    IEncryptionService encryptionService;
    @Test
    void getKey() {
        System.out.println(EncryptionUtil.getPublicKeyAsBase64());
        System.out.println(EncryptionUtil.getPrivateKeyAsBase64());
    }

    // @Test
    // void encrypt() {
    //     String string = encryptionService.encryptData("你好，世界！", "123456");
    //     System.out.println(string);
    //     decrypt(string.getBytes());
    // }
    //
    // @Test
    // void decrypt(byte[] encryptedText) {
    //     System.out.println(Arrays.toString(encryptionService.decrypt(encryptedText)));
    // }
}