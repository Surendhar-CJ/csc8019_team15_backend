package uk.ac.ncl.tastetracker.utils;

/**
 * Regex pattern class contains Regex patterns for email address, username and password.
 * These are used to validate the user details with specific requirement based on regex pattern.
 *
 * @author Jiang He
 */
public final class RegexPattern {

    /**
     * RFC 5322 regular expression for email validation
     */
    public static final String EMAIL_PATTERN= "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]{3,20}+$";

    /**
     * Username valid characters
     */
    public static final String USERNAME_PATTERN =  "^[a-zA-Z][a-zA-Z0-9._]{3,}$";

    /**
     * Password valid characters
     */
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$";
}
