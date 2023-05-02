package com.app.foodfinder.exception.custom;

/**
 * Thrown to indicate that the resource being searched for is not found.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public class ResourceNotFoundException extends RuntimeException {


    /**
     * Constructs an ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
