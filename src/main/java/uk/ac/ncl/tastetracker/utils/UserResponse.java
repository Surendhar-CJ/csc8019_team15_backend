package uk.ac.ncl.tastetracker.utils;

import uk.ac.ncl.tastetracker.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


/**
 * Represents the user response which contains UserDTO and token, which is sent to the client when
 * during a successful user registration and login.
 * It uses Lombok annotations to generate getters, all argument constructor, equals/hashcode and toString methods at compile-time.
 *
 * @author Surendhar Chandran Jayapal
 */
@Data
@Getter
@AllArgsConstructor
public final class UserResponse {

    /**
     * Represents the UserDTO object which contains User ID and Username.
     * Passwords are not sent back to the client for security reasons.
     */
    private final UserDTO userObj;

    /**
     * Represents the JWT generated for the user.
     */
    private final String token;
}
