package com.hireright.job.juniorcandidate.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextProcessor {

    private boolean verbose;

    private void printVerbose(String message) {
        if (verbose) {
            System.out.println(message);
        }
    }

    private void countWords(TextAndURLHolder textAndURLHolder, List<String> words) {
        for (String word : words) {
            int counter = textAndURLHolder.getText().countWordOccurrences(word);
            System.out.println(textAndURLHolder.getUrl().toString() + " word '" + word + "': " + counter + " occurrence");
        }
    }

    private void countWordsOccurenceInTexts(List<TextAndURLHolder> textAndURLHolders, List<String> words) {
        boolean countWordsOccurence = Parser.FLAGS.get(new Flag(Flag.Values.COUNT_WORDS_OCCURENCES));
        if (countWordsOccurence) {
            printVerbose("Counting words...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            textAndURLHolders.forEach(holder -> countWords(holder, words));
            long endTime = verbose ? System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    private void countCharacters(TextAndURLHolder textAndURLHolder) {
        int counter = textAndURLHolder.getText().countCharacters();
        System.out.println(textAndURLHolder.getUrl().toString() + " contains " + counter + " characters");
    }

    private void countCharacters(List<TextAndURLHolder> textAndURLHolders) {
        boolean countCharactersOfPage = Parser.FLAGS.get(new Flag(Flag.Values.COUNT_CHARACTERS_OF_PAGE));
        if (countCharactersOfPage) {
            printVerbose("Counting characters...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            textAndURLHolders.forEach(this::countCharacters);
            long endTime = verbose ? System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    private void printWordAndSentences(String word, Set<String> sentences) {
        System.out.println("Sentences with word '" + word + "':");
        //sentences.forEach(System.out::println);
        int index = 1;
        for (String sentence : sentences){
            System.out.println(index + ". " + sentence);
            index++;
        }
    }

    private void extractAllSentencesWithSeveralWords(TextAndURLHolder textAndURLHolder, List<String> words) {
        Map<String, Set<String>> wordSentencesDict = textAndURLHolder.getText().extractAllSentencesWithSeveralWords(words);
        Set<String> keys = wordSentencesDict.keySet();
        keys.forEach(key -> printWordAndSentences(key, wordSentencesDict.get(key)));
    }

    private void extractAllSentencesWithSeveralWords(List<TextAndURLHolder> textAndURLHolders, List<String> words) {
        boolean extractSentencesWithWord = Parser.FLAGS.get(new Flag(Flag.Values.EXTRACT_SENTENCES_WITH_WORD));
        if (extractSentencesWithWord) {
            printVerbose("Extracting sentences...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            textAndURLHolders.forEach(holder -> extractAllSentencesWithSeveralWords(holder, words));
            long endTime = verbose ? System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    public void run(List<TextAndURLHolder> textAndURLHolders, List<String> words) {
        verbose = Parser.FLAGS.get(new Flag(Flag.Values.VERBOSE));
        countWordsOccurenceInTexts(textAndURLHolders, words);
        countCharacters(textAndURLHolders);
        extractAllSentencesWithSeveralWords(textAndURLHolders, words);
    }
}
