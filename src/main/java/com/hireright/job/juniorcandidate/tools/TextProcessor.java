package com.hireright.job.juniorcandidate.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * �����, �������� ��� ��������� ������ � ������ ���������� � ���. (������� ������������� �����, ���������� ����������� � �.�.)
 *
 * @author Artur Yuzhanin
 */
public class TextProcessor {

    /**
     * ����� ������� ���������� ���������. True - ��, false - �����.
     */
    private boolean verbose;

    /**
     * ����� ��������� � �������. ���� verbose true, �� ��������� ���������.
     *
     * @param message ��������� �� �����.
     */
    private void printVerbose(String message) {
        if (verbose) {
            System.out.println(message);
        }
    }

    /**
     * ������� ��� ���������� ������� ������������� ���� � ������.
     *
     * @param holder ��������� ������ � URL.
     * @param words  ������ ����, ������� ������������� ������� ����� ���������.
     */
    private void countWordsOccurrences(TextURLHolder holder, List<String> words) {
        for (String word : words) {
            int counter = holder.getText().countWordOccurrences(word);
            System.out.println(holder.getUrl().toString() + " word '" + word + "': " + counter + " occurrence");
        }
    }

    /**
     * ������� ��� ���������� ������� ������������� ���� � �������.
     *
     * @param holders ��������� ������� � URL.
     * @param words   ������ ����, ������� ������������� ������� ����� ���������.
     */
    private void countWordsOccurencesInTexts(List<TextURLHolder> holders, List<String> words) {
        boolean countWordsOccurence = Parser.FLAGS.get(new Flag(Flag.Value.COUNT_WORDS_OCCURENCES));
        if (countWordsOccurence) {
            printVerbose("Counting words...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            holders.forEach(holder -> countWordsOccurrences(holder, words));
            long endTime = verbose ? System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    /**
     * ������� ��� ���������� ���������� �������� �� ��������.
     *
     * @param holder ��������� ������ ��������.
     */
    private void countCharacters(TextURLHolder holder) {
        int counter = holder.getText().countCharacters();
        System.out.println(holder.getUrl().toString() + " contains " + counter + " characters");
    }

    /**
     * ������� ��� ���������� ���������� �������� �� ���������.
     *
     * @param holders ������� ���������� ������� �������.
     */
    private void countCharacters(List<TextURLHolder> holders) {
        boolean countCharactersOfPage = Parser.FLAGS.get(new Flag(Flag.Value.COUNT_CHARACTERS_OF_PAGE));
        if (countCharactersOfPage) {
            printVerbose("Counting characters...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            holders.forEach(this::countCharacters);
            long endTime = verbose ? System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    /**
     * ������� ��� ������ �� ����� �����������, ���������� �������� �����.
     *
     * @param word      �����, ������� �������� �����������.
     * @param sentences ����������� � �������� ������.
     */
    private void printWordAndSentences(String word, Set<String> sentences) {
        System.out.println("Sentences with word '" + word + "':");
        //sentences.forEach(System.out::println);
        int index = 1;
        for (String sentence : sentences) {
            System.out.println(index + ". " + sentence);
            index++;
        }
    }

    /**
     * ������ ��� ���������� ����������� �� ������, ���������� ����� �� �������� ����.
     *
     * @param holder ��������� ������ ��������.
     * @param words  ������ ����.
     */
    private void extractAllSentencesWithSeveralWords(TextURLHolder holder, List<String> words) {
        Map<String, Set<String>> wordSentencesDict = holder.getText().extractAllSentencesWithSeveralWords(words);
        Set<String> keys = wordSentencesDict.keySet();
        keys.forEach(key -> printWordAndSentences(key, wordSentencesDict.get(key)));
    }

    /**
     * ������� ��� ���������� ���� ����������� �� ���� �������� ������� ����������, ���������� ����� �� �������� ����.
     *
     * @param holders ��������� ������� �������.
     * @param words   ������ ����.
     */
    private void extractAllSentencesWithSeveralWords(List<TextURLHolder> holders, List<String> words) {
        boolean extractSentencesWithWord = Parser.FLAGS.get(new Flag(Flag.Value.EXTRACT_SENTENCES_WITH_WORD));
        if (extractSentencesWithWord) {
            printVerbose("Extracting sentences...");
            long startTime = verbose ? System.currentTimeMillis() : 0;
            holders.forEach(holder -> extractAllSentencesWithSeveralWords(holder, words));
            long endTime = verbose ? System.currentTimeMillis() : 0;
            printVerbose("Elapsed time: " + (endTime - startTime) + " ms");
        }
    }

    /**
     * ���������� ��� �������.
     *
     * @param holders ��������� ������� �������.
     * @param words   ������ ����.
     */
    public void run(List<TextURLHolder> holders, List<String> words) {
        verbose = Parser.FLAGS.get(new Flag(Flag.Value.VERBOSE));
        countWordsOccurencesInTexts(holders, words);
        countCharacters(holders);
        extractAllSentencesWithSeveralWords(holders, words);
    }
}
