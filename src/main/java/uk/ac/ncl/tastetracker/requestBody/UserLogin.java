package uk.ac.ncl.tastetracker.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * This class represents the user login which contains username and password.
 * It uses Lombok annotations to generate getters, setters, all argument constructor, equals/hashcode and toString methods.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.4 (01-05-2023)
 */
@Data
@AllArgsConstructor
public class UserLogin {

    /**
     * Represents the username of the user entered during login.
     */
    private String username;

    /**
     * Represents the password of the user entered during login.
     */
    private String password;
}
