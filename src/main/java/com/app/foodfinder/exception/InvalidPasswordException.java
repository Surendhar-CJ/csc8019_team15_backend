package com.app.foodfinder.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message)
    {
        super(message);
    }
}
