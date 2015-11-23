package com.hireright.job.juniorcandidate.tools.handlers;

import com.hireright.job.juniorcandidate.tools.Text;
import com.hireright.job.juniorcandidate.tools.handlers.base.Handler;

import java.util.List;
import java.util.Map;

public class WordOccurencesCounter implements Handler {
    public void verboseRun(Map<Character, Boolean> flags, List<String> words, Text text) {
        boolean verbose = flags.get('v');
        boolean countOccurences = flags.get('w');
        if (countOccurences) {
            for (String word : words) {
                long startTime = verbose ? System.currentTimeMillis() : 0;
                int counter = text.countWordOccurrences(word);
                System.out.println("Word's '" + word + "' occurrence: " + counter + '\n');
                long endTime = verbose ? System.currentTimeMillis() - startTime : 0;
                if (verbose) {
                    System.out.println("Elapsed time: " + (endTime - startTime) + "ms");
                }
            }
        }
    }
}
