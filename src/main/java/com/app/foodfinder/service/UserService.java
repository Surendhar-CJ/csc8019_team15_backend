package com.app.foodfinder.service;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;

/**
 * Defines methods for managing user accounts and authentication.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface UserService {


    /**
     * Registers a new user with the provided user data.
     *
     * @param user the data for the user to register
     */
    void userRegister(User user);



    /**
     * Authenticates a user with the specified username and password, and returns a UserDTO containing the user's data
     * if the authentication is successful.
     *
     * @param username the username of the user to authenticate
     * @param password the password of the user to authenticate
     *
     * @return a UserDTO containing the authenticated user's data
     */
    UserDTO userLogin(String username, String password);


}
