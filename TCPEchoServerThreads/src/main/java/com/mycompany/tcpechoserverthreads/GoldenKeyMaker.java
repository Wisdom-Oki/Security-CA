package com.mycompany.tcpechoserverthreads;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GoldenKeyMaker {
    public static String makeKey() {
        SecureRandom secureRandom = new SecureRandom();
        String key = new BigInteger(130, secureRandom).toString(32);
        return key;
    }
}

