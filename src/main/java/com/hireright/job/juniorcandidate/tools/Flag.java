package com.hireright.job.juniorcandidate.tools;

/**
 * ����� Flag ��� �������� ������, �������� � ��������� ������ (���������)
 *
 * @author Artur Yuzhanin
 */
public class Flag {
    /**
     * ����������� ��������� � ����������� �����
     */
    public final static char[] LEGAL_FLAGS = {'v', 'w', 'c', 'e'};
    public final static char ILLEGAL_FLAG = ' ';

    /**
     * ��������� �����, �������� ����� � "����� ����"
     * VERBOSE - ����� ������������ �������.
     * COUNT_WORDS_OCCURENCES - ����� ������� ������������� ����(�).
     * COUNT_CHARACTERS_OF_PAGE - ����� ���������� �������� �� ��������.
     * EXTRACT_SENTENCES_WITH_WORD - ����� �����������, ���������� ������ �����(-�)
     * NOT_DEFINED - ���� �� ���������.
     */
    public enum Value {

        VERBOSE(LEGAL_FLAGS[0]),
        COUNT_WORDS_OCCURENCES(LEGAL_FLAGS[1]),
        COUNT_CHARACTERS_OF_PAGE(LEGAL_FLAGS[2]),
        EXTRACT_SENTENCES_WITH_WORD(LEGAL_FLAGS[3]),
        NOT_DEFINED(ILLEGAL_FLAG);

        private char value;

        Value(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }

    /**
     * ���� �������� ���� � "����� ����" (������ ��������, ��������������)
     */
    private Value value;

    /**
     * �����������
     *
     * @param value ��� ������ � "����� ����"
     */
    public Flag(Value value) {
        this.value = value;
    }

    /**
     * �����������
     *
     * @param symbol ���������� ���, ������������� (�� �������������� ��������) ������ ������������ ����
     */
    public Flag(char symbol) {
        switch (symbol) {
            case 'v':
                value = Value.VERBOSE;
                break;
            case 'w':
                value = Value.COUNT_WORDS_OCCURENCES;
                break;
            case 'c':
                value = Value.COUNT_CHARACTERS_OF_PAGE;
                break;
            case 'e':
                value = Value.EXTRACT_SENTENCES_WITH_WORD;
                break;
            default:
                value = Value.NOT_DEFINED;
                break;
        }
    }

    /**
     * �������� ����������� �����
     *
     * @return true, ���� ���� ��������� (���� � ������ ����)
     */
    public boolean isLegalFlag() {
        return !(value == Value.NOT_DEFINED);
    }

    /**
     * �������� ����
     *
     * @return �������� (enum) �����
     */
    public Value getValue() {
        return value;
    }

    /**
     * �������������� ����� ��� ������ � HaspMap.
     * ��������� ���-��� �������.
     *
     * @return hashCode, ������� ����� ����������� ������ �������� ������������
     */
    @Override
    public int hashCode() {
        return value.ordinal();
    }

    /**
     * �������������� ����� ��� ������ � HashMap.
     * �������� �������� �� ������� ���������.
     *
     * @param object ������, � ������� ������������ ������ this.
     * @return true, ���� ������� this � object �����
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Flag)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        return value == ((Flag) object).getValue();
    }
}
