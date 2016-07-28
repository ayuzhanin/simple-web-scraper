package com.hireright.job.junior.app.exception;

/**
 * Exception which indicates something is wrong during the text parsing
 *  @author Artur Yuzhanin
 *
 */
public class ParsingException extends ProcessingException {
    /**
     * Constructor
     * @param message
     */
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(Throwable throwable) {
        super(throwable);
    }
}
