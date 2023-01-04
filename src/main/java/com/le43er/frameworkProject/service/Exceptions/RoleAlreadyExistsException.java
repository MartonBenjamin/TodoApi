package com.le43er.frameworkProject.service.Exceptions;

public class RoleAlreadyExistsException extends Exception{
    public RoleAlreadyExistsException() {
    }

    public RoleAlreadyExistsException(String message) {
        super(message);
    }

    public RoleAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public RoleAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
