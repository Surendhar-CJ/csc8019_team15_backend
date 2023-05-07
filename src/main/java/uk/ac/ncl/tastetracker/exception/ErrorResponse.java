package uk.ac.ncl.tastetracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;


/**
 * This class represents an error response object that is returned to the client when an error occurs during API processing.
 * All the runtime exceptions and custom exceptions thrown are modified in GlobalExceptionHandler according to the format
 * of ErrorResponse class and sent to the client.
 * It uses Lombok annotations to generate getters, setters, and all argument constructor.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Represents HTTP Status code that is to be sent to the client
     */
    private int httpStatusCode;

    /**
     * Represents the message of the exception
     */
    private String message;

    /**
     * Represents the timestamp of the exception
     */
    private LocalTime timeStamp;

}
