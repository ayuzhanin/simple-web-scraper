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

    private void countWords(Holder holder, List<String> words) {
        for (String word : words) {
            int counter = holder.getText().countWordOccurrences(word);
            System.out.println(holder.getUrl().toString() + " word's '" + word + "': " + counter + " occurrence");
        }
    }

    private void countWordsOccurenceInTexts(List<Holder> holders, List<String> words) {
        boolean countWordsOccurence = Parser.FLAGS.get(new Flag(Flag.Values.COUNT_WORDS_OCCURENCE));
        if (countWordsOccurence) {
            printVerbose("Start counting words...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            holders.forEach(holder -> countWords(holder, words));
            long endTime = verbose ? startTime - System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    private void countCharacters(Holder holder) {
        int counter = holder.getText().countCharacters();
        System.out.println(holder.getUrl().toString() + " contains " + counter + " characters");
    }

    private void countCharacters(List<Holder> holders) {
        boolean countCharactersOfPage = Parser.FLAGS.get(new Flag(Flag.Values.COUNT_CHARACTERS_OF_PAGE));
        if (countCharactersOfPage) {
            printVerbose("Start counting characters...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            holders.forEach(this::countCharacters);
            long endTime = verbose ? startTime - System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    private void printWordAndSentences(String word, Set<String> sentences) {
        System.out.println("Sentences with word " + word);
        sentences.forEach(System.out::println);
    }

    private void extractAllSentencesWithSeveralWords(Holder holder, List<String> words) {
        Map<String, Set<String>> wordSentencesDict = holder.getText().extractAllSentencesWithSeveralWords(words);
        System.out.println("Processing :" + holder.getUrl().toString());
        Set<String> keys = wordSentencesDict.keySet();
        keys.forEach(key -> printWordAndSentences(key, wordSentencesDict.get(key)));
    }

    private void extractAllSentencesWithSeveralWords(List<Holder> holders, List<String> words) {
        boolean extractSentencesWithWord = Parser.FLAGS.get(new Flag(Flag.Values.EXTRACT_SENTENCES_WITH_WORD));
        if (extractSentencesWithWord) {
            printVerbose("Start extracting sentences...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            holders.forEach(holder -> extractAllSentencesWithSeveralWords(holder, words));
            long endTime = verbose ? startTime - System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    public void run(List<Holder> holders, List<String> words) {
        verbose = Parser.FLAGS.get(new Flag(Flag.Values.VERBOSE));
        countWordsOccurenceInTexts(holders, words);
        countCharacters(holders);
        extractAllSentencesWithSeveralWords(holders, words);
    }
}
