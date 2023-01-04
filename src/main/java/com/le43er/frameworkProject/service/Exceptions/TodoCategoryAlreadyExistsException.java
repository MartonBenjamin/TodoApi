package com.le43er.frameworkProject.service.Exceptions;

public class TodoCategoryAlreadyExistsException extends Exception{
    public TodoCategoryAlreadyExistsException() {
    }

    public TodoCategoryAlreadyExistsException(String message) {
        super(message);
    }

    public TodoCategoryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoCategoryAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public TodoCategoryAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
