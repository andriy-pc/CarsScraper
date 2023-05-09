package org.automotive.exception;

public class TerminateProcessException extends RuntimeException {

    public TerminateProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
