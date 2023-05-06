package com.app.foodfinder.service.implementation;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.exception.custom.InvalidInputException;
import com.app.foodfinder.exception.custom.InvalidPasswordException;
import com.app.foodfinder.dto.dtomapper.UserDTOMapper;
import com.app.foodfinder.exception.custom.ResourceExistsException;
import com.app.foodfinder.utils.RegexPattern;
import com.app.foodfinder.repository.UserRepository;
import com.app.foodfinder.utils.EmailService;
import com.app.foodfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



/**
 * This class implements the UserService interface to interact with UserRepository.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Service
public class UserServiceImplementation implements UserService
{
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;


    /**
     * Constructor for UserServiceImplementation class with the specified dependencies.
     *
     * @param userRepository repository for User entity
     * @param userDTOMapper UserDTOMapper class that maps User entities to UserDTO data transfer objects
     * @param bCryptPasswordEncoder BCryptPasswordEncoder class that performs password hashing.
     */
    @Autowired
    public UserServiceImplementation(UserRepository userRepository, UserDTOMapper userDTOMapper, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDTOMapper = userDTOMapper;
        this.emailService = emailService;
    }



    /**
     * This method registers a new user with the provided information and returns a UserDTO containing the saved user's data.
     *
     * @param user the User entity to register
     *
     * @throws ResourceExistsException if a user with the same username or email already exists in the repository
     * @throws InvalidInputException if the email, username or password is not as per the requirements
     */
    @Override
    public void userRegister(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            throw new ResourceExistsException("Username already taken");
        }

        existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new ResourceExistsException("User with the email address '" + user.getEmail() + "' already exists");
        }
        else {
            emailService.sendVerificationEmail(user.getEmail(), "https://www.google.com/");
        }

        // Validate username
        if (!user.getUsername().matches(RegexPattern.USERNAME_PATTERN)) {
            throw new InvalidInputException("Username should start with a letter and contain only letters, digits, underscores and dots");
        }
        // Validate email
        if (!user.getEmail().matches(RegexPattern.EMAIL_PATTERN)) {
            throw new InvalidInputException("Invalid email address");
        }
        // Validate password
        if (!user.getPassword().matches(RegexPattern.PASSWORD_PATTERN)) {
            throw new InvalidInputException("Password should contain at least 5 characters and only contain letters, digits and underscores");
        }

            String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            userRepository.save(user);

    }



    /**
     * This method authenticates a user with the provided username and password and returns a UserDTO containing the user's data.
     *
     * @param username the username of the user to authenticate
     * @param password the password of the user to authenticate
     *
     * @return a UserDTO containing the authenticated user's data
     *
     * @throws UsernameNotFoundException if the provided username does not exist in the repository
     * @throws InvalidPasswordException if the provided password does not match the user's password
     */
    @Override
    public UserDTO userLogin(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null || !username.matches(user.getUsername()) ) {
            throw new UsernameNotFoundException("Invalid username");
        }
        else if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid Password");
        }

        return userDTOMapper.apply(user);

    }




  /*  @Override
    public UserDTO userResetPassword(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null || !existingUser.getEmail().equals(user.getEmail())) {
            throw new UsernameNotFoundException("Username cannot be found");
        }
        // need website send new password in userModel
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return userDTOMapper.apply(user);
    }

    @Override
    public UserDTO userChangePassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }
        // need website send new password in userModel
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return userDTOMapper.apply(user);
    } */


}
