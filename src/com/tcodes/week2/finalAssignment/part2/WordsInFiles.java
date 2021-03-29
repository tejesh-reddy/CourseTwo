package com.tcodes.week2.finalAssignment.part2;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class WordsInFiles {
    private HashMap<String, ArrayList<String>> wordsToFiles;

    public WordsInFiles()
    {
        wordsToFiles = new HashMap<>();
    }

    private void addWordsFromFile(File file)
    {
        FileResource fr = new FileResource(file);
        String filename = file.getName();
        for(String s : fr.words()){
            if(wordsToFiles.containsKey(s)){
                ArrayList<String> word = wordsToFiles.get(s);
                if(!word.contains(filename))
                    word.add(filename);
            }
            else {
                wordsToFiles.put(s, new ArrayList<String>());
                wordsToFiles.get(s).add(filename);
            }
        }
    }

    public void buildWordFileMap()
    {
        wordsToFiles.clear();

        DirectoryResource dr = new DirectoryResource();
        for (File f :  dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }

    public void printFilesIn(String word)
    {
        if(wordsToFiles.containsKey(word)){
            for (String s : wordsToFiles.get(word)) {
                System.out.println(s);
            }
        }
        else {
            System.out.println(word + " doesn't exist in any file.");
        }
    }

    public int maxNumber()
    {
        int result = 0;

        for(String word : wordsToFiles.keySet())
        {
            int fileListSize = wordsToFiles.get(word).size();
            if(result < fileListSize)
                result = fileListSize;
        }

        return result;
    }

    public ArrayList<String> wordsInNumFiles(int number)
    {
        ArrayList<String> result = new ArrayList<>();

        for(String word : wordsToFiles.keySet())
        {
            int fileListSize = wordsToFiles.get(word).size();
            if(number == fileListSize)
                result.add(word);
        }

        return  result;
    }

    public void tester()
    {
        buildWordFileMap();
        int maxNum = maxNumber();
        System.out.println(maxNum);
        ArrayList<String> words = wordsInNumFiles(maxNum);
        System.out.println(words);
        for (String word : words) {
            printFilesIn(word);
            System.out.println();
        }
    }
}
