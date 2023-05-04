package com.app.foodfinder.model;

public final class RegexPattern {

    /**
     * RFC 5322 regular expression for email validation
     */
    public static final String EMAIL_PATTERN= "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    /**
     * Username valid characters
     */
    public static final String USERNAME_PATTERN =  "^[a-zA-Z]+[a-zA-Z0-9._]{3,20}+$";

    /**
     * Password valid characters
     */
    public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9_]{5,}$";
}
