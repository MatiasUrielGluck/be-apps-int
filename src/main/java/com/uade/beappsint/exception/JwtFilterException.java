package com.uade.beappsint.exception;

import org.hibernate.HibernateException;

/**
 * Exception thrown when there is an issue with the JWT filter.
 * Extends HibernateException to provide additional context for Hibernate-related errors.
 */
public class JwtFilterException extends HibernateException {

    /**
     * Constructs a new JwtFilterException with the specified detail message.
     *
     * @param message the detail message.
     */
    public JwtFilterException(String message) {
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