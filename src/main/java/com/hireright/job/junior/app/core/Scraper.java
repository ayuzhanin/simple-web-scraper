package com.hireright.job.junior.app.core;

import com.hireright.job.junior.app.exception.ProcessingException;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scraper {

    private Parser parser;

    private TaskPerformer performer;

    private List<URL> urls;

    private PrintStream printer;

    private List<String> words;

    private Map<Task, Boolean> tasks;

    public Scraper() {
        parser = new Parser();
        performer = new TaskPerformer();
    }

    private void parse(String... input) throws ProcessingException {
        parser.run(input);
        urls = parser.getURLs();
        printer = parser.getPrinter();
        words = parser.getWords();
        tasks = parser.getTasks();
    }

    private void print(){
        if (tasks.get(new Task(Task.Flag.COUNT_GIVEN_WORDS_OCCURRENCES))) {
            List<Map<String, Integer>> numberOfOccurrences = performer.getNumberOfOccurrences();
            for (int index = 0; index < urls.size() - 1; index++){
                printer.println((index + 1) + ". " + urls.get(index) + ": ");
                Map <String, Integer> map = numberOfOccurrences.get(index);
                for (Map.Entry entry : map.entrySet()) {
                    printer.println("'" + entry.getKey() + "' " + entry.getValue() + " occurrences");
                }
            }
        }

        if (tasks.get(new Task(Task.Flag.COUNT_CHARACTERS_IN_PAGE))) {
            List<Integer> numberOfCharacters = performer.getNumberOfCharacters();
            for (int index = 0; index < urls.size() - 1; index++) {
                printer.println((index + 1) + ". " + urls.get(index) + ": " + numberOfCharacters.get(0) + " characters");
            }
        }

        if (tasks.get(new Task(Task.Flag.EXTRACT_SENTENCES_WITH_GIVEN_WORD))) {
            List<Map<String, Set<String>>> sentences = performer.getSentences();
            for (int index = 0; index < urls.size() - 1; index++) {
                printer.println((index + 1) + ". " + urls.get(index) + ": ");
                Map <String, Set<String>> map = sentences.get(index);
                for (Map.Entry entry : map.entrySet()) {
                    printer.println("'" + entry.getKey() + "' found in:");
                    ((Set<String>)entry.getValue()).forEach(printer::println);
                }
            }
        }
    }

    public void run(String... input) throws ProcessingException {
        parse(input);
        performer.run(urls, words, tasks);
        print();
    }

}
