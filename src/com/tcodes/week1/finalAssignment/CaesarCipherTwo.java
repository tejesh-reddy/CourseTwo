package com.tcodes.week1.finalAssignment;

import java.awt.*;

public class CaesarCipherTwo {
    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int key1;
    private int key2;

    public CaesarCipherTwo(int key1, int key2)
    {
        this.key1 = key1;
        this.key2 = key2;

        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet1 = alphabet.substring(key1) + alphabet.substring(0, key1);
        shiftedAlphabet2 = alphabet.substring(key2) + alphabet.substring(0, key2);
    }

    public String encrypt(String input)
    {
        OOCaesarCipher caesarCipher1 = new OOCaesarCipher(key1);
        OOCaesarCipher caesarCipher2 = new OOCaesarCipher(key2);

        String firstHalf = halfOfString(input, 0);
        String secondHalf = halfOfString(input, 1);

        String firstHalfEncrypted = caesarCipher1.encrypt(firstHalf);
        String secondHalfEncrypted = caesarCipher2.encrypt(secondHalf);

        return interleave(firstHalfEncrypted, secondHalfEncrypted);
    }

    private String halfOfString(String stringToSplit, int startIndex)
    {
        StringBuilder stringBuilder = new StringBuilder();
        // Splits the given string by taking alternate characters starting from startIndex
        for (int i = startIndex; i < stringToSplit.length(); i += 2) {
            stringBuilder.append(stringToSplit.charAt(i));
        }

        return stringBuilder.toString();
    }

    private String interleave(String first, String second) {
        // Interleaves two strings by sequentially taking one char from each string
        StringBuilder stringBuilder = new StringBuilder();
        int firstLen = first.length();
        int secondLen = second.length();
        for (int i = 0; i < firstLen + secondLen; i++) {
            if (i % 2 == 0) {
                stringBuilder.append(first.charAt(i / 2));
            } else {
                stringBuilder.append(second.charAt(i / 2));
            }
        }

        return stringBuilder.toString();
    }

    public String decrypt(String input)
    {
        int dkey1 = 26-key1;
        int dkey2 = 26-key2;

        CaesarCipherTwo caesarCipherTwo = new CaesarCipherTwo(dkey1, dkey2);

        return caesarCipherTwo.encrypt(input);
    }
}
