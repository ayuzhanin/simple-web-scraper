package com.hireright.job.junior.app.core.result;

import java.util.*;

/**
 * Represents word occurrences in text.
 * Similar to NumberOfCharacters text should be specific characters free.
 * Class contains occurrences where a key is the word and a value is an occurrence.
 *
 */
public class WordOccurrences {
    /**
     * Map for words and their occurrences
     */
    private Map<String, Integer> occurrences;
    /**
     * Map for words and elapsed time for getting number of occurrences
     */
    private Map<String, Long> times;

    public WordOccurrences() {
        this.occurrences = new HashMap<>();
        this.times = new HashMap<>();
    }

    public void update(String word, Integer times) {
        occurrences.put(word, times);
    }

    public void addTime(String word, long time) {
        times.put(word, time);
    }

    public Map<String, Integer> getOccurrences() {
        return occurrences;
    }

    public Map<String, Long> getTimes() {
        return times;
    }
}
