package com.hireright.job.junior.app.core;

import com.hireright.job.junior.app.exception.ParsingException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

class Parser {
    private List<String> words;

    private List<URL> URLs;

    private HashMap<Task, Boolean> tasks;

    private PrintStream printer;

    List<String> getWords() {
        return words;
    }

    List<URL> getURLs() {
        return URLs;
    }

    HashMap<Task, Boolean> getTasks() {
        return tasks;
    }

    PrintStream getPrinter() {
        return printer;
    }

    private List<String> toList(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    private List<String> getFlags(List<String> input){
        List<String> flags = new ArrayList<>();
        for (int index = input.size() - 1; index >= 0; index--){
            String string = input.get(index);
            if (string.length() == 2 && (string.startsWith("-") || string.startsWith("–"))){
                flags.add(string);
            } else {
                break;
            }
        }
        return flags;
    }

    private String getPathToURLs(List<String> input) {
        return input.get(0);
    }

    private String getPathToWrite(List<String> input) {
        return input.get(1);
    }

    private List<String> getRawWords(List<String> input){
        List<String> words = new ArrayList<>(input);
        Collections.copy(words, input);
        words.removeAll(getFlags(input));
        words.remove(getPathToURLs(input));
        words.remove(getPathToWrite(input));
        return words;
    }

    private List<String> getWords(List<String> input){
        List<String> rawWords = getRawWords(input);
        String tokenized = rawWords.toString().replaceAll("\\[", "").replaceAll("]","").replaceAll(",,","|").replaceAll(",","");
        List<String> words = toList(tokenized.split("\\| "));
        words.forEach(String::trim);
        return words;
    }

    private List<URL> getFromFile(String path) throws ParsingException {
        List<URL> urls = new ArrayList<>();
        String input;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((input = reader.readLine()) != null) {
                urls.add(new URL(input));
            }
            reader.close();
        } catch (IOException ex) {
            urls.clear();
            throw new ParsingException(ex);
        }
        return urls;
    }

    private URL getDirectly(String path) throws ParsingException {
        try {
            return new URL(path);
        } catch (MalformedURLException ex) {
            throw new ParsingException(ex);
        }
    }

    private List<URL> toURLs(String path) throws ParsingException {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            return getFromFile(path);
        } else {
            List<URL> single = new ArrayList<>();
            single.add(getDirectly(path));
            return single;
        }
    }

    private PrintStream toPrinter(String path) throws ParsingException {
        PrintStream printStream;
        if (path.equals("_")) {
            printStream = System.out;
        } else {
            try {
                printStream = new PrintStream(new FileOutputStream(path, true));
            } catch (FileNotFoundException ex) {
                throw new ParsingException(ex);
            }
        }
        return printStream;
    }

    private HashMap<Task, Boolean> toTasks (List<String> flags){
        HashMap<Task, Boolean> map = new HashMap<>();
        for (char flag : Task.LEGAL) {
            map.put(new Task(flag), false);
        }
        flags.forEach((String string) -> {
            char flag = string.charAt(1);
            map.put(new Task(flag), true);
        });
        return map;
    }

    void run(String... args) throws ParsingException {
        List<String> input = toList(args);

        String pathToURLs = getPathToURLs(input);
        URLs = toURLs(pathToURLs);

        String pathToWrite = getPathToWrite(input);
        printer = toPrinter(pathToWrite);

        words = getWords(input);

        List<String> flags = getFlags(input);
        tasks = toTasks(flags);
    }

}
