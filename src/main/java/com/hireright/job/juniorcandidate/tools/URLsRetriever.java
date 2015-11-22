package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.RetrievingException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class URLsRetriever {

    private static List<URL> retrieveURLsFromFile(String pathToFile) throws RetrievingException {
        String inputLine = "";
        List<URL> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            urls = new ArrayList<>();
            while ((inputLine = reader.readLine()) != null) {
                urls.add(new URL(inputLine));
            }
            reader.close();
            return urls;
        } catch (IOException e) {
            throw new RetrievingException("retrieveURLsFromFile: I/O exception while retrieving URL " + inputLine + " from file has occurred\n");
        } finally {
            urls.clear();
        }
    }

    public static List<URL> getURLs(String path) throws IOException {
        File file = new File(path);
        List<URL> urls = new ArrayList<>();
        if (file.exists() && !file.isDirectory()) {
            try {
                urls = retrieveURLsFromFile(path);
            } finally {
                urls.clear();
            }
        } else {
            try {
                urls.add(new URL(path));
            } finally {
                urls.clear();
            }
        }
        return urls;
    }
}
