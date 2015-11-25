package com.hireright.job.juniorcandidate.tools;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleWebScraper {

    private List<Holder> holders = null;
    private Parser parser = null;
    private TextProcessor textProcessor = null;

    public SimpleWebScraper() {
        holders = new ArrayList<>();
        parser = new Parser();
        textProcessor = new TextProcessor();
    }

    public void process(String... args) throws IOException {
        parser.parse(args);

        List<URL> urls = parser.getURLs();
        holders = new ArrayList<>();
        holders.addAll(parser.getURLs().stream().map(Holder::new).collect(Collectors.toList()));

        for (Holder holder : holders) {
            holder.retrieveTextFromURL();
        }

        textProcessor.run(holders, parser.getWords());

    }

}
