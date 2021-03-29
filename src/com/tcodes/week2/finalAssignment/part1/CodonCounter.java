package com.tcodes.week2.finalAssignment.part1;

import edu.duke.FileResource;

import java.util.HashMap;

public class CodonCounter {
    private HashMap<String, Integer> codonCounts;

    public CodonCounter()
    {
        codonCounts = new HashMap<>();
    }

    public void buildCodonMap(String dna, int startPos)
    {
        codonCounts.clear();

        for (int i = startPos; i < dna.length()-3; i += 3) {
            String frame = dna.substring(i, i+3);
            if(codonCounts.containsKey(frame)){
                codonCounts.put(frame, codonCounts.get(frame)+1);
            }
            else {
                codonCounts.put(frame, 1);
            }
        }
    }

    public String getMostCommonCodon()
    {
        int maxCount = 0;
        String mostCommon = "";

        for (String s : codonCounts.keySet()) {
            int count = codonCounts.get(s);
            if(maxCount < count){
                maxCount = count;
                mostCommon = s;
            }
        }

        return mostCommon;
    }

    public void printCodonCounts(int start, int end)
    {
        for (String s : codonCounts.keySet()) {
            int count = codonCounts.get(s);
            if(end >= count && start <= count){
                System.out.println(s + " occurs " + count + " times.");
            }
        }
    }

    public void tester()
    {
        FileResource fileResource = new FileResource();
        String dna = fileResource.asString().trim();
        for (int i = 0; i < 3; i++) {
            System.out.println("start at " + i + " -------");
            buildCodonMap(dna, i);
            String mostCommonCodon = getMostCommonCodon();
            System.out.println(mostCommonCodon + " occurs " + codonCounts.get(mostCommonCodon) + " times.");
            printCodonCounts(0, 1);
            System.out.println("*******************");
        }
    }
}
