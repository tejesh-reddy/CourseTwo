package com.tcodes.week2.secondAssignment;

import edu.duke.FileResource;

import java.util.ArrayList;

public class CharactersInPlay {
    private ArrayList<String> characterNames;
    private ArrayList<Integer> characterFrequencies;

    public CharactersInPlay()
    {
        characterFrequencies = new ArrayList<>();
        characterNames = new ArrayList<>();
    }

    public void update(String person)
    {
        person = person.trim();
        if(characterNames.contains(person))
        {
            int index = characterNames.indexOf(person);
            int freq = characterFrequencies.get(index);
            characterFrequencies.set(index, freq+1);
        }
        else
        {
            characterFrequencies.add(1);
            characterNames.add(person);
        }
    }

    public void findAllCharacters()
    {
        characterNames.clear();
        characterFrequencies.clear();

        FileResource fileResource = new FileResource();

        for (String line : fileResource.lines()) {
            if (line.contains(".")) {
                update(line.substring(0, line.indexOf('.')));
            }
        }
    }

    public void charactersWithNumParts(int num1, int num2)
    {
        // Gets all characters in file with num1 <= frequency <= num2
        findAllCharacters();
        int size = characterNames.size();
        int i = 0;
        while(i < size) {
            int frequency = characterFrequencies.get(i);
            if(frequency < num1 || frequency > num2){
                characterFrequencies.remove(i);
                characterNames.remove(i);
                i -= 1;
                size -= 1;
            }
            i++;
        }
    }

    public void tester()
    {
        charactersWithNumParts(2, 10);
        for (int i = 0; i < characterNames.size(); i++) {
            System.out.println("Name (" + characterNames.get(i) + ") has frequency: " + characterFrequencies.get(i));
        }
    }
}
