package com.app.foodfinder.utils;

import com.app.foodfinder.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


/**
 * This class represents the user response which contains UserDTO and token, which is sent to the client when
 * during a successful user registration and login.
 * It uses Lombok annotations to generate getters, all argument constructor, equals/hashcode and toString methods at compile-time.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */

@Data
@Getter
@AllArgsConstructor
public final class UserResponse {

    private final UserDTO userObj;
    private final String token;
}
