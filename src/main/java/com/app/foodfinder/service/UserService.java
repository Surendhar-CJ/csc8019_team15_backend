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
     * Registers a new user with the provided user data, and returns a UserDTO containing the newly created user's data.
     *
     * @param user the data for the user to register
     * @return a UserDTO containing the newly created user's data
     */
    UserDTO userRegister(User user);



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



    /**
     * Retrieves a user with the specified ID and returns a UserDTO containing the user's data.
     *
     * @param id the ID of the user to retrieve
     * @return a UserDTO containing the retrieved user's data
     */
    UserDTO getUserById(Long id);



    /**
     * Deletes a user with the specified ID from the repository.
     *
     * @param id the ID of the user to delete
     */
    void deleteUserById(Long id);

    /**
     *
     *
     * @param user user
     * @return userDTO
     */
    UserDTO userResetPassword(User user);

    UserDTO userChangePassword(String username, String password);
}
