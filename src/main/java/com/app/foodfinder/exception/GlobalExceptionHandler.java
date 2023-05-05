package com.app.foodfinder.exception;


import com.app.foodfinder.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalTime;

/**
 * This class serves as a global exception handler.
 *
 * Exceptions thrown are modified to give a proper response to the client with appropriate status code, message and time.
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



    /**
     * Handles the {@link InvalidInputException} thrown.
     *
     * @param invalidInputException The {@link InvalidInputException} to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code.
     */
    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handlingInvalidInputException(InvalidInputException invalidInputException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), invalidInputException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    /**
     * Handles the {@link InvalidTokenException} thrown.
     *
     * @param invalidTokenException The {@link InvalidTokenException} to handle.
     *
     * @return A ResponseEntity containing the error response and HTTP status code.
     */
    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handlingInvalidInputException(InvalidTokenException invalidTokenException){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), invalidTokenException.getMessage(), LocalTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}
