package com.example.muneer.g18_async.helpers;

/**
 * Created by Muneer on 10-11-2017.
 */

public class EncryptDecrypt {

    public static String encrypt(String text) {
        int s = 18;
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                char ch = (char) (((int) text.charAt(i) +
                        s - 65) % 26 + 65);
                result.append(ch);
            } else if (Character.isLowerCase(text.charAt(i))) {
                char ch = (char) (((int) text.charAt(i) +
                        s - 97) % 26 + 97);
                result.append(ch);
            } else {
                char ch = text.charAt(i);
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text) {
        int s = 26 - 18;
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                char ch = (char) (((int) text.charAt(i) +
                        s - 65) % 26 + 65);
                result.append(ch);
            } else if (Character.isLowerCase(text.charAt(i))) {
                char ch = (char) (((int) text.charAt(i) +
                        s - 97) % 26 + 97);
                result.append(ch);
            } else {
                char ch = text.charAt(i);
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(decrypt("uipdmmap nibpqui"));
    }
}
