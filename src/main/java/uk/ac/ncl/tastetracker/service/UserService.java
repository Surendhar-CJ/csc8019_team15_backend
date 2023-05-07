package uk.ac.ncl.tastetracker.service;

import uk.ac.ncl.tastetracker.dto.UserDTO;
import uk.ac.ncl.tastetracker.entity.User;

/**
 * Defines methods for managing user accounts and authentication.
 *
 * @author Jiang He
 * @version 1.5 (06-05-2023)
 * @since 1.1 (22-04-2023)
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
