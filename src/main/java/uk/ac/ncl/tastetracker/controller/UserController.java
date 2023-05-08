package uk.ac.ncl.tastetracker.controller;

import uk.ac.ncl.tastetracker.config.jwt.TokenBlacklist;
import uk.ac.ncl.tastetracker.dto.UserDTO;
import uk.ac.ncl.tastetracker.entity.User;
import uk.ac.ncl.tastetracker.config.jwt.JWTService;
import uk.ac.ncl.tastetracker.exception.custom.InvalidCredentialsException;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceExistsException;
import uk.ac.ncl.tastetracker.requestBody.UserLogin;
import uk.ac.ncl.tastetracker.utils.UserResponse;
import uk.ac.ncl.tastetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;



/**
 * The UserController class handles REST API requests for user registration, login, retrieval, and deletion.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.1 (22-04-2023)
 */
@RestController
@RequestMapping("/food_finder/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {


    /**
     * Service {@link UserService} for handling Restaurant related operations
     */
    private final UserService userService;

    /**
     * Service {@link JWTService} for handling JWT related operations
     */
    private final JWTService jwtService;

    /**
     * {@link TokenBlacklist} for validating and blacklisting the token
     */
    private final TokenBlacklist tokenBlacklist;





    /**
     * @param userService the {@link UserService} instance to use for handling user-related operations
     * @param jwtService the {@link JWTService} instance to use for handling JWT-related operations
     * @param tokenBlacklist the {@link TokenBlacklist} instance to validate and blacklist the jwt upon user logout
     */
    @Autowired
    public UserController(UserService userService, JWTService jwtService, TokenBlacklist tokenBlacklist) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenBlacklist = tokenBlacklist;
    }





    /**
     * Handles HTTP POST requests to "/food_finder/users/register".
     * This API endpoint requires {@link User} object in the request body.
     * A successful request creates a User, stores it and returns an HTTP Status code : 201 CREATED.
     *
     * @param user the User object containing user information to register
     *
     * @return a void ResponseEntity with an HTTP status code : 201 CREATED.
     *
     * @throws ResourceExistsException if the username or email address is already in use with a status code : 409 CONFLICT.
     * @throws InvalidInputException if the username or email address or password is not based on the requirements or the User object passed is null
     *         with a status code : 400 BAD REQUEST.
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user) {
        if(user == null) {
            throw new InvalidInputException("User cannot be null");
        }
        userService.userRegister(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }





    /**
     * Handles HTTP POST requests to "/food_finder/users/login".
     * This API endpoint requires {@link UserLogin} object which contains username and password in the request body.
     * A successful request returns {@link UserResponse} object containing User ID, username and a JWT.
     *
     * @param userLogin the {@link UserLogin} object containing user login information
     *
     * @return a ResponseEntity with a UserResponse object that contains UserDTO object and JWT token string with an HTTP status code : 200 OK.
     *
     * @throws UsernameNotFoundException if the username of the userLogin is not found with a status code : 404 NOT FOUND.
     * @throws InvalidCredentialsException if the password does not match the password of the user with a status code : 403 FORBIDDEN
     * @throws InvalidInputException if the UserLogin object passed is null with a status code : 400 BAD REQUEST
     *
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLogin userLogin) {
        if(userLogin == null) {
            throw new InvalidInputException("User login object passed cannot be null");
        }

        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        UserDTO userDTO = userService.userLogin(username, password);

        final String jwtToken = jwtService.generateToken(username);

        return new ResponseEntity<UserResponse>(new UserResponse(userDTO, jwtToken), HttpStatus.OK);
    }





    /**
     * Handles HTTP POST requests to "/food_finder/users/login".
     * This API endpoint requires token in the request body.
     * A successful request creates a token and blacklists it and returns a ResponseEntity with an
     * HTTP Status code : 200 OK
     *
     * @param token token to be blacklisted
     *
     * @return ResponseEntity with a status code : 200 OK.
     *
     * @throws InvalidCredentialsException if the token is invalid with an HTTP status code : 403 FORBIDDEN
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String token) {
        tokenBlacklist.addTokenToBlacklist(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
