package com.hireright.job.juniorcandidate;

import com.hireright.job.juniorcandidate.tools.Container;

import java.io.IOException;

public class app {

    public static void main(String... args) throws IOException {

        //String str = "http://www.oracle.com/";

        Container container = new Container();
        container.process(args);

    }
}
