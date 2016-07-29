package com.hireright.job.junior.app.core;

import com.hireright.job.junior.app.core.result.NumberOfCharacters;
import com.hireright.job.junior.app.core.result.WordOccurrences;
import com.hireright.job.junior.app.core.result.WordsToSentencesMap;
import com.hireright.job.junior.app.exception.ProcessingException;

import java.net.URL;
import java.util.*;


class TaskManager {

    private WordOccurrences wordOccurrences;
    private NumberOfCharacters numberOfCharacters;
    private WordsToSentencesMap wordsToSentences;

    private URL url;
    private Page page;
    private List<String> words;

    private void setUrl(URL url) {
        this.url = url;
    }

    private void setPage(Page page) {
        this.page = page;
    }

    private void setWords(List<String> words) {
        this.words = words;
    }

    public URL getUrl() {
        return url;
    }

    public Page getPage() {
        return page;
    }

    public List<String> getWords() {
        return words;
    }

    WordOccurrences getWordOccurrences() {
        return wordOccurrences;
    }

    NumberOfCharacters getNumberOfCharacters() {
        return numberOfCharacters;
    }

    WordsToSentencesMap getWordsToSentences() {
        return wordsToSentences;
    }

    private WordOccurrences obtainWordOccurrences() {
        WordOccurrences occurrences = new WordOccurrences();
        words.forEach(word -> {
            long start = System.nanoTime();
            occurrences.update(word, page.getNumberOfOccurrences(word));
            long finish = System.nanoTime();
            occurrences.addTime(finish - start);
        });
        return occurrences;
    }

    private NumberOfCharacters obtainNumberOfCharacters() {
        long start = System.nanoTime();
        int count = page.getNumberOfCharacters();
        long finish = System.nanoTime();
        return new NumberOfCharacters(count, finish - start);
    }

    private WordsToSentencesMap obtainWordsToSentencesMap() {
        long start = System.nanoTime();
        Map<String, Set<String>> map = page.getSentences(words);
        long finish = System.nanoTime();
        return new WordsToSentencesMap(map, finish - start);
    }

    private Page getCleanPage(URL url) throws ProcessingException {
        Page page = new Page();
        page.clean(url);
        return page;
    }

    void run(URL url, List<String> words, Map<Task, Boolean> tasks) throws ProcessingException {
        setUrl(url);
        setPage(getCleanPage(url));
        setWords(words);

        if (tasks.get(new Task(Task.Flag.COUNT_GIVEN_WORDS_OCCURRENCES))) {
            wordOccurrences = obtainWordOccurrences();
        }

        if (tasks.get(new Task(Task.Flag.COUNT_CHARACTERS_IN_PAGE))) {
            numberOfCharacters = obtainNumberOfCharacters();
        }

        if (tasks.get(new Task(Task.Flag.EXTRACT_SENTENCES_WITH_GIVEN_WORD))) {
            wordsToSentences = obtainWordsToSentencesMap();
        }
    }
}
