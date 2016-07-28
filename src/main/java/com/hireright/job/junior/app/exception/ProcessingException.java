package com.hireright.job.junior.app.exception;

import java.io.IOException;

/**
 * Exception which indicates something is wrong during the text parsing
 *  @author Artur Yuzhanin
 *
 */
public class ProcessingException extends IOException {
    /**
     * Constructor
     * @param
     */
    public ProcessingException(Throwable throwable) {
        super(throwable);
    }
    ProcessingException(String message) {
        super(message);
    }
}