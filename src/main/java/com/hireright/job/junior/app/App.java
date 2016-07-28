package com.hireright.job.junior.app;

import com.hireright.job.junior.app.core.Scraper;
import com.hireright.job.junior.app.exception.ProcessingException;

public class App {

    public static void main(String... args)  {
        Scraper scraper = new Scraper();
        try {
            scraper.run(args);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }
    }
}
