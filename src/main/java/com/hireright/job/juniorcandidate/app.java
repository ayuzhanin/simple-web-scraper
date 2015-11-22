package com.hireright.job.juniorcandidate;

import com.hireright.job.juniorcandidate.exception.RetrievingException;
import com.hireright.job.juniorcandidate.tools.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class app {

    public static void main(String... args) throws IOException {


        //String str = "http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java";
        String str = "http://www.oracle.com/";

        URL url = new URL(str);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));


        try {
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine).append("\n");
            }
            reader.close();

            //System.out.println(builder);
            Text t = new Text(builder.toString());
            t.removeTags();
            System.out.println(t.getText());
        } catch (MalformedURLException exception) {
            System.out.println("lol");
        } catch (IOException exception) {
            System.out.println("lol2");
        }

    }
}
