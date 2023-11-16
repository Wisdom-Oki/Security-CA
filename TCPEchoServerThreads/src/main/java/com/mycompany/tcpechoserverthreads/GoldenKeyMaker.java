package com.mycompany.tcpechoserverthreads;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GoldenKeyMaker {
    public static SecretKey makeKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keyGen.init(256, random);
        return keyGen.generateKey();
    }
}
