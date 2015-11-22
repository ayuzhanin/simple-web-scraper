package com.hireright.job.juniorcandidate.tools;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Container {
    private List<URLTextHolder> holders = null;
    private InputParser parser = null;

    public Container() {
        holders = new ArrayList<>();
        parser = new InputParser();
    }

    private void countWords() {
        boolean verbosity = parser.getFlagsMap().get('v');
        for (URLTextHolder holder : holders) {
            System.out.println(holder.getUrl().toString() + ":\n");
            for (String word : parser.getWords()) {
                long startTime = 0;
                long endTime = 0;
                if (verbosity) {
                    startTime = System.currentTimeMillis();
                }
                int counter = holder.getText().countWord(word);
                System.out.println("Word's '" + word + "' occurrence: " + counter + '\n');
                if (verbosity) {
                    endTime = System.currentTimeMillis();
                    System.out.println("Elapsed time: " + (endTime - startTime) + "ms");
                }
            }
        }
    }

    private void countCharacters() {
        boolean verbosity = parser.getFlagsMap().get('v');
        for (URLTextHolder holder : holders) {
            System.out.println(holder.getUrl().toString() + ":\n");
            long startTime = 0;
            long endTime = 0;
            if (verbosity) {
                startTime = System.currentTimeMillis();
            }
            int counter = holder.getText().countCharacters();
            System.out.println("Number of characters: " + counter);
            if (verbosity) {
                endTime = System.currentTimeMillis();
                System.out.println("Elapsed time: " + (endTime - startTime) + "ms");
            }
        }
    }

    private void extractSentences() {
        boolean verbosity = parser.getFlagsMap().get('v');
        for (URLTextHolder holder : holders) {
            System.out.println(holder.getUrl().toString() + ":\n");
            for (String word : parser.getWords()) {
                long startTime = 0;
                long endTime = 0;
                if (verbosity) {
                    startTime = System.currentTimeMillis();
                }
                List<String> sentences = holder.getText().extractSentences(word);
                System.out.println("Sentences with word '" + word + "': \n");
                sentences.forEach(System.out::println);
                if (verbosity) {
                    endTime = System.currentTimeMillis();
                    System.out.println("Elapsed time: " + (endTime - startTime) + "ms");
                }
            }
        }
    }

    public void process(String... args) throws IOException {
        parser.parse(args);

        List<URL> urls = URLsRetriever.getURLs(parser.getPath());
        holders = new ArrayList<>();
        for (URL url : urls) {
            holders.add(new URLTextHolder(url));
        }

        for (URLTextHolder holder : holders) {
            holder.retrieveTextFromURL();
        }

        if (parser.getFlagsMap().get('w')) {
            countWords();
        }

        if (parser.getFlagsMap().get('c')) {
            countCharacters();
        }

        if (parser.getFlagsMap().get('e')) {
            extractSentences();
        }
    }

}
