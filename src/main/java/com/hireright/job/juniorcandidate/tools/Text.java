package com.hireright.job.juniorcandidate.tools;


import java.util.*;

/**
 * �����, �������� ��� ��������� ��������� �������.
 *
 * @author Artur Yuzhanin.
 */
public class Text {
    /**
     * ���� � �������.
     */
    private String text = null;

    /**
     * �����������.
     *
     * @param text - ������, ���������� �� ����. ��� � ���� �����, ������� ����� ����� ��������������.
     */
    public Text(String text) {
        this.text = text;
    }

    /**
     * ������� ��� �������� ������ (html ��������) �� JS, CSS, HTML.
     */
    public void clean() {
        text = new TextCleaner().run(text);
    }

    /**
     * ������� ��� �������� ���������������� � �������� ����� ������.
     *
     * @param string ������, ������� ���������� ����������.
     * @return ����� ������ ���������������� � ��������.
     */
    private String createCaseInsensitiveCopy(String string) {
        StringBuilder regular = new StringBuilder();
        char symbol;
        for (int index = 0; index < string.length(); index++) {
            symbol = string.charAt(index);
            regular.append("[").append(Character.toLowerCase(symbol)).append(Character.toUpperCase(symbol)).append("]");
        }
        return regular.toString();
    }

    /**
     * ������ ��� �������� ��������� ��������� ��������� � ������.
     *
     * @param word ��������� (�����), ������� ��������� ������� ����� ����������.
     * @return ����� ���������.
     */
    public int countWordOccurrences(String word) {
        String replaced = text.replaceAll(createCaseInsensitiveCopy(word), "");
        return (text.length() - replaced.length()) / word.length();
    }

    /**
     * ������� ��� ��������, ���������� �������� �� ��������.
     *
     * @return ���������� �������� �� ��������.
     */
    public int countCharacters() {
        return text.length();
    }

    /**
     * ������ ��� ���������� ����������� ����� �� ����.
     * �� ������ ��������, ��� �� �� ����� ������������ � ����� ����� ������������ �� �����.
     *
     * @param a ������ �����.
     * @param b ������ �����.
     * @param c ������ �����.
     * @return ����������� �����.
     */
    private int findMin(int a, int b, int c) {
        return (a < b) ? (a < c ? a : (c < b ? c : b)) : (b < c ? b : (c < a ? c : a));
    }

    /**
     * ������� ��� ���������� ����� �����������, �� ������� ������ ����� �� ����.
     *
     * @param positionOfWord ������� ������ ����� �� �����������.
     * @return ������ ����� �����������.
     */
    private int endIndOfSentenceWithWord(int positionOfWord) {
        int indexOfEnd = text.indexOf("!", positionOfWord);
        int indOfExclamation = (indexOfEnd != -1) ? indexOfEnd : Integer.MAX_VALUE;
        indexOfEnd = text.indexOf("?", positionOfWord);
        int indOfQuestion = (indexOfEnd != -1) ? indexOfEnd : Integer.MAX_VALUE;
        indexOfEnd = text.indexOf(".", positionOfWord);
        int indOfFullStop = (indexOfEnd != -1) ? indexOfEnd : Integer.MAX_VALUE;

        return findMin(indOfExclamation, indOfQuestion, indOfFullStop);
    }

    /**
     * ������� ��� ���������� ������ �����������, �� ������� ������ ����� �� ����.*
     *
     * @param positionOfWord ������� ������ ����� �� �����������.
     * @return ������ ������ �����������.
     */
    private int startIndOfSentenceWithWord(int positionOfWord) {
        String substrWithBeginnig = text.substring(0, positionOfWord);

        int indexOfStart = substrWithBeginnig.lastIndexOf("!");
        int indOfExclamation = (indexOfStart != -1) ? indexOfStart : 0;
        indexOfStart = substrWithBeginnig.lastIndexOf("?");
        int indOfQuestion = (indexOfStart != -1) ? indexOfStart : 0;
        indexOfStart = substrWithBeginnig.lastIndexOf(".");
        int indOfFullStop = (indexOfStart != -1) ? indexOfStart : 0;
        return -1 * findMin(-1 * indOfExclamation, -1 * indOfQuestion, -1 * indOfFullStop);
    }

    /**
     * ������� ��� ���������� ����������� � �������� ������ �� ������.
     *
     * @param positionOfWord ������� ������ �����.
     * @return �����������, ���������� �����.
     */
    public String extractSentenceWithWord(int positionOfWord) {
        int endIndex = endIndOfSentenceWithWord(positionOfWord);
        int beginIndex = startIndOfSentenceWithWord(positionOfWord);
        if (beginIndex != 0) {
            beginIndex++;
        }
        return text.substring(beginIndex, endIndex + 1).trim();
    }

    /**
     * ������� ��� ���������� ���� ����������� � �������� ������ �� ������.
     *
     * @param word �������� �����.
     * @return ������ �����������, ���������� ������ �����.
     */
    public Set<String> extractAllSentencesWithWord(String word) {
        Set<String> sentences = new HashSet<>();
        int index = 0;
        while ((index = text.indexOf(word, index)) != -1 && index < text.length()) {
            sentences.add(extractSentenceWithWord(index));
            index += word.length();
        }
        return sentences;
    }

    /**
     * ������� ��� ���������� ���� ����������� � ����� �� �������� ���� �� ������ �� ������.
     *
     * @param words �������� �����.
     * @return �������: �������� ����� � �����������, ���������� ����� �������� �����.
     */
    public Map<String, Set<String>> extractAllSentencesWithSeveralWords(List<String> words) {
        Map<String, Set<String>> sentences = new HashMap<>();
        for (String word : words) {
            Set<String> sentencesForSingleWord = extractAllSentencesWithWord(word);
            sentences.put(word, sentencesForSingleWord);
        }
        return sentences;
    }

}
