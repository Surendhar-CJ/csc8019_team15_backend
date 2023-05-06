package uk.ac.ncl.tastetracker.controller;

import uk.ac.ncl.tastetracker.config.jwt.TokenBlacklist;
import uk.ac.ncl.tastetracker.dto.UserDTO;
import uk.ac.ncl.tastetracker.entity.User;
import uk.ac.ncl.tastetracker.config.jwt.JWTService;
import uk.ac.ncl.tastetracker.exception.custom.InvalidPasswordException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceExistsException;
import uk.ac.ncl.tastetracker.utils.UserLogin;
import uk.ac.ncl.tastetracker.utils.UserResponse;
import uk.ac.ncl.tastetracker.utils.EmailService;
import uk.ac.ncl.tastetracker.service.UserService;
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
    private final TokenBlacklist tokenBlacklist;

    private final EmailService emailService;






    /**
     * @param userService the {@link UserService} instance to use for handling user-related operations
     * @param jwtService the {@link JWTService} instance to use for handling JWT-related operations
     */
    @Autowired
    public UserController(UserService userService, JWTService jwtService, TokenBlacklist tokenBlacklist, EmailService emailService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenBlacklist = tokenBlacklist;
        this.emailService = emailService;
    }



    /**
     * Handles HTTP POST requests to "/food_finder/users/register".
     *
     * @param user the User object containing user information to register
     *
     * @return a ResponseEntity with an HTTP status code of 201 CREATED.
     *
     * @throws ResourceExistsException if the username or email address is already in use with a status code of 409 CONFLICT.
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user) throws ResourceExistsException {
        userService.userRegister(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
     * Handles HTTP POST requests to "/food_finder/users/login".
     *
     * @param token token to be blacklisted
     *
     * @return Response Entity with a status code 200 OK
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String token) {
        tokenBlacklist.addTokenToBlacklist(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }



  /*  @PostMapping("/password-reset")
    public ResponseEntity<UserDTO> userResetPassword(@RequestBody User user) throws ResourceExistsException {
        UserDTO userDto = userService.userResetPassword(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<UserResponse> userChangePassword(@RequestBody User user) throws ResourceExistsException {
        String username = user.getUsername();
        String password = user.getPassword();

        UserDTO userDTO = userService.userChangePassword(username, password);
        final String jwtToken = jwtService.generateToken(username);

        return new ResponseEntity<>(new UserResponse(userDTO, jwtToken), HttpStatus.OK);
    } */


}
