package com.hireright.job.junior.app.core;


class Task {

    final static char[] LEGAL = {'v', 'w', 'c', 'e'};
    private final static char ILLEGAL = ' ';

    enum Flag {
        VERBOSE(LEGAL[0]),
        COUNT_GIVEN_WORDS_OCCURRENCES(LEGAL[1]),
        COUNT_CHARACTERS_IN_PAGE(LEGAL[2]),
        EXTRACT_SENTENCES_WITH_GIVEN_WORD(LEGAL[3]),
        NOT_DEFINED(ILLEGAL);

        private char flag;

        Flag(char flag) {
            this.flag = flag;
        }
    }

    private Flag flag;

    Task(Flag flag) {
        this.flag = flag;
    }

    Task(char symbol) {
        switch (symbol) {
            case 'v':
                flag = Flag.VERBOSE;
                break;
            case 'w':
                flag = Flag.COUNT_GIVEN_WORDS_OCCURRENCES;
                break;
            case 'c':
                flag = Flag.COUNT_CHARACTERS_IN_PAGE;
                break;
            case 'e':
                flag = Flag.EXTRACT_SENTENCES_WITH_GIVEN_WORD;
                break;
            default:
                flag = Flag.NOT_DEFINED;
                break;
        }
    }

    boolean isExecutable() {
        return !(flag == Flag.NOT_DEFINED);
    }

    private Flag getFlag() {
        return flag;
    }

    @Override
    public int hashCode() {
        return flag.ordinal();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Task && (object == this || flag == ((Task) object).getFlag());
    }
}
