package com.hireright.job.junior.app.core.result;

public class NumberOfCharacters {
    private int number;
    private long time;

    public NumberOfCharacters(int number, long time) {
        this.number = number;
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public long getTime() {
        return time;
    }
}
