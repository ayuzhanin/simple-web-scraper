package com.hireright.job.junior.app.core.result;

import java.util.Map;
import java.util.Set;

/**
 * Class for given word and sentences which contain such word
 * Text should be specific symbols free
 * Scrapper and TaskManager use that class for representing, storing and printing result
 */
public class WordsToSentencesMap {
    /**
     * Map of words and corresponding sentences
     */
    private Map<String, Set<String>> sentences;

    /**
     * Map of words and elapsed time used to obtain results
     */
    private Map<String, Long> time;

    public WordsToSentencesMap(Map<String, Set<String>> sentences, Map<String, Long> time) {
        this.sentences = sentences;
        this.time = time;
    }

    public Map<String, Set<String>> getSentences() {
        return sentences;
    }

    public Map<String, Long> getTime() {
        return time;
    }
}
