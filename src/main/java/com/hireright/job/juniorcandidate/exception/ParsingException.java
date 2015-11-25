package com.hireright.job.juniorcandidate.exception;

import java.io.IOException;

/**
 * @author Artur Yuzhanin
 * Исключение  (тут все понятно)
 */
public class ParsingException extends IOException {
    /**
     * Конструктор
     * @param message сообщение, передаваемое в исключение.
     */
    public ParsingException(String message) {
        super(message);
    }
}
