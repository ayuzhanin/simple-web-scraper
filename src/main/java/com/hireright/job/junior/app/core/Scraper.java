package com.hireright.job.junior.app.core;

import com.hireright.job.junior.app.core.result.WordOccurrences;
import com.hireright.job.junior.app.core.result.WordsToSentencesMap;
import com.hireright.job.junior.app.exception.ProcessingException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Scraper {

    private Parser parser;

    private List<TaskManager> managers;

    private List<URL> urls;

    private PrintWriter writer;

    private List<String> words;

    private Map<Task, Boolean> tasks;

    private boolean verbose;

    public Scraper() {
        parser = new Parser();
        managers = new ArrayList<>();
        verbose = false;
    }

    private void parse(String... input) throws ProcessingException {
        parser.run(input);
        urls = parser.getURLs();
        writer = parser.getWriter();
        words = parser.getWords();
        tasks = parser.getTasks();
    }

    private void printVerbose(String message) {
        if (verbose) {
            writer.println(message);
        }
    }

    private void printWordOccurrences(TaskManager manager) {
        if (tasks.get(new Task(Task.Flag.COUNT_GIVEN_WORDS_OCCURRENCES))) {
            writer.println(manager.getUrl() + ": ");
            WordOccurrences wordOccurrences = manager.getWordOccurrences();

            Map<String, Integer> occurrences = wordOccurrences.getOccurrences();
            Map<String, Long> times = wordOccurrences.getTimes();
            SortedSet<String> keys = new TreeSet<>(occurrences.keySet());

            for (String key : keys) {
                writer.println("'" + key + "': " + occurrences.get(key) + " occurrences");
                printVerbose("Elapsed time: " + times.get(key) + " ms");
            }
        }
    }

    private void printNumberOfCharacters(TaskManager manager) {
        if (tasks.get(new Task(Task.Flag.COUNT_CHARACTERS_IN_PAGE))) {
            writer.print(manager.getUrl() + ": ");
            writer.print(manager.getNumberOfCharacters().getNumber() + " characters");
            printVerbose("Elapsed time: " + manager.getNumberOfCharacters().getTime() + " ms");
        }
    }

    private void printWordsToSentencesMap(TaskManager manager) {
        if (tasks.get(new Task(Task.Flag.EXTRACT_SENTENCES_WITH_GIVEN_WORD))) {
            writer.println(manager.getUrl() + ": ");

            WordsToSentencesMap wordsToSentences = manager.getWordsToSentences();
            Map<String, Set<String>> sentences = wordsToSentences.getSentences();
            Map<String, Long> time = wordsToSentences.getTime();
            SortedSet<String> sortedWords = new TreeSet<>(sentences.keySet());

            for (String word : sortedWords) {
                writer.println("'" + word + "': ");
                int index = 0;
                for (String sentence : sentences.get(word)){
                    writer.println(index + ". " + sentence);
                    index++;
                }
                printVerbose("Elapsed time: " + time.get(word) + " ms");
            }
        }
    }

    private void print() {
        managers.forEach(this::printWordOccurrences);
        managers.forEach(this::printNumberOfCharacters);
        managers.forEach(this::printWordsToSentencesMap);
        writer.close();
    }

    public void run(String... input) throws ProcessingException {
        parse(input);

        verbose = tasks.get(new Task(Task.Flag.VERBOSE));

        for (URL url : urls) {
            TaskManager manager = new TaskManager();
            manager.run(url, words, tasks);
            managers.add(manager);
        }

        print();
    }

}
