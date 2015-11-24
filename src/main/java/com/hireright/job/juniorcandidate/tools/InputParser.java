package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.ParsingException;

import java.io.IOException;
import java.util.*;

public class InputParser {

    private String pathToURL;
    private String stringOfFlags;
    private List<String> words;

    public final static HashMap<Flag, Boolean> flags = new HashMap<>();

    public InputParser() {
        words = new ArrayList<>();
    }

    static {
        for (char flag : Flag.LEGAL_FLAGS) {
            flags.put(new Flag(flag), false);
        }
    }

    public String getPathToURL() {
        return pathToURL;
    }

    public List<String> getWords() {
        return words;
    }

    private boolean isProperFlag(char flagToCheck) {
        Flag flag = new Flag(flagToCheck);
        return flag.isProperFlag();
    }

    private void setFlagMentioned(char flagToMention) {
        flags.put(new Flag(flagToMention), true);
    }

    private void parseFlags() throws ParsingException {
        stringOfFlags = stringOfFlags.replaceAll("(-|–)", "");
        for (int index = 0; index < stringOfFlags.length(); index++) {
            char currentFlag = stringOfFlags.charAt(index);
            if (isProperFlag(currentFlag)) {
                setFlagMentioned(currentFlag);
            } else {
                throw new ParsingException("Unknown flag: " + currentFlag + "\n");
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
        stringOfFlags = flagsBuilder.toString().replaceAll("\\s*", "");
    }

    private void splitPathAndFirstWords() {
        String str = words.get(0);
        words.remove(0);
        words.add(0, str.substring(str.indexOf(" ") + 1, str.length()));
        pathToURL = str.substring(0, str.indexOf(" "));
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
