package com.example.wordcounter.services;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordCounterService {

    private final List<String> listOfWords = new ArrayList<>();

    public String addListOfWords(String words){

        String[] splitWords = words.split("\\s");

        /*.
         * check, filter  and group all the non-alphabetic and alphabetic words
         * if you don't need to know how many wasn't added then disregard using the map and instead return the collection into a list with filter and checking is isAlphabeticOnly
         *  List<String> alphabeticWords = Arrays.stream(splitWords).filter(this::isAlphabeticOnly).collect(Collectors.toList());
         */
        Map<Boolean, List<String>> alphabeticWords = Arrays.stream(splitWords)
                .collect(Collectors.groupingBy(this::isAlphabeticOnly));

        /*
         * add the alphabetic words to the listOfWords collection
         * get both collections of words for printing
         */

        List<String> alphabetic = alphabeticWords.get(Boolean.TRUE);

        List<String> nonAlphabetic = alphabeticWords.get(Boolean.FALSE);

        int alphaTotal = alphabetic != null ? alphabetic.size() : 0;
        int nonAlphaTotal = nonAlphabetic != null ? nonAlphabetic.size() : 0;

        if(alphaTotal > 0){
            listOfWords.addAll(alphabetic);
        }
        return String.format("%d Word(s) added successfully and %d not added",alphaTotal,nonAlphaTotal);
    }

    /**
     * Count how many times a word has been added
     * @param wordToCount the word to count
     * @return the total of the matched words and total in collection
     */
    public String countWords(String wordToCount){
        if (new StringTokenizer(wordToCount).countTokens() > 1){
            return "Only a single word can be checked at a time. This api does not support multiple word checks";
        }
       long wordCounts =  listOfWords.stream().filter(word -> word.equalsIgnoreCase(wordToCount)).count();
       String matchCount = String.format("There are %x match(s) with the word %s ", wordCounts, wordToCount);
       String totalMatch = String.format("and there are %x words in total", listOfWords.size());

       return matchCount.concat(totalMatch);
    }

    /**
     * check if a word is either alphabetic or not
     * @param word the word to check
     * @return this returns true if the word is alphabetic and false if it is not
     */
    private boolean isAlphabeticOnly(String word){
        String translatedWords = translateToEnglish(word);
        return !translatedWords.equals("") && translatedWords.matches("^[a-zA-Z]*$");
    }

    /**
     * This method will call an external api to help translate the word from a different language to english
     * @param word the word to translate
     * @return the translated word
     */
    private String translateToEnglish(String word){
        //TODO call external class to translate the word to english
        return word;
    }
}
