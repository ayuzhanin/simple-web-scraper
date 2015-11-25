package com.hireright.job.juniorcandidate.tools;

/**
 * �����, �������� ��� �������� ��� �������� �� JS, CSS, HTML.
 *
 * @author Artur Yuzhanin
 */
public class TextCleaner {

    /**
     * ������ JS-����
     */
    private static final String JS_START = "<script";
    /**
     * ����� JS-����
     */
    private static final String JS_END = "</script>";

    /**
     * ������ CSS-������
     */
    private static final String CSS_START = "<style>";
    /**
     * ����� CSS-������
     */
    private static final String CSS_END = "</style>";

    /**
     * ������� ��� �������� CSS � JS ���� �� ��������.
     *
     * @param someString     ������, ������ ����� ������� ���
     * @param startSubstring ������ ����.
     * @param endSubstring   ����� ����.
     * @return ������ ��� JS � CSS.
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
     * ������� ��� �������� HTML ���� �� ��������.
     *
     * @param someString �������� (������), ������ ����� ������� ���.
     * @return ������ ��� HTML.
     */
    private StringBuilder removeHTML(StringBuilder someString) {
        String string = someString.toString().replaceAll("<[^>]*>", "");
        return new StringBuilder(string);
    }

    /**
     * ���������� ������� ���� � ����.
     *
     * @param string ������, ������� ����� ��������� �� JS, CSS, HTML.
     * @return ������ � ������ �������.
     */
    public String run(String string) {
        StringBuilder builder = new StringBuilder(string);
        builder = removeSubstrings(builder, JS_START, JS_END);
        builder = removeSubstrings(builder, CSS_START, CSS_END);
        builder = removeHTML(builder);
        return builder.toString().replaceAll("[\\s\\n]+", " ");
    }
}
