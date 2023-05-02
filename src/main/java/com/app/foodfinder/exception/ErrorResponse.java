package com.app.foodfinder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;


/**
 * This class represents an error response object that is returned to the client when an error occurs during API processing.
 * It uses Lombok annotations to generate getters, setters, and constructors at compile-time.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private int httpStatusCode;
    private String message;
    private LocalTime timeStamp;
}
