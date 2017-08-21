package com.vigenerecipher;

class Vigenere {

    static String encryptString(String plaintext, String keyword) {
        plaintext = plaintext.toUpperCase();
        keyword = keyword.toUpperCase();
        String key = generateKey(plaintext, keyword);

        StringBuilder builder = new StringBuilder(plaintext.length());
        for (int i = 0; i < plaintext.length(); i++) {
            builder.append(getCipherChar(plaintext.charAt(i), key.charAt(i)));
        }
        return builder.toString();
    }

//    static String decryptString(String cipherText) {
//
//    }

    static String decryptString(String cipherText, String keyword) {
        cipherText = cipherText.toUpperCase();
        keyword = keyword.toUpperCase();
        String key = generateKey(cipherText, keyword);

        StringBuilder builder = new StringBuilder(cipherText.length());
        for (int i = 0; i < cipherText.length(); i++) {
            builder.append(getPlainChar(cipherText.charAt(i), key.charAt(i)));
        }
        return builder.toString();
    }

    private static char getPlainChar(char cipherChar, char keyChar) {
        if (toInteger(cipherChar) - toInteger(keyChar) >= 0) {
            return toChar((toInteger(cipherChar) - toInteger(keyChar)) % 26);
        } else {
            return (char) (26 + toInteger(cipherChar) - toInteger(keyChar) + 65);
        }
    }

    private static char getCipherChar(char plainChar, char keyChar) {
        return toChar((toInteger(plainChar) + toInteger(keyChar)) % 26);
    }

    private static int toInteger(char c) { return c - 65; }
    private static char toChar(int i) { return (char) (i + 65); }

    private static String generateKey(String plaintext, String keyword) {
        StringBuilder builder = new StringBuilder(plaintext.length() + keyword.length() - 1);
        while(builder.length() < plaintext.length()) {
            builder.append(keyword);
        }
        builder.setLength(plaintext.length());
        return builder.toString();
    }

}
