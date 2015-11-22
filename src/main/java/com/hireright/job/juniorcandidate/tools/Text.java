package com.hireright.job.juniorcandidate.tools;


import java.util.ArrayList;
import java.util.List;

public class Text {
    private String text = null;

    public Text(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void removeTags() {
        text = text.replaceAll("<[^>]*>", "").replaceAll("\\s+", " ");
    }

    private String createRegularCopy(String string) {
        StringBuilder regular = new StringBuilder();
        char c;
        c = string.charAt(0);
        regular.append("[").append(c).append(Character.toUpperCase(c)).append("]");
        for (int index = 1; index < string.length(); index++) {
            c = string.charAt(index);
            regular.append("[").append(c).append(Character.toUpperCase(c)).append("]");
        }
        System.out.println(regular);
        return regular.toString();
    }

    public int countWord(String word) {
        String replaced = text.replaceAll(createRegularCopy(word), "");
        return (text.length() - replaced.length()) / word.length();
    }

    public int countWordGreedily(String word) {
        int index = 0;
        int occurance = 0;
        String regWord = createRegularCopy(word);
        while ((index = text.indexOf(regWord, index)) != -1 && index < text.length()) {
            index += word.length();
            occurance++;
        }
        return occurance;
    }

    public int countCharacters() {
        return text.length();
    }

    private String extractSentence(int positionOfWordInText) {
        char sym;
        int endIndex = 0;
        for (int index = positionOfWordInText; index < text.length(); index++) {
            sym = text.charAt(index);
            if ('!' == sym || '?' == sym || '.' == sym){
                endIndex = index;
            }
        }

        int beginIndex = 0;
        for (int index = positionOfWordInText; index != 0; index--) {
            sym = text.charAt(index);
            if ('!' == sym || '?' == sym || '.' == sym){
                beginIndex = index + 2;
            }
        }

        return text.substring(beginIndex, endIndex);
    }

    public List<String> extractSentences(String givenWord) {
        List<String> sentences = new ArrayList<>();
        String regWord = createRegularCopy(givenWord);
        int index = 0;
        while ((index = text.indexOf(regWord, index)) != -1 && index < text.length()) {
            index += givenWord.length();
            sentences.add(extractSentence(index));
        }
        return sentences;
    }

}
