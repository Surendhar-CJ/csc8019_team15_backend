package com.app.foodfinder.exception.custom;


/**
 * Thrown to indicate that the user already exists.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public class UserExistsException extends RuntimeException {

    /**
     * Constructs an UserExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserExistsException(String message) {
        super(message);
    }
}