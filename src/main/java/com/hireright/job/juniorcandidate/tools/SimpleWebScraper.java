package com.hireright.job.juniorcandidate.tools;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * �����, ��������������� ��� ���-���������. ������������ ���������. ��� ���������� ������ ��� ��� ����� ���������� � ���.
 *
 * @author Artur Yuzhanin.
 */
public class SimpleWebScraper {

    /**
     * ������ ���������� ��� URL � �������, ���������� �� ���� URL.
     */
    private List<TextURLHolder> holders = null;
    /**
     * ������, ������������ �������� ����� ������������.
     */
    private Parser parser = null;
    /**
     * ���������� �������, ������� ����������� ������������ �� URL.
     */
    private TextProcessor processor = null;

    /**
     * �����������, ������ ����������.
     */
    public SimpleWebScraper() {
        holders = new ArrayList<>();
        parser = new Parser();
        processor = new TextProcessor();
    }

    /**
     * ������� ��� ������� ��������.
     * ��������� ������. ����� �������� ��� ��������� ��� URL � �������.
     * ��������� ������ ��������� � �������� ������ �� URL, ������� ������ � ����������.
     *
     * @param args ��������� ��������� ������.
     * @throws IOException � ������ ������������� ������.
     */
    public void process(String... args) throws IOException {
        parser.parse(args);
        holders.addAll(parser.getURLs().stream().map(TextURLHolder::new).collect(Collectors.toList()));

        for (TextURLHolder holder : holders) {
            holder.retrieveCleanTextFromURL();
        }

        processor.run(holders, parser.getWords());
    }

}
