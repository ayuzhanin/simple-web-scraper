package com.hireright.job.juniorcandidate.tools;

/**
 * Класс Flag для хранения флагов, введеныз в командной строке (терминале)
 *
 * @author Artur Yuzhanin
 */
public class Flag {
    /**
     * Перечисляем легальные и нелегальные флаги
     */
    public final static char[] LEGAL_FLAGS = {'v', 'w', 'c', 'e'};
    public final static char ILLEGAL_FLAG = ' ';

    /**
     * Вложенный класс, хранящий флаги в "явном виде"
     * VERBOSE - вывод затраченного времени.
     * COUNT_WORDS_OCCURENCES - вывод частоты встречаемости слов(а).
     * COUNT_CHARACTERS_OF_PAGE - вывод количества символов на странице.
     * EXTRACT_SENTENCES_WITH_WORD - вывод предложений, содержащих данное слово(-а)
     * NOT_DEFINED - флаг не определен.
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
     * Поле хранящее флаг в "явном виде" (удобно читаемом, воспринимаемом)
     */
    private Value value;

    /**
     * Конструктор
     *
     * @param value тип фалага в "явном виде"
     */
    public Flag(Value value) {
        this.value = value;
    }

    /**
     * Конструктор
     *
     * @param symbol символьный тип, эквивалентный (но воспринимаемый неудобно) классу перечисления выше
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
     * Проверка легальности флага
     *
     * @return true, если флаг легальный (есть в списке выше)
     */
    public boolean isLegalFlag() {
        return !(value == Value.NOT_DEFINED);
    }

    /**
     * Получаем флаг
     *
     * @return значение (enum) флага
     */
    public Value getValue() {
        return value;
    }

    /**
     * Переопределяем метод для работы с HaspMap.
     * Вычисляем хэш-код объекта.
     *
     * @return hashCode, который равен порядковому номеру значения перечисления
     */
    @Override
    public int hashCode() {
        return value.ordinal();
    }

    /**
     * Переопределяем метод для работы с HashMap.
     * Проверка объектов на предмет равенства.
     *
     * @param object объект, с которым сравнивается объект this.
     * @return true, если объекты this и object равны
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
