package uk.ac.ncl.tastetracker.exception.custom;

/**
 * Thrown to indicate that the input entered or passed are invalid.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.5 (06-05-2023)
 */
public class InvalidInputException extends RuntimeException {

    /**
     * Constructs an InvalidInputException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
