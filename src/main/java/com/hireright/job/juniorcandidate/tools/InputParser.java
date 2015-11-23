package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.ParsingException;

import java.io.IOException;
import java.util.*;

public class InputParser {

    private final static char[] flagsChar = {'v', 'w', 'c', 'e'};

    private String path;
    private String flags;
    private List<String> words;
    private HashMap<Character, Boolean> flagsMap = null;

    public InputParser() {
        flagsMap = new HashMap<>();
        for (char aFlagsChar : flagsChar) {
            flagsMap.put(aFlagsChar, false);
        }
        words = new ArrayList<>();
    }

    public HashMap<Character, Boolean> getFlagsMap() {
        return flagsMap;
    }

    public String getPath() {
        return path;
    }

    public List<String> getWords() {
        return words;
    }

    private boolean isProperFlag(char c) {
        return flagsMap.containsKey(c);
    }

    private void setMentionedFlag(char flagToMention) {
        flagsMap.put(flagToMention, true);
    }

    private void parseFlags() throws ParsingException {
        flags = flags.replaceAll("(-|–)", "");
        for (int index = 0; index < flags.length(); index++) {
            if (isProperFlag(flags.charAt(index))) {
                setMentionedFlag(flags.charAt(index));
            } else {
                throw new ParsingException("Unknown flag: " + flags.charAt(index) + "\n");
            }
        }
    }

    private void splitLastWordsAndFlags() {
        String str = words.get(words.size() - 1);
        String[] parts = str.split(" ");
        words.remove(words.size() - 1);
        StringBuilder flagsBuilder = new StringBuilder();
        int counter = 0;
        for (int index = parts.length - 1; index >= 0; index--) {
            String trimmed = parts[index].trim();
            if (trimmed.length() == 2 && isProperFlag(trimmed.charAt(1))) {
                flagsBuilder.append(trimmed);
                counter++;
            } else {
                break;
            }
        }

        StringBuilder lastWords = new StringBuilder();
        for (int index = 0; index < parts.length - counter; index++) {
            lastWords.append(parts[index]).append(" ");
        }

        words.add(lastWords.toString());
        flags = flagsBuilder.toString().replaceAll("\\s*", "");
    }

    private void splitPathAndFirstWords() {
        String str = words.get(0);
        words.remove(0);
        words.add(0, str.substring(str.indexOf(" ") + 1, str.length()));
        path = str.substring(0, str.indexOf(" "));
    }

    private void parseToWords(String... args) {
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

    public void parse(String... args) throws IOException {
        parseToWords(args);
        splitPathAndFirstWords();
        splitLastWordsAndFlags();
        parseFlags();
    }
}
