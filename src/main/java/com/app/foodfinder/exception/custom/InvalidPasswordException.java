package com.app.foodfinder.exception.custom;

/**
 * Thrown to indicate that the password entered is invalid.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * Constructs an InvalidPasswordException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}
