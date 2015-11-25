package com.hireright.job.juniorcandidate.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  ласс, служащий дл€ обработки строки и вывода информации о ней. („астота встречаемости слова, извлечение предложений и т.д.)
 *
 * @author Artur Yuzhanin
 */
public class TextProcessor {

    /**
     * ¬ывод времени выполнени€ программы. True - да, false - иначе.
     */
    private boolean verbose;

    /**
     * ¬ывод сообщени€ в консоль. ≈сли verbose true, то сообщение выведитс€.
     *
     * @param message сообщение на вывод.
     */
    private void printVerbose(String message) {
        if (verbose) {
            System.out.println(message);
        }
    }

    /**
     * ‘ункци€ дл€ вычислени€ частоты встречаемости слов в тексте.
     *
     * @param holder держатель текста и URL.
     * @param words  список слов, частоту встречаемости которых нужно вычислить.
     */
    private void countWordsOccurrences(TextURLHolder holder, List<String> words) {
        for (String word : words) {
            int counter = holder.getText().countWordOccurrences(word);
            System.out.println(holder.getUrl().toString() + " word '" + word + "': " + counter + " occurrence");
        }
    }

    /**
     * ‘ункци€ дл€ вычислени€ частоты встречаемости слов в текстах.
     *
     * @param holders держатели текстов и URL.
     * @param words   список слов, частоту встречаемости которых нужно вычислить.
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
     * ‘ункци€ дл€ вычислени€ количества символов на странице.
     *
     * @param holder держатель текста страницы.
     */
    private void countCharacters(TextURLHolder holder) {
        int counter = holder.getText().countCharacters();
        System.out.println(holder.getUrl().toString() + " contains " + counter + " characters");
    }

    /**
     * ‘ункци€ дл€ вычислени€ количества символов на страницах.
     *
     * @param holders спискок держателей текстов страниц.
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
     * ‘ункци€ дл€ вывода на экран предложений, содержащих заданное слово.
     *
     * @param word      слово, которое содержат предложени€.
     * @param sentences предложени€ с заданным словом.
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
     * ‘унки€ дл€ извлечени€ предложений из текста, содержащих любые из заданных слов.
     *
     * @param holder держатель текста страницы.
     * @param words  список слов.
     */
    private void extractAllSentencesWithSeveralWords(TextURLHolder holder, List<String> words) {
        Map<String, Set<String>> wordSentencesDict = holder.getText().extractAllSentencesWithSeveralWords(words);
        Set<String> keys = wordSentencesDict.keySet();
        keys.forEach(key -> printWordAndSentences(key, wordSentencesDict.get(key)));
    }

    /**
     * ‘ункиц€ дл€ извлечени€ всех предложений из всех заданных текстов держателей, содержащих любые из заданных слов.
     *
     * @param holders держатели текстов страниц.
     * @param words   список слов.
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
     * ќбъедин€ем все функции.
     *
     * @param holders держатели текстов страниц.
     * @param words   список слов.
     */
    public void run(List<TextURLHolder> holders, List<String> words) {
        verbose = Parser.FLAGS.get(new Flag(Flag.Value.VERBOSE));
        countWordsOccurencesInTexts(holders, words);
        countCharacters(holders);
        extractAllSentencesWithSeveralWords(holders, words);
    }
}
