package com.hireright.job.juniorcandidate;

import com.hireright.job.juniorcandidate.tools.SimpleWebScraper;

import java.io.IOException;

public class app {

    public static void main(String... args)  {

        SimpleWebScraper simpleWebScraper = new SimpleWebScraper();
        try {
            simpleWebScraper.process(args);
        }
        catch (IOException exc){
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }

    }
}
