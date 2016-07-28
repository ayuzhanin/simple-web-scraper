package com.hireright.job.junior.app.core;

import com.hireright.job.junior.app.exception.ProcessingException;

import java.net.URL;
import java.util.*;


class TaskPerformer {

    private List<Map<String, Integer>> numberOfOccurrences;
    private List<Integer> numberOfCharacters;
    private List<Map<String, Set<String>>> sentences;

    List<Map<String, Integer>> getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    List<Integer> getNumberOfCharacters() {
        return numberOfCharacters;
    }

    List<Map<String, Set<String>>> getSentences() {
        return sentences;
    }

    private Map<String, Integer> obtainNumberOfOccurrences(Text text, List<String> words) {
        Map<String, Integer> occurrences = new HashMap<>();
        for (String word : words) {
            int count = text.getNumberOfOccurrences(word);
            occurrences.put(word, count);
        }
        return occurrences;
    }

    private List<Map<String, Integer>> obtainNumberOfOccurrences(List<Text> texts, List<String> words) {
        List<Map<String, Integer>> occurrences = new ArrayList<>(texts.size());
        texts.forEach(text -> occurrences.add(obtainNumberOfOccurrences(text, words)));
        return occurrences;
    }

    private int obtainNumberOfCharacters(Text text) {
        return text.getNumberOfCharacters();
    }

    private List<Integer> obtainNumberOfCharacters(List<Text> texts) {
        List<Integer> numbers = new ArrayList<>(texts.size());
        texts.forEach(text -> numbers.add(obtainNumberOfCharacters(text)));
        return numbers;
    }

    private Map<String, Set<String>> obtainSentences(Text text, List<String> words) {
        return text.getSentences(words);
    }

    private List<Map<String, Set<String>>> obtainSentences(List<Text> texts, List<String> words) {
        List<Map<String, Set<String>>> sentencesList = new ArrayList<>(texts.size());
        texts.forEach(text -> sentencesList.add(obtainSentences(text, words)));
        return sentencesList;
    }

    private List<Text> getTexts (List<URL> urls) throws ProcessingException {
        List<Text> texts = new ArrayList<>(urls.size());
        for (URL url : urls) {
            Text text = new Text();
            text.obtainCleanPageContent(url);
            texts.add(text);
        }
        return texts;
    }

    void run(List<URL> urls, List<String> words, Map<Task, Boolean> tasks) throws ProcessingException {
        List<Text> texts = getTexts(urls);

        if (tasks.get(new Task(Task.Flag.COUNT_GIVEN_WORDS_OCCURRENCES))) {
            numberOfOccurrences = obtainNumberOfOccurrences(texts, words);
        }

        if (tasks.get(new Task(Task.Flag.COUNT_CHARACTERS_IN_PAGE))) {
            numberOfCharacters = obtainNumberOfCharacters(texts);
        }

        if (tasks.get(new Task(Task.Flag.EXTRACT_SENTENCES_WITH_GIVEN_WORD))) {
            sentences = obtainSentences(texts, words);
        }
    }
}
