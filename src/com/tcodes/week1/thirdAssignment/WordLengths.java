package com.tcodes.week1.thirdAssignment;

import edu.duke.FileResource;

public class WordLengths {
    public void countWordLengths(FileResource fileResource, int[] counts)
    {
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }
        for(String word: fileResource.words()){
            int wordLen = word.length();
            int c = 0;
            boolean alphabetic = Character.isLetter(word.charAt(word.length() - 1));
            if(Character.isLetter(word.charAt(0))){
                if(alphabetic) {
                    if(wordLen >= counts.length)
                        wordLen = counts.length-1;
                    counts[wordLen] += 1;
                    c = wordLen;
                }
                else {
                    if(wordLen > counts.length)
                        wordLen = counts.length;
                    counts[wordLen - 1] += 1;
                    c = wordLen-1;
                }
            }
            else if(alphabetic) {
                if(wordLen > counts.length)
                    wordLen = counts.length;
                counts[wordLen-1] += 1;
                c = wordLen-1;
            }
        }
    }

    public void testWordLengths()
    {
        FileResource fileResource = new FileResource();
        int[] counts = new int[12];
        countWordLengths(fileResource, counts);
        for (int i = 0; i < counts.length; i++) {
            System.out.println(i + " letter words are " + counts[i]);
        }
    }
}
