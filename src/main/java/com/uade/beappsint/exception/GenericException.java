package com.uade.beappsint.exception;

import org.hibernate.HibernateException;

/**
 * Exception thrown for generic errors.
 * Extends HibernateException to provide additional context for Hibernate-related errors.
 */
public class GenericException extends HibernateException {
    private final Exception exception;

    /**
     * Constructs a new GenericException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param e the cause of the exception.
     */
    public GenericException(String message, Exception e) {
        super(message);
        this.exception = e;
    }

    /**
     * Returns the detail message string of the underlying exception.
     *
     * @return the detail message string of the underlying exception.
     */
    public String getMessage() {
        return exception.getMessage();
    }
}