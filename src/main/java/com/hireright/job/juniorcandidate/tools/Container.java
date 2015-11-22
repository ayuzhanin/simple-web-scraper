package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.ParsingException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Container {
    private List<String> words;
    private String path;
    private String flags;
    private final static HashMap<Character, Boolean> flagsMap = new HashMap<>();
    private List<URLTextHolder> holders = null;

    static {
        flagsMap.put('v', false);
        flagsMap.put('w', false);
        flagsMap.put('c', false);
        flagsMap.put('e', false);
    }

    public Container() {
        this.words = new ArrayList<>();
        this.holders = new ArrayList<>();
    }

    private static boolean isProperFlag(char c) {
        return flagsMap.containsKey(c);
    }

    private static void setMentionedFlag(char flagToMention) {
        flagsMap.put(flagToMention, true);
    }

    private void parseFlags() throws ParsingException {
        char sym;
        for (int index = 0; index < flags.length(); index++) {
            sym = flags.charAt(index);
            if (isProperFlag(sym)) {
                setMentionedFlag(sym);
            } else {
                if ('-' != sym) {
                    throw new ParsingException("Unknown flag: " + sym + "\\n");
                }
            }
        }
    }

    private void parseFlagsAndLastWords() {
        String str = words.get(words.size() - 1);
        String[] parts = str.split(" ");
        words.remove(words.size() - 1);
        StringBuilder flagsBuilder = new StringBuilder();
        int counter = 0;
        for (int index = parts.length - 1; index != 0; index--) {
            String trimmed = parts[index].trim();
            if (trimmed.length() == 2) {
                if (isProperFlag(trimmed.charAt(1))) {
                    flagsBuilder.append(trimmed);
                    counter++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        words.addAll(Arrays.asList(parts).subList(0, parts.length - counter));
        flags = flagsBuilder.toString().replaceAll("\\s*", "");
    }

    private void parsePathAndFirstWords() {
        String str = words.get(0);
        words.remove(0);
        words.add(0, str.substring(str.indexOf(" ") + 1, str.length()));
        path = str.substring(0, str.indexOf(" "));
    }

    private void parse(String... args) {
        StringBuilder inputBuilder = new StringBuilder();
        char delimeter = '|';
        for (String arg : args) {
            inputBuilder.append(arg).append(" ");
        }

        String string = inputBuilder.toString().replaceAll("\\s*,\\s*", String.valueOf(delimeter));
        StringTokenizer tokenizer = new StringTokenizer(string, String.valueOf(delimeter));

        while (tokenizer.hasMoreElements()) {
            words.add((String) tokenizer.nextElement());
        }
    }

    public void parseInput(String... args) throws IOException {
        parse(args);
        parsePathAndFirstWords();
        parseFlagsAndLastWords();
        parseFlags();
    }

    public void process(String... args) throws IOException {
        parseInput(args);
        List<URL> urls = URLsRetriever.getURLs(path);
        holders = new ArrayList<>();
        for (URL url : urls) {
            holders.add(new URLTextHolder(url));
        }
        for (URLTextHolder holder : holders) {
            holder.retrieveTextFromURL();
        }

        boolean verbosity = flagsMap.get('v');
        boolean wordsOccurrence = flagsMap.get('w');
        boolean countNumberOfCharacters = flagsMap.get('c');
        boolean extractSentences = flagsMap.get('e');
        for (URLTextHolder holder : holders) {
            System.out.println(holder.getUrl().toString() + ":\n");
            if (wordsOccurrence) {
                for (String word : words) {
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
            if (countNumberOfCharacters) {
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
            if (extractSentences) {
                for (String word : words) {
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
    }

}
