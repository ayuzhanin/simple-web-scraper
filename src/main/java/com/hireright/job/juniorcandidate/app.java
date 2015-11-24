package com.hireright.job.juniorcandidate;

import com.hireright.job.juniorcandidate.tools.SimpleWebScraper;

import java.io.IOException;

public class app {

    public static void main(String... args) throws IOException {

        SimpleWebScraper simpleWebScraper = new SimpleWebScraper();
        simpleWebScraper.process(args);


    }
}
