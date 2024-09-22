package com.uade.beappsint.exception;

import org.hibernate.HibernateException;

/**
 * Exception thrown when a bad request is made.
 * Extends HibernateException to provide additional context for Hibernate-related errors.
 */
public class BadRequestException extends HibernateException {

    /**
     * Constructs a new BadRequestException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Returns the detail message string of this exception.
     *
     * @return the detail message string of this exception.
     */
    public String getMessage() {
        return super.getMessage();
    }
}