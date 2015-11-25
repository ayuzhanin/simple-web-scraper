package com.hireright.job.juniorcandidate.tools;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleWebScraper {

    private List<TextAndURLHolder> holders = null;
    private Parser parser = null;
    private TextProcessor processor = null;

    public SimpleWebScraper() {
        holders = new ArrayList<>();
        parser = new Parser();
        processor = new TextProcessor();
    }

    public void process(String... args) throws IOException {
        parser.parse(args);
        holders.addAll(parser.getURLs().stream().map(TextAndURLHolder::new).collect(Collectors.toList()));

        for (TextAndURLHolder holder : holders) {
            holder.retrieveCleanTextFromURL();
        }

        processor.run(holders, parser.getWords());
    }

}
