package uk.ac.ncl.tastetracker.exception.custom;

/**
 * Thrown to indicate that the resource being searched for is not found.
 *
 * @author Surendhar Chandran Jayapal
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
