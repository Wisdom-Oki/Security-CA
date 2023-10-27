package com.mycompany.tcpechoserver;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Encyrption {

    private String message;
    private String newMessage;

    public Encyrption() {
        this.message = "";
        this.newMessage = "";
        // Generate key
        try{
          SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        }catch(NoSuchAlgorithmException e){
            System.out.println("error"+ e);
        }
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
