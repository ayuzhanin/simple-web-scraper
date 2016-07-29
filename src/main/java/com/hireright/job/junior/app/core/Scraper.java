package com.hireright.job.junior.app.core;

import com.hireright.job.junior.app.core.result.WordOccurrences;
import com.hireright.job.junior.app.core.result.WordsToSentencesMap;
import com.hireright.job.junior.app.exception.ProcessingException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scraper {

    private Parser parser;

    private List<TaskManager> managers;

    private List<URL> urls;

    private PrintStream printer;

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
        printer = parser.getPrinter();
        words = parser.getWords();
        tasks = parser.getTasks();
    }

    private void printVerbose(String message) {
        if (verbose) {
            printer.println(message);
        }
    }

    private void printWordOccurrences(TaskManager manager) {
        if (tasks.get(new Task(Task.Flag.COUNT_GIVEN_WORDS_OCCURRENCES))) {
            printer.println(manager.getUrl() + ": ");
            WordOccurrences wordOccurrences = manager.getWordOccurrences();
            Map<String, Integer> map = wordOccurrences.getMap();
            int index = 0;
            for (Map.Entry entry : map.entrySet()) {
                printer.println("'" + entry.getKey() + "': " + entry.getValue() + " occurrences");
                printVerbose("Elapsed time: " + wordOccurrences.getTimes().get(index));
                index++;
            }
        }
    }

    private void printNumberOfCharacters(TaskManager manager) {
        if (tasks.get(new Task(Task.Flag.COUNT_CHARACTERS_IN_PAGE))) {
            printer.print(manager.getUrl() + ": ");
            printer.print(manager.getNumberOfCharacters().getNumber() + " characters");
            printVerbose("Elapsed time: " + manager.getNumberOfCharacters().getTime());
        }
    }

    private void printWordsToSentencesMap(TaskManager manager) {
        if (tasks.get(new Task(Task.Flag.EXTRACT_SENTENCES_WITH_GIVEN_WORD))) {
            printer.println(manager.getUrl() + ": ");
            WordsToSentencesMap wordsToSentences = manager.getWordsToSentences();
            Map<String, Set<String>> map = wordsToSentences.getMap();
            for (Map.Entry entry : map.entrySet()) {
                printer.println("'" + entry.getKey() + "' found in:");
                int index = 0;
                for (String string : ((Set<String>) entry.getValue())){
                    printer.println(index + ". " + string);
                    index++;
                }
                printVerbose("Elapsed time: " + wordsToSentences.getTime());
            }
        }
    }

    private void print() {
        managers.forEach(this::printWordOccurrences);
        managers.forEach(this::printNumberOfCharacters);
        managers.forEach(this::printWordsToSentencesMap);
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
