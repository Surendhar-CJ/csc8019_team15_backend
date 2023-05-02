package com.app.foodfinder.controller;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.config.jwt.JWTService;
import com.app.foodfinder.exception.custom.InvalidPasswordException;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.exception.custom.UserExistsException;
import com.app.foodfinder.model.UserLogin;
import com.app.foodfinder.model.UserResponse;
import com.app.foodfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


/**
 * The UserController class handles REST API requests for user registration, login, retrieval, and deletion.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@RestController
@RequestMapping("/food_finder/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;



    /**
     * @param userService the {@link UserService} instance to use for handling user-related operations
     * @param jwtService the {@link JWTService} instance to use for handling JWT-related operations
     */
    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }



    /**
     * Handles HTTP POST requests to "/food_finder/users/register".
     *
     * @param user the User object containing user information to register
     *
     * @return a ResponseEntity with a JWT token string and an HTTP status code of 201 CREATED.
     *
     * @throws UserExistsException if the username or email address is already in use with a status code of 409 CONFLICT.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) throws UserExistsException {
        userService.userRegister(user);

        final String jwtToken = jwtService.generateToken(user.getUsername());

        return new ResponseEntity<String>(jwtToken, HttpStatus.CREATED);
    }



    /**
     * Handles HTTP POST requests to "/food_finder/users/login".
     *
     * @param userLogin the {@link UserLogin} object containing user login information
     *
     * @return a ResponseEntity with a UserResponse object that contains UserDTO object and JWT token string with an HTTP status code of 200 OK.
     *
     * @throws UsernameNotFoundException if the username of the userLogin is not found with a status code 404 NOT FOUND.
     * @throws InvalidPasswordException if the password does not match the password of the user with a status code 401 UNAUTHORIZED.
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLogin userLogin) throws UsernameNotFoundException, InvalidPasswordException {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        UserDTO userDTO = userService.userLogin(username, password);

        final String jwtToken = jwtService.generateToken(username);

        return new ResponseEntity<UserResponse>(new UserResponse(userDTO, jwtToken), HttpStatus.OK);
    }



    /**
     *Handles HTTP GET requests to "/food_finder/users/{id}".
     *
     * @param id the ID of the user to retrieve.
     *
     * @return a ResponseEntity with a {@link UserDTO} object and an HTTP status code of 302 FOUND.
     *
     * @throws ResourceNotFoundException if the user ID is not found with a status code 404 NOT FOUND.
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<UserDTO>(user, HttpStatus.FOUND);
    }



    /**
     * Handles HTTP DELETE requests to "/food_finder/users/{id}".
     *
     * @param id the ID of the user to delete.
     *
     * @return a ResponseEntity with a success message and an HTTP status code of 200 OK.
     *
     * @throws ResourceNotFoundException if the user ID is not found with a status code 404 NOT FOUND.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
