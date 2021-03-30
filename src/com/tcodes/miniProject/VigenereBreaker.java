package com.tcodes.miniProject;

import java.io.File;
import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < message.length()-whichSlice; i += totalSlices) {
            int j = i+whichSlice;
            stringBuilder.append(message.charAt(j));
        }

        return stringBuilder.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];

        CaesarCracker caesarCracker = new CaesarCracker(mostCommon);
        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            int dkey = caesarCracker.getKey(slice);
            key[i] = dkey;
        }

        return key;
    }

    public void breakVigenere () {
        FileResource fileResource = new FileResource();
        String encrypted = fileResource.asString();

        DirectoryResource dictionaryResources = new DirectoryResource();
        HashMap<String, HashSet<String>> langToDictionary = new HashMap<>();

        for (File f : dictionaryResources.selectedFiles()) {
            FileResource dictResource = new FileResource(f);
            HashSet<String> dictionary = readDictionary(dictResource);
            String lang = f.getName();

            langToDictionary.put(lang, dictionary);
        }

        breakForAllLangs(encrypted, langToDictionary);;
    }

    public HashSet<String> readDictionary(FileResource fr)
    {
        HashSet<String> dictionary = new HashSet<>();

        for(String line : fr.lines())
            dictionary.add(line.toLowerCase());

        return dictionary;
    }

    public int countWords(String message, HashSet<String> dictionary)
    {
        int wordCount = 0;

        for(String word : message.split("\\W+")){
            if(dictionary.contains(word))
                wordCount++;
        }

        return wordCount;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary, char mostCommonChar)
    {
        int maxWords = 0;
        String bestDecrypt = new String();

        for (int i = 1; i < 100; i++) {
            int[] key = tryKeyLength(encrypted, i, mostCommonChar);
            VigenereCipher vigenereCipher = new VigenereCipher(key);
            String message = vigenereCipher.decrypt(encrypted);

            int cnt = countWords(message, dictionary);
            if(cnt > maxWords){
                maxWords = cnt;
                bestDecrypt = message;
            }
        }

        return bestDecrypt;
    }

    char mostCommonCharIn(HashSet<String> words)
    {
        char mostCommonChar = 'e';
        int maxCnt = 1;
        HashMap<Character, Integer> charCount = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase();
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                if(charCount.containsKey(c)){
                    int cnt = charCount.get(c)+1;
                    charCount.put(c, cnt);

                    if(cnt > maxCnt) {
                        mostCommonChar = c;
                        maxCnt = cnt;
                    }
                }
                else {
                    charCount.put(c, 1);
                }
            }
        }

        return mostCommonChar;
    }

    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> langToDict)
    {
        String bestDecrypt = new String();
        int maxWords = 0;
        String bestDecryptLang = new String();

        for (String lang : langToDict.keySet()){
            HashSet<String> dictionary = langToDict.get(lang);
            char mostCommonChar = mostCommonCharIn(dictionary);
            String bestDecryptInLang = breakForLanguage(encrypted, dictionary, mostCommonChar);

            int cnt = countWords(bestDecryptInLang, dictionary);
            if(maxWords < cnt){
                bestDecrypt = bestDecryptInLang;
                maxWords = cnt;
                bestDecryptLang = lang;
            }
        }

        System.out.println("language: " + bestDecryptLang);
        System.out.println(bestDecrypt);
    }
}
