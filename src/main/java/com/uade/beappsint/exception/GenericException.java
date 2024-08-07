package com.uade.beappsint.exception;

import org.hibernate.HibernateException;

public class GenericException extends HibernateException {
    private final Exception exception;

    public GenericException(String message, Exception e) {
        super(message);
        this.exception = e;
    }

    public String getMessage() {
        return exception.getMessage();
    }
}