package com.hireright.job.junior.app.core.result;

/**
 * Class which is used fot representation number of characters
 * (including whitespaces) in page and time elapsed to obtain it.
 *
 * This class is used for pages which contains cleared text only, i.e.
 * page should not contain any HTML, CSS or JavaScript symbols.
 *
 * Scrapper and TaskManager use that class for representing, storing and printing result
 */

public class NumberOfCharacters {
    /**
     * Number of characters in page. Non-negative value
     */
    private int number;
    /**
     * Time elapsed for obtaining the number of characters
     */
    private long time;

    /**
     * Constructor
     * @param number represents number of characters of pure text
     * @param time represents time in nanoseconds
     */
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
