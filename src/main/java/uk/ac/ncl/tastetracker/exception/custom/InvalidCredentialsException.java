package uk.ac.ncl.tastetracker.exception.custom;


/**
 * Thrown to indicate that the credentials entered or passed are invalid.
 *
 * @author Surendhar Chandran Jayapal
 */
public class InvalidCredentialsException extends RuntimeException{

    /**
     * Constructs an InvalidCredentialsException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
