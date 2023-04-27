package com.app.foodfinder.exception;

import java.time.LocalTime;

public class ErrorResponse {

    private int httpStatusCode;
    private String message;
    private LocalTime timeStamp;

    public ErrorResponse(int httpStatusCode, String message, LocalTime timeStamp)
    {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
