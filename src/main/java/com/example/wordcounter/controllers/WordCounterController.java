package com.example.wordcounter.controllers;

import com.example.wordcounter.services.WordCounterService;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/word-counter")
public class WordCounterController {
    @Autowired
    private WordCounterService wordCounterService;

    @ApiOperation(value = "Allows users to add single or multiple words as required", produces = "application/json")
    @PostMapping("/addWords")
    private ResponseEntity<String> addWords(@ApiParam(name = "words", value = "The words to be added, single or multiple words") @NotNull @RequestParam String words){
        String result = wordCounterService.addListOfWords(words);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Allows users to get the total words in collection or repeated words", produces = "application/json")
    @GetMapping("/countWord")
    private ResponseEntity<String> getWordCount(@ApiParam(name = "words", value = "The word to count") @NotNull @RequestParam String words){
        String result = wordCounterService.countWords(words);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
