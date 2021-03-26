package com.tcodes.week1.firstAssignment;

public class WordPlay {

    public String emphasize(String phrase, char ch)
    {
        StringBuilder stringBuilder = new StringBuilder(phrase);
        for (int i = 0; i < phrase.length(); i++) {
            if(ch == Character.toLowerCase(phrase.charAt(i))){
                if(i%2 == 0)
                    stringBuilder.setCharAt(i, '+');
                else
                    stringBuilder.setCharAt(i, '*');
            }
        }

        return stringBuilder.toString();
    }

    public String replaceAllVowels(String phrase, char ch)
    {
        StringBuilder stringBuilder = new StringBuilder(phrase);
        for(int i = 0; i < phrase.length(); i++)
        {
            if(isVowel(phrase.charAt(i))){
                stringBuilder.setCharAt(i, ch);
            }
        }

        return stringBuilder.toString();
    }

    public boolean isVowel(char ch)
    {
        String vowels = "aeiou";
        for(int i = 0; i < vowels.length(); i++){
            if(Character.toLowerCase(ch) == vowels.charAt(i))
                return true;
        }

        return false;
    }
}
