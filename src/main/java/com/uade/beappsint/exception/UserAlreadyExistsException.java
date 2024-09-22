package com.uade.beappsint.exception;

import org.hibernate.HibernateException;

/**
 * Exception thrown when a user already exists.
 * Extends HibernateException to provide additional context for Hibernate-related errors.
 */
public class UserAlreadyExistsException extends HibernateException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserAlreadyExistsException(String message) {
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