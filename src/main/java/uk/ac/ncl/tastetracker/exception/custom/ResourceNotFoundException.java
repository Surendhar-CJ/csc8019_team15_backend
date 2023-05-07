package uk.ac.ncl.tastetracker.exception.custom;

/**
 * Thrown to indicate that the resource being searched for is not found.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.3 (28-04-2023)
 */
public class ResourceNotFoundException extends RuntimeException {


    /**
     * Constructs a ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
