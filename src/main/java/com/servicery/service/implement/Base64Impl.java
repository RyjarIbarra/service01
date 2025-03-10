package com.servicery.service.implement;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Impl {

    public static String encode(String text) {
        if (text == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.ISO_8859_1));
    }

    public static String decode(String encodedText) {
        if (encodedText == null) {
            return null;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
            return new String(decodedBytes, StandardCharsets.ISO_8859_1);
        } catch (IllegalArgumentException e) {
            // Handle invalid Base64 input
            System.err.println("Invalid Base64 input: " + e.getMessage());
            return null;
        }
    }

}
