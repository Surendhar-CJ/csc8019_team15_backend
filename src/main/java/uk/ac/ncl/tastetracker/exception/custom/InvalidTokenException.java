package uk.ac.ncl.tastetracker.exception.custom;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message){
        super(message);
    }
}
