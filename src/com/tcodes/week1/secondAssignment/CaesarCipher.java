package com.tcodes.week1.secondAssignment;

import edu.duke.*;

public class CaesarCipher {

    public String encryptTwoKeys(String input, int key1, int key2)
    {
        StringBuilder firstEncryptInput = new StringBuilder();
        StringBuilder secondEncryptInput = new StringBuilder();

        for(int i = 0; i < input.length(); i += 2){
            firstEncryptInput.append(input.charAt(i));
        }

        for(int i = 1; i < input.length(); i += 2){
            secondEncryptInput.append(input.charAt(i));
        }

        String firstEncrypt = encrypt(firstEncryptInput.toString(), key1);
        String secondEncrypt = encrypt(secondEncryptInput.toString(), key2);

        return interleave(firstEncrypt, secondEncrypt);
    }

    private String interleave(String first, String second)
    {
        // Interleaves two strings by sequentially taking one char from each string
        StringBuilder stringBuilder = new StringBuilder();
        int firstLen = first.length();
        int secondLen = second.length();
        for (int i = 0; i < firstLen+secondLen; i++) {
            if(i%2 == 0){
                stringBuilder.append(first.charAt(i/2));
            }
            else{
                stringBuilder.append(second.charAt(i/2));
            }
        }

        return stringBuilder.toString();
    }

    public String encrypt(String input, int key) {
        StringBuilder encrypted = new StringBuilder(input);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = alphabet.substring(key)+
        alphabet.substring(0,key);
        for(int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(Character.toUpperCase(currChar));
            if(idx != -1){
                char newChar = shiftedAlphabet.charAt(idx);
                if(Character.isLowerCase(currChar)){
                    encrypted.setCharAt(i, Character.toLowerCase(newChar));
                }
                else{
                    encrypted.setCharAt(i, newChar);
                }
            }
        }
        return encrypted.toString();
    }
    public void testCaesar() {
        int key = 17;
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encrypt(message, key);
        System.out.println(encrypted);
        String decrypted = encrypt(encrypted, 26-key);
        System.out.println(decrypted);
    }

    public void testTwoKeys() {
        int key1 = 17;
        int key2 = 9;
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encryptTwoKeys(message, key1, key2);
        System.out.println(encrypted);
        String decrypted = encryptTwoKeys(encrypted, 26-key1, 26-key2);
        System.out.println(decrypted);
    }
}

