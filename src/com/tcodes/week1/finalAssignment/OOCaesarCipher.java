package com.tcodes.week1.finalAssignment;

import com.tcodes.week1.secondAssignment.CaesarCipher;

public class OOCaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;

    public OOCaesarCipher(int key)
    {
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
        mainKey = key;
    }

    public String encrypt(String input)
    {
        StringBuilder encrypted = new StringBuilder(input);
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

    public String decrypt(String encrypted)
    {
        int dkey = 26 - mainKey;
        OOCaesarCipher caesarCipher = new OOCaesarCipher(dkey);


        return caesarCipher.encrypt(encrypted);
    }

}
