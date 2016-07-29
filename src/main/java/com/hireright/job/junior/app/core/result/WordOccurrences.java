package com.hireright.job.junior.app.core.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordOccurrences {
    private Map<String, Integer> map;
    private List<Long> times;

    public WordOccurrences() {
        this.map = new LinkedHashMap<>();
        this.times = new ArrayList<>();
    }

    public void update(String word, Integer times) {
        map.put(word, times);
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public List<Long> getTimes() {
        return times;
    }

    public void addTime(long time) {
        times.add(time);
    }
}
