package uk.ac.ncl.tastetracker.exception;


import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.ac.ncl.tastetracker.exception.custom.*;
import java.time.LocalTime;



/**
 * This class serves as a global exception handler.
 * Exceptions thrown are modified to give a proper response to the client with appropriate status code, message and time.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
 */
@ControllerAdvice
public final class GlobalExceptionHandler {



    /**
     * Handles the {@link ResourceNotFoundException} thrown.
     *
     * @param resourceNotFoundException The {@link ResourceNotFoundException} to handle.
     *
     * @return ResponseEntity containing the error response and HTTP status code 404 NOT FOUND.
     */
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlingResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }





    /**
     * Handles the {@link ResourceExistsException} thrown.
     *
     * @param resourceExistsException The {@link ResourceExistsException} to handle.
     *
     * @return ResponseEntity containing the error response and HTTP status code 409 CONFLICT.
     */
    @ExceptionHandler(value = ResourceExistsException.class)
    public ResponseEntity<ErrorResponse> handlingResourceExistsException(ResourceExistsException resourceExistsException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), resourceExistsException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }





    /**
     * Handles the {@link IllegalArgumentException} thrown.
     *
     * @param illegalArgumentException The {@link IllegalArgumentException} to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code 400 BAD REQUEST.
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), illegalArgumentException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }





    /**
     * Handles the {@link UsernameNotFoundException} thrown.
     *
     * @param usernameNotFoundException The {@link UsernameNotFoundException} to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code 404 NOT FOUND.
     */
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlingUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), usernameNotFoundException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }





    /**
     * Handles the {@link InvalidInputException} thrown.
     *
     * @param invalidInputException The {@link InvalidInputException} to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code 400 BAD REQUEST.
     */
    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handlingInvalidInputException(InvalidInputException invalidInputException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), invalidInputException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }





    /**
     * Handles the {@link InvalidCredentialsException} thrown.
     *
     * @param invalidCredentialsException The InvalidCredentialsException to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code 403 FORBIDDEN.
     */
    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handlingInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), invalidCredentialsException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.FORBIDDEN);
    }





    /**
     * Handles the {@link JSONException} thrown.
     *
     * @param jsonException The JSONException to handle
     *
     * @return A ResponseEntity containing the error response and HTTP status code 400 BAD REQUEST.
     */
    @ExceptionHandler(value = JSONException.class)
    public ResponseEntity<ErrorResponse> handlingJSONException(JSONException jsonException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), jsonException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
