package com.uade.beappsint.exception;

import org.hibernate.HibernateException;

public class BadRequestException extends HibernateException {
    public BadRequestException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}