package com.le43er.frameworkProject.service.Exceptions;

public class TodoCategoryNotFoundException extends Exception{
    public TodoCategoryNotFoundException() {
    }

    public TodoCategoryNotFoundException(String message) {
        super(message);
    }

    public TodoCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoCategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public TodoCategoryNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
