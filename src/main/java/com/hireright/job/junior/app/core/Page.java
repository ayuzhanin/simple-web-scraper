package com.hireright.job.junior.app.core;


import com.hireright.job.junior.app.exception.ProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;


class Page {

    private class Cleaner {

        private static final String JS_START = "<script";

        private static final String JS_END = "</script>";

        private static final String CSS_START = "<style>";

        private static final String CSS_END = "</style>";

        private StringBuilder removeSubstring(StringBuilder string, String start, String end) {
            int startIndex = string.indexOf(start);
            int endIndex = string.indexOf(end);
            if (startIndex == -1 || endIndex == -1) {
                return string;
            }
            return new StringBuilder(string).delete(startIndex, endIndex + end.length());
        }

        private StringBuilder removeHTML(StringBuilder someString) {
            String string = someString.toString().replaceAll("<[^>]*>", "");
            return new StringBuilder(string);
        }

        String removeSpecialCharacters(String string) {
            StringBuilder builder = new StringBuilder(string);
            builder = removeSubstring(builder, JS_START, JS_END);
            builder = removeSubstring(builder, CSS_START, CSS_END);
            builder = removeHTML(builder);
            return builder.toString().replaceAll("[\\s\\n]+", " ");
        }
    }

    private String text;

    private String getRawText(URL url) throws ProcessingException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String input;
            StringBuilder builder = new StringBuilder();
            while ((input = reader.readLine()) != null) {
                builder.append(input).append("\n");
            }
            reader.close();
            return builder.toString();
        } catch (IOException ex) {
            throw new ProcessingException(ex);
        }
    }

    private String removeSpecialCharacters(String string) {
        Cleaner cleaner = new Cleaner();
        return cleaner.removeSpecialCharacters(string);
    }

    void clean(URL url) throws ProcessingException {
        String raw = getRawText(url);
        text = removeSpecialCharacters(raw);
    }

    private String getCaseInsensitiveCopy(String string) {
        StringBuilder regular = new StringBuilder();
        char symbol;
        for (int index = 0; index < string.length(); index++) {
            symbol = string.charAt(index);
            regular.append("[").append(Character.toLowerCase(symbol)).append(Character.toUpperCase(symbol)).append("]");
        }
        return regular.toString();
    }

    int getNumberOfOccurrences(String word) {
        String replaced = text.replaceAll(getCaseInsensitiveCopy(word), "");
        return (text.length() - replaced.length()) / word.length();
    }

    int getNumberOfCharacters() {
        return text.length();
    }

    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), Math.min(b, c));
    }

    private int max(int a, int b, int c) {
        return -1 * min(-1 * a, -1 * b, -1 * c);
    }

    private int endOfSentence(int wordIndex) {
        int index = text.indexOf("!", wordIndex);
        int exclamationMarkIndex = (index != -1) ? index : Integer.MAX_VALUE;
        index = text.indexOf("?", wordIndex);
        int questionMarkIndex = (index != -1) ? index : Integer.MAX_VALUE;
        index = text.indexOf(".", wordIndex);
        int fullStopIndex = (index != -1) ? index : Integer.MAX_VALUE;
        return min(exclamationMarkIndex, questionMarkIndex, fullStopIndex);
    }

    private int startOfSentence(int wordIndex) {
        String substring = text.substring(0, wordIndex);
        int index = substring.lastIndexOf("!");
        int exclamationMarkIndex = (index != -1) ? index : 0;
        index = substring.lastIndexOf("?");
        int questionMarkIndex = (index != -1) ? index : 0;
        index = substring.lastIndexOf(".");
        int fullStopIndex = (index != -1) ? index : 0;
        int start = max(exclamationMarkIndex, questionMarkIndex, fullStopIndex);
        return start == 0 ? start : start + 1;
    }

    private String getSentence(int wordIndex) {
        int end = endOfSentence(wordIndex);
        int start = startOfSentence(wordIndex);
        return text.substring(start, end + 1).trim();
    }

    private Set<String> getSentences(String word) {
        Set<String> sentences = new HashSet<>();
        int index = 0;
        while ((index = text.indexOf(word, index)) != -1 && index < text.length()) {
            sentences.add(getSentence(index));
            index += word.length();
        }
        return sentences;
    }

    Map<String, Set<String>> getSentences(List<String> words) {
        Map<String, Set<String>> sentences = new HashMap<>();
        for (String word : words) {
            Set<String> sentencesForSingleWord = getSentences(word);
            sentences.put(word, sentencesForSingleWord);
        }
        return sentences;
    }

}
