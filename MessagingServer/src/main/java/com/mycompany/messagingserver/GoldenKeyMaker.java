package com.mycompany.messagingserver;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GoldenKeyMaker {
    //Create a randomly generated AlphaNumeric using SecureRandom String to use as the key
    public static String makeKey() {
        SecureRandom secureRandom = new SecureRandom();
        String key = new BigInteger(130, secureRandom).toString(32);
        return key;
    }
}
