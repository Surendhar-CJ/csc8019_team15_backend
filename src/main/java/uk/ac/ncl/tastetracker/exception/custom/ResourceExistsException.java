package uk.ac.ncl.tastetracker.exception.custom;


/**
 * Thrown to indicate that the resource being added exists already.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.3 (28-04-2023)
 */
public class ResourceExistsException extends RuntimeException{


    /**
     * Constructs a ResourceExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceExistsException(String message){
        super(message);
    }
}