package com.hireright.job.junior.app;

import com.hireright.job.junior.app.core.Scraper;
import com.hireright.job.junior.app.exception.ProcessingException;

/**
 * @author Artur Y.
 * Starting application
 */

public class App {
    public static void main(String... args)  {
        if (args.length == 0) {
            return;
        }
        Scraper scraper = new Scraper();
        try {
            scraper.run(args);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }
    }
}
