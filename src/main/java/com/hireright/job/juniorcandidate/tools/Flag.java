package com.hireright.job.juniorcandidate.tools;

public class Flag {

    public final static char[] LEGAL_FLAGS = {'v', 'w', 'c', 'e'};
    public final static char ILLEGAL_FLAG = ' ';

    public enum Values {

        VERBOSE(LEGAL_FLAGS[0]),
        COUNT_WORDS_OCCURENCE(LEGAL_FLAGS[1]),
        COUNT_CHARACTERS_OF_PAGE(LEGAL_FLAGS[2]),
        EXTRACT_SENTENCES_WITH_WORD(LEGAL_FLAGS[3]),
        NOT_DEFINED(ILLEGAL_FLAG);

        private char value;

        Values(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }

    private Values values;

    public Flag(Values values) {
        this.values = values;
    }

    public Flag(char symbol) {
        switch (symbol) {
            case 'v':
                values = Values.VERBOSE;
                break;
            case 'w':
                values = Values.COUNT_WORDS_OCCURENCE;
                break;
            case 'c':
                values = Values.COUNT_CHARACTERS_OF_PAGE;
                break;
            case 'e':
                values = Values.EXTRACT_SENTENCES_WITH_WORD;
                break;
            default:
                values = Values.NOT_DEFINED;
                break;
        }
    }

    public boolean isLegalFlag(){
        return !(values == Values.NOT_DEFINED);
    }

    public Values getValues() {
        return values;
    }

    @Override
    public int hashCode() {
        return values.ordinal();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Flag)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        return values == ((Flag) object).getValues();
    }
}
