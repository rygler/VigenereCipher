package com.vigenerecipher;

class Vigenere {
    private static double[] frequencies = { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228,
            2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507,
            1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150,
            1.974, 0.074 }; //frequencies of a-z in english

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

    private static int toInteger(char c) {
        return c - 65;
    }

    private static char toChar(int i) {
        return (char) (i + 65);
    }

    private static String generateKey(String plaintext, String keyword) {
        StringBuilder builder = new StringBuilder(plaintext.length() + keyword.length() - 1);
        while (builder.length() < plaintext.length()) {
            builder.append(keyword);
        }
        builder.setLength(plaintext.length());
        return builder.toString();
    }

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

    static String decryptString(String cipherText) {
        String best = null;
        int maxKeyLength = 18;
        for (int keyLength = 1; keyLength <= maxKeyLength; keyLength++) {
            StringBuilder[] interleaves = splitInterleaves(cipherText, keyLength);
            String[] decryptedInterleaves = new String[keyLength];

            for (int i = 0; i < keyLength; i++) {
                decryptedInterleaves[i] = decryptCaeser(interleaves[i].toString());
            }
            StringBuilder combined = new StringBuilder();
            for (int i = 0; i < cipherText.length(); i++) {
                combined.append(decryptedInterleaves[i % keyLength].charAt(i / keyLength));// Combine interleaves
            }
            best = lowerScoreOf(best, combined.toString()); //minimize between password lengths
        }
        return best;
    }

    private static String decryptCaeser(String input) {
        String best = null;
        for (int i = 'A'; i <= 'Z'; i++) {
            best = lowerScoreOf(best, encryptString(input, Character.toString((char) i)));
        }
        return best;
    }

    private static String lowerScoreOf(String a, String b) {
        return a == null || (b != null && score(a) > score(b)) ? b : a;
    }

    private static double score(String input) { // compute sum squares of deviations from expected frequency
        int[] letters = new int[26];   //compute histogram of A-Z frequency:
        for (char c : input.toUpperCase().replaceAll("[^A-Z]", "").toCharArray()) {
            letters[c - 'A']++;
        }

        double sumDSquared = 0.0;
        for (int i = 0; i < frequencies.length; i++) {
            sumDSquared += Math.pow((100.0 * letters[i] / input.length() - frequencies[i]), 2);
        }
        return sumDSquared;

    }

    private static StringBuilder[] splitInterleaves(String cipherText, int keyLength) {
        StringBuilder[] interleaves = new StringBuilder[keyLength];
        for (int i = 0; i < interleaves.length; i++) {
            interleaves[i] = new StringBuilder();
        }
        for (int j = 0; j < cipherText.length(); j++) {
            interleaves[j % keyLength].append(cipherText.charAt(j));
        }
        return interleaves;
    }
}
