package com.tcodes.week1.thirdAssignment;

import com.tcodes.week1.secondAssignment.CaesarCipher;
import edu.duke.FileResource;

public class CaesarBreaker {
    public String decryptTwoKeys(String encrypted)
    {
        CaesarCipher caesarCipher = new CaesarCipher();

        String firstHalf = halfOfString(encrypted, 0);
        String secondHalf = halfOfString(encrypted, 1);

        String firstHalfDecrypted = decrypt(firstHalf);
        String secondHalfDecrypted = decrypt(secondHalf);

        return CaesarCipher.interleave(firstHalfDecrypted, secondHalfDecrypted);
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

    public String decrypt(String encrypted)
    {
        CaesarCipher caesarCipher = new CaesarCipher();
        int key = getKey(encrypted);


        return caesarCipher.encrypt(encrypted, 26-key);
    }

    public int getKey(String encrypted)
    {
        int[] frequency = countLetters(encrypted);
        int maximumIndex = maxIndex(frequency);
        int key = maximumIndex - 4;

        if(maximumIndex < 4){
            key = 21 - key;
        }

        return key;
    }public int[] countLetters(String sentence)
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];


        for (int i = 0; i < sentence.length(); i++) {
            char currChar = sentence.charAt(i);
            if(Character.isLetter(currChar))
                counts[alphabet.indexOf(Character.toLowerCase(currChar))]++;
        }

        return counts;
    }

    public int maxIndex(int[] frequency)
    {
        int maxId = 0;

        for (int i = 0; i < frequency.length; i++) {
            if(frequency[maxId] < frequency[i])
                maxId = i;
        }

        return maxId;
    }

    public void testDecrypt()
    {
        FileResource fileResource = new FileResource();
        String message = fileResource.asString();
        int key = 19;

        System.out.println("Message is:");
        System.out.println(message);
        System.out.println("Encrypting with key: " + key);

        CaesarCipher caesarCipher = new CaesarCipher();
        String encrypted = caesarCipher.encrypt(message, key);

        System.out.println("Encrypted Message is: ");
        System.out.println(encrypted);

        CaesarBreaker caesarBreaker = new CaesarBreaker();
        String decrypted = caesarBreaker.decrypt(encrypted);

        System.out.println("Decrypted Message is: ");
        System.out.println(decrypted);
    }
}
