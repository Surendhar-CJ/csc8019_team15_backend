package uk.ac.ncl.tastetracker.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * UserLogin class represents the user login which contains username and password.
 * It uses Lombok annotations to generate getters, setters, all argument constructor, equals/hashcode and toString methods.
 *
 * @author Jiang He
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
