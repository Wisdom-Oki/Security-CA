package com.mycompany.tcpechoserver;

public class Encyrption {
    //You can create a smaller alphabet to decrypt here: dcode.fr/deranged-alphabet-generator
    //You can test the result here: dcode.fr/atbash-cipher
    private static final char[] ALPHABET = "POjl9sri6LWy8X5cZA7HQkUvKaJCqbFRp14tm2TYfwE0gzdISNMDxGuh3VoenB".toCharArray();
    private static final char[] REVERSED_ALPHABET = new StringBuilder("POjl9sri6LWy8X5cZA7HQkUvKaJCqbFRp14tm2TYfwE0gzdISNMDxGuh3VoenB").reverse().toString().toCharArray();

    private String message;
    private String newMessage;

    public Encyrption() {
        this.message = "";
        this.newMessage = "";
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
