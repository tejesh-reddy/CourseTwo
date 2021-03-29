package com.tcodes.week2.finalAssignment;

import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedWords;
    private int replaceWordCount;
    private Random myRandom;
    private String[] categories;

    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";

    public GladLibMap(){
        myMap = new HashMap<>();
        categories = new String[]{"adjective", "noun", "color", "country", "name",
                "animal", "timeframe", "verb", "fruit"};
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }

    public GladLibMap(String source){
        myMap = new HashMap<>();
        categories = new String[]{"adjective", "noun", "color", "country", "name",
                "animal", "timeframe", "verb", "fruit"};
        initializeFromSource(source);
        myRandom = new Random();
    }

    private void initializeFromSource(String source) {
        usedWords = new ArrayList<>();

        for (String category :
                categories) {
            myMap.put(category, readIt(source + "/" + category + ".txt"));
        }
    }

    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    private String getSubstitute(String label) {
        if(myMap.containsKey(label)){
            return randomFrom(myMap.get(label));
        }
        else if(label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }

        return "**UNKNOWN**";
    }

    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);

        if (first == -1 || last == -1){
            return w;
        }

        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        String finalWord = prefix+sub+suffix;

        if(usedWords.contains(finalWord)) {
            replaceWordCount++;
            return processWord(w);
        }
        else {
            usedWords.add(finalWord);
        }

        return finalWord;
    }

    private void printOut(String s, int lineWidth){
        int charsWritten = 0;

        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
        System.out.println();
    }

    private String fromTemplate(String source){
        String story = "";

        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);

            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);

            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }

        return story;
    }

    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }

    public void makeStory(){

        usedWords.clear();
        replaceWordCount = 0;

        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);

        String wordsUsed = "Number of words replaced: " + replaceWordCount;
        printOut(wordsUsed, 60);
    }

    public int totalWordsInMap()
    {
        int totalWords = 0;

        for (String word : myMap.keySet()) {
            totalWords += myMap.get(word).size();
        }

        return totalWords;
    }

    public int totalWordsConsidered()
    {
        int totalWords = 0;

        for (String category : categories) {
            totalWords += myMap.get(category).size();
        }

        return  totalWords;
    }

}

