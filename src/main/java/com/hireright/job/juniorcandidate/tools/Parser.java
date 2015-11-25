package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.ParsingException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Parser {

    private String pathToURL;
    private String stringOfFlags;
    private List<String> words;
    private List<URL> URLs;

    public final static HashMap<Flag, Boolean> FLAGS = new HashMap<>();

    public Parser() {
        words = new ArrayList<>();
        URLs = new ArrayList<>();
    }

    static {
        for (char flag : Flag.LEGAL_FLAGS) {
            FLAGS.put(new Flag(flag), false);
        }
    }

    public List<URL> getURLs() {
        return URLs;
    }

    public List<String> getWords() {
        return words;
    }

    private boolean isLegalFlag(char flagToCheck) {
        Flag flag = new Flag(flagToCheck);
        return flag.isLegalFlag();
    }

    private void setFlagMentioned(char flagToMention) {
        FLAGS.put(new Flag(flagToMention), true);
    }

    private void parseFlags() throws ParsingException {
        stringOfFlags = stringOfFlags.replaceAll("(-|–)", "");
        for (int index = 0; index < stringOfFlags.length(); index++) {
            char currentFlag = stringOfFlags.charAt(index);
            if (isLegalFlag(currentFlag)) {
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
            if (trimmed.length() == 2 && isLegalFlag(trimmed.charAt(1))) {
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

        words.add(lastWords.toString().trim());
        stringOfFlags = flagsBuilder.toString().replaceAll("\\s*", "");
    }

    private void splitPathAndFirstWords() {
        String str = words.get(0);
        words.remove(0);
        words.add(0, str.substring(str.indexOf(" ") + 1, str.length()));
        pathToURL = str.substring(0, str.indexOf(" "));
    }

    private void putArgsToWords(String... args) {
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

    public void parseURLs() throws ParsingException {
        File file = new File(pathToURL);
        String inputLine = "";
        if (file.exists() && !file.isDirectory()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToURL))) {
                while ((inputLine = reader.readLine()) != null) {
                    URLs.add(new URL(inputLine));
                }
                reader.close();
            } catch (MalformedURLException exception) {
                URLs.clear();
                throw new ParsingException(inputLine + " invalid URL found while retrieving URLs from file");
            } catch (FileNotFoundException exception) {
                URLs.clear();
                throw new ParsingException("File " + pathToURL + " not found");
            } catch (IOException exception) {
                URLs.clear();
                throw new ParsingException("IOException occurred");
            }
        } else {
            try {
                URLs.add(new URL(pathToURL));
            } catch (MalformedURLException exception) {
                URLs.clear();
                throw new ParsingException(pathToURL + " invalid URL");
            }
        }

    }

    public void parse(String... args) throws IOException {
        putArgsToWords(args);
        splitPathAndFirstWords();
        splitLastWordsAndFlags();
        parseFlags();
        parseURLs();
    }
}
