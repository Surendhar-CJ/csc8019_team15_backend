package com.app.foodfinder.exception;


import com.app.foodfinder.exception.custom.InvalidPasswordException;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.exception.custom.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalTime;

/**
 * This class serves as a global exception handler.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@ControllerAdvice
public final class GlobalExceptionHandler {

    /**
     * Handles the {@link ResourceNotFoundException} thrown.
     *
     * @param resourceNotFoundException The {@link ResourceNotFoundException} to handle.
     *
     * @return ResponseEntity containing the error response and HTTP status code.
     */
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlingNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }



    /**
     * Handles the {@link InvalidPasswordException} thrown.
     *
     * @param invalidPasswordException The {@link InvalidPasswordException} to handle.
     *
     * @return ResponseEntity containing the error response and HTTP status code.
     */
    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handlingInvalidPasswordException(InvalidPasswordException invalidPasswordException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), invalidPasswordException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
    }



    /**
     * Handles the {@link IllegalArgumentException} thrown.
     *
     * @param illegalArgumentException The {@link IllegalArgumentException} to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code.
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
     * @return A ResponseEntity containing the error response and HTTP status code.
     */
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlingUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), usernameNotFoundException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }



    /**
     * Handles the {@link UserExistsException} thrown.
     *
     * @param userExistsException The {@link UserExistsException} to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code.
     */
    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<ErrorResponse> handlingUserExistException(UserExistsException userExistsException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), userExistsException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }

    
}
