package com.example.wordcounter.services;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WordCounterServiceTest {

    @InjectMocks
    @Spy
    private WordCounterService wordCounterService;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void wordCounterServiceTestSuccess() {
        String sentenceOne = "John Doe and Mary had a little lamb and John was very scared of the little lamb the lamb became very naughty";
        String sentenceTwo = "John Doe had no choice but to run away to a far far away place";
        String result1 = wordCounterService.addListOfWords(sentenceOne);
        String result2 = wordCounterService.addListOfWords(sentenceTwo);
        Mockito.verify(wordCounterService, Mockito.times(2)).addListOfWords(Mockito.anyString());
        assertEquals(result1, "22 Word(s) added successfully and 0 not added");
        assertEquals(result2, "15 Word(s) added successfully and 0 not added");

        assertEquals("There are 2 match(s) with the word The and there are 25 words in total", wordCounterService.countWords("The") );
        assertEquals("There are 3 match(s) with the word John and there are 25 words in total", wordCounterService.countWords("John") );

        Mockito.verify(wordCounterService, Mockito.times(2)).addListOfWords(Mockito.anyString());
    }

    @Test
    public void wordCounterServiceTestWithSomeMatches() {
        String sentenceOne = "Becau$e p0ple dont wan7 to listen when s90ken to";
        String result1 = wordCounterService.addListOfWords(sentenceOne);
        Mockito.verify(wordCounterService, Mockito.times(1)).addListOfWords(Mockito.anyString());
        assertEquals(result1, "5 Word(s) added successfully and 4 not added");

        assertEquals(wordCounterService.countWords("to"), "There are 2 match(s) with the word to and there are 5 words in total" );
        assertEquals(wordCounterService.countWords("dont"), "There are 1 match(s) with the word dont and there are 5 words in total");
        Mockito.verify(wordCounterService, Mockito.times(2)).countWords(Mockito.anyString());
    }

    @Test
    public void wordCounterServiceWithNoMatch() {
        String sentenceOne = "Becau$e p0ple don7 wan7 7o li$ten wh3n s90ken 7o";
        String result1 = wordCounterService.addListOfWords(sentenceOne);
        Mockito.verify(wordCounterService, Mockito.times(1)).addListOfWords(Mockito.anyString());
        assertEquals(result1, "0 Word(s) added successfully and 9 not added");

        assertEquals(wordCounterService.countWords("to"), "There are 0 match(s) with the word to and there are 0 words in total");
        assertEquals(wordCounterService.countWords("dont"), "There are 0 match(s) with the word dont and there are 0 words in total");
        assertEquals(wordCounterService.countWords("li$ten"), "There are 0 match(s) with the word li$ten and there are 0 words in total");
        Mockito.verify(wordCounterService, Mockito.times(3)).countWords(Mockito.anyString());
    }

    @Test
    public void wordCounterServiceTestWithMoreThanOneWord(){
        String sentenceOne = "This is more than one word";
        String result1 = wordCounterService.addListOfWords(sentenceOne);
        Mockito.verify(wordCounterService, Mockito.times(1)).addListOfWords(Mockito.anyString());
        assertEquals(result1, "6 Word(s) added successfully and 0 not added");

        String msg = "Only a single word can be checked at a time. This api does not support multiple word checks";
        assertEquals(wordCounterService.countWords("This is more"), msg);
    }
}
