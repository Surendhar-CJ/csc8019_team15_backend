package com.app.foodfinder.exception.custom;


/**
 * Thrown to indicate that the resource being added exists already.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public class ResourceExistsException extends RuntimeException{

    /**
     * Constructs an ResourceExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceExistsException(String message){
        super(message);
    }
}