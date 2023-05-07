package uk.ac.ncl.tastetracker.exception.custom;


/**
 * Thrown to indicate that the credentials entered or passed are invalid.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.5 (06-05-2023)
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
