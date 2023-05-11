package uk.ac.ncl.tastetracker.exception.custom;


/**
 * Thrown to indicate that the resource being added exists already.
 *
 * @author Surendhar Chandran Jayapal
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