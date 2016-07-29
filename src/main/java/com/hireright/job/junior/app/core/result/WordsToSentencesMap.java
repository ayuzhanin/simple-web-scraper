package com.hireright.job.junior.app.core.result;

import java.util.Map;
import java.util.Set;

public class WordsToSentencesMap {
    private Map<String, Set<String>> map;
    private double time;
    public WordsToSentencesMap(Map<String, Set<String>> map, double time){
        this.map = map;
        this.time = time;
    }

    public Map<String, Set<String>> getMap() {
        return map;
    }

    public double getTime() {
        return time;
    }
}
