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

    URL getUrl() {
        return url;
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
            occurrences.addTime(word, (finish - start) / 1_000_000);
        });
        return occurrences;
    }

    private NumberOfCharacters obtainNumberOfCharacters() {
        long start = System.nanoTime();
        int count = page.getNumberOfCharacters();
        long finish = System.nanoTime();
        return new NumberOfCharacters(count, (finish - start) / 1_000_000);
    }

    /**
     * Need refactor
     * @return
     */

    private WordsToSentencesMap obtainWordsToSentencesMap() {
        Map<String, Set<String>> sentences = new HashMap<>();
        Map<String, Long> time = new HashMap<>();
        words.forEach(word -> {
            long start = System.nanoTime();
            sentences.put(word, page.getSentences(word));
            long finish = System.nanoTime();
            time.put(word, (finish-start) / 1_000_000);
        });
        return new WordsToSentencesMap(sentences, time);
    }

    private Page getCleanPage(URL url) throws ProcessingException {
        Page page = new Page();
        page.run(url);
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
