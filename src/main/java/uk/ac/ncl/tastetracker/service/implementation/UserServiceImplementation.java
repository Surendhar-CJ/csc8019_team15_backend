package uk.ac.ncl.tastetracker.service.implementation;

import uk.ac.ncl.tastetracker.dto.UserDTO;
import uk.ac.ncl.tastetracker.entity.User;
import uk.ac.ncl.tastetracker.exception.custom.InvalidCredentialsException;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.dto.dtomapper.UserDTOMapper;
import uk.ac.ncl.tastetracker.exception.custom.ResourceExistsException;
import uk.ac.ncl.tastetracker.utils.RegexPattern;
import uk.ac.ncl.tastetracker.repository.UserRepository;
import uk.ac.ncl.tastetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * This class implements the UserService interface to interact with UserRepository.
 *
 * @author Jiang He
 * @version 1.5 (06-05-2023)
 * @since 1.1 (22-04-2023)
 */
@Service
public class UserServiceImplementation implements UserService
{

    /**
     * UserRepository to interact with the User entity in the database.
     */
    private final UserRepository userRepository;

    /**
     * UserDTOMapper to map between User entities and DTOs.
     */
    private final UserDTOMapper userDTOMapper;

    /**
     * BCryptPasswordEncoder to encode and decode user passwords.
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    /**
     * Constructor for UserServiceImplementation class with the specified dependencies.
     *
     * @param userRepository repository for User entity
     * @param userDTOMapper UserDTOMapper class that maps User entities to UserDTO data transfer objects
     * @param bCryptPasswordEncoder BCryptPasswordEncoder class that performs password hashing.
     */
    @Autowired
    public UserServiceImplementation(UserRepository userRepository, UserDTOMapper userDTOMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDTOMapper = userDTOMapper;
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

        //Validates user credentials against the requirements
        User validatedUser = validateUserInput(user);

        validatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(validatedUser);
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
     * @throws InvalidCredentialsException if the provided password does not match the user's password
     */
    @Override
    public UserDTO userLogin(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null || !username.matches(user.getUsername()) || username.matches("(?i)null")){
            throw new UsernameNotFoundException("Invalid username");
        }
        else if(!bCryptPasswordEncoder.matches(password, user.getPassword()) || username.matches("(?i)null")) {
            throw new InvalidCredentialsException("Invalid Password");
        }

        return userDTOMapper.apply(user);
    }






    /**
     * This method validates user inputs against the required conditions.
     *
     * @param user user object that contains username, email and password.
     *
     * @return User  user object after validating.
     *
     * @throws InvalidInputException if the email, username or password is not as per the requirements.
     */
    private User validateUserInput(User user) {

        if (user.getUsername() == null || user.getUsername().matches("(?i)null")) {
            throw new InvalidInputException("Invalid username");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResourceExistsException("Username already taken");
        }

        if (!user.getUsername().matches(RegexPattern.USERNAME_PATTERN)) {
            throw new InvalidInputException("Username should start with a letter and contain only letters, digits, underscores and dots, and must be at least 4 characters long");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ResourceExistsException("User with the email address '" + user.getEmail() + "' already exists");
        }

        if (!user.getEmail().matches(RegexPattern.EMAIL_PATTERN)) {
            throw new InvalidInputException("Invalid email address");
        }

        if (user.getPassword() == null || user.getPassword().matches("(?i)null")) {
            throw new InvalidInputException("Invalid password, please try again");
        }

        if (!user.getPassword().matches(RegexPattern.PASSWORD_PATTERN)) {
            throw new InvalidInputException("Password should contain at least 8 characters including at least one number and one symbol from !@#$%^&*");
        }

        return user;
    }


}
