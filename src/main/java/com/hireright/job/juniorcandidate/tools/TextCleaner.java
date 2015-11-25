package com.hireright.job.juniorcandidate.tools;

/**
 * Класс, служащий для отчистки веб страницы от JS, CSS, HTML.
 *
 * @author Artur Yuzhanin
 */
public class TextCleaner {

    /**
     * Начало JS-кода
     */
    private static final String JS_START = "<script";
    /**
     * Конец JS-кода
     */
    private static final String JS_END = "</script>";

    /**
     * Начало CSS-стилей
     */
    private static final String CSS_START = "<style>";
    /**
     * Конец CSS-стилей
     */
    private static final String CSS_END = "</style>";

    /**
     * Функция для удаления CSS и JS кода со страницы.
     *
     * @param someString     строка, откуда нужно удалить код
     * @param startSubstring начало кода.
     * @param endSubstring   конец кода.
     * @return строка без JS и CSS.
     */
    private StringBuilder removeSubstrings(StringBuilder someString, String startSubstring, String endSubstring) {
        int startInd = someString.indexOf(startSubstring);
        int endInd = someString.indexOf(endSubstring);
        if (startInd == -1 || endInd == -1) {
            return someString;
        }
        someString.delete(startInd, endInd + endSubstring.length());
        return removeSubstrings(someString, startSubstring, endSubstring);
    }

    /**
     * Функиця для удаления HTML кода со страницы.
     *
     * @param someString страница (строка), откуда нужно удалить код.
     * @return строка без HTML.
     */
    private StringBuilder removeHTML(StringBuilder someString) {
        String string = someString.toString().replaceAll("<[^>]*>", "");
        return new StringBuilder(string);
    }

    /**
     * Объединяем функции выше в одну.
     *
     * @param string строка, котокую нужно отчистить от JS, CSS, HTML.
     * @return строка с чистым текстом.
     */
    public String run(String string) {
        StringBuilder builder = new StringBuilder(string);
        builder = removeSubstrings(builder, JS_START, JS_END);
        builder = removeSubstrings(builder, CSS_START, CSS_END);
        builder = removeHTML(builder);
        return builder.toString().replaceAll("[\\s\\n]+", " ");
    }
}
