package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.RetrievingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Holder {
    private Text text = null;
    private URL url = null;

    public Holder(URL url) {
        this.url = url;
    }

    public void retrieveTextFromURL() throws RetrievingException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine).append("\n");
            }
            reader.close();
            text = new Text(builder.toString());
            text.removeHTMLTags();
        } catch (MalformedURLException exception) {
            throw new RetrievingException("retrieveTextFromURL(" + url.toString() + "): Malformed URL exception has occurred\n");
        } catch (IOException exception) {
            throw new RetrievingException("retrieveTextFromURL(" + url.toString() + "): I/O exception while retrieving data from URL has occurred\n");
        }
    }

    public Text getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }
}
