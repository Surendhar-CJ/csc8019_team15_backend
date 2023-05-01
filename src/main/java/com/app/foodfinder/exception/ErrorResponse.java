package com.app.foodfinder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private int httpStatusCode;
    private String message;
    private LocalTime timeStamp;

    public static class UserExistsException extends RuntimeException {

        public UserExistsException(String message) {
            super(message);
        }
    }
}
