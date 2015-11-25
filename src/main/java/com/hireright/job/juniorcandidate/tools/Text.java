package com.hireright.job.juniorcandidate.tools;


import java.util.*;

/**
 *  ласс, служащий дл€ первичной обработки текстов.
 *
 * @author Artur Yuzhanin.
 */
public class Text {
    /**
     * ѕоле с текстом.
     */
    private String text = null;

    /**
     *  онструктор.
     *
     * @param text - строка, подаваема€ на вход. Ёто и есть текст, который далее будет обрабатыватьс€.
     */
    public Text(String text) {
        this.text = text;
    }

    /**
     * ‘ункци€ дл€ отчистки текста (html страницы) от JS, CSS, HTML.
     */
    public void clean() {
        text = new TextCleaner().run(text);
    }

    /**
     * ‘ункци€ дл€ создани€ нечувствительной к регистру копии строки.
     *
     * @param string строка, которую необходимо копировать.
     * @return копи€ строки нечувствительна€ к регистру.
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
     * ‘унки€ дл€ подсчета количеств вхождени€ подстроки в строку.
     *
     * @param word подстрока (слово), частоту входлений которой нужно определить.
     * @return число вхождений.
     */
    public int countWordOccurrences(String word) {
        String replaced = text.replaceAll(createCaseInsensitiveCopy(word), "");
        return (text.length() - replaced.length()) / word.length();
    }

    /**
     * ‘ункци€ дл€ подсчета, количества символов на странице.
     *
     * @return количество символов на странице.
     */
    public int countCharacters() {
        return text.length();
    }

    /**
     * ‘унци€ дл€ нахождени€ наименьшего числа из трех.
     * »з матана известно, что ее же можно использовать и чтобы найти максимальное из чисел.
     *
     * @param a первое число.
     * @param b второе число.
     * @param c третье число.
     * @return минимальное число.
     */
    private int findMin(int a, int b, int c) {
        return (a < b) ? (a < c ? a : (c < b ? c : b)) : (b < c ? b : (c < a ? c : a));
    }

    /**
     * ‘ункци€ дл€ нахождени€ конца предложени€, по позиции начала слова из него.
     *
     * @param positionOfWord позици€ начала слова из предложени€.
     * @return индекс конца предложени€.
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
     * ‘ункци€ дл€ нахождени€ начала предложени€, по позиции начала слова из него.*
     *
     * @param positionOfWord позици€ начала слова из предложени€.
     * @return индекс начала предложени€.
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
     * ‘ункци€ дл€ извлечени€ предложени€ с заданным словом из текста.
     *
     * @param positionOfWord позици€ начала слова.
     * @return предложение, содержащее слово.
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
     * ‘ункци€ дл€ извлечени€ всех предложени€ с заданным словом из текста.
     *
     * @param word заданное слово.
     * @return список предложений, содержащих данное слово.
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
     * ‘ункци€ дл€ извлечени€ всех предложени€ с любым из заданных слов из текста из списка.
     *
     * @param words заданные слова.
     * @return словарь: заданные слова и предложени€, содержащие любое заданные слова.
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
