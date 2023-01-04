package com.le43er.frameworkProject.service.Exceptions;

public class TodoNotFoundException extends Exception{
    public TodoNotFoundException() {
    }

    public TodoNotFoundException(String message) {
        super(message);
    }

    public TodoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoNotFoundException(Throwable cause) {
        super(cause);
    }

    public TodoNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
