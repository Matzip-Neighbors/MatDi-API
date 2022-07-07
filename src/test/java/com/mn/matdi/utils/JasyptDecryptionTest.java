package com.mn.matdi.utils;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptDecryptionTest {

    @Value("${jasypt.encryptor.password}")
    private String jasyptKey;

    @Autowired
    @Qualifier("jasyptStringEncryptor")
    private StringEncryptor jasypt;

    @Test
    void encrypt() {
        String result = jasypt.encrypt("1234");
        System.out.println(result);
    }


    @Test
    void decrypt() {

        String result = jasypt.decrypt("HPgx2TR4ZB/VjO04jNiipw==");
        System.out.println(result);
    }
}