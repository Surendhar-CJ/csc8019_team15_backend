package com.app.foodfinder.service.implementation;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.exception.custom.InvalidInputException;
import com.app.foodfinder.exception.custom.InvalidPasswordException;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.dto.dtomapper.UserDTOMapper;
import com.app.foodfinder.exception.custom.UserExistsException;
import com.app.foodfinder.model.RegexPattern;
import com.app.foodfinder.repository.UserRepository;
import com.app.foodfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;



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
     * @return a UserDTO containing the saved user's data
     *
     * @throws UserExistsException if a user with the same username or email already exists in the repository
     */
    @Override
    public UserDTO userRegister(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            throw new UserExistsException("Username already taken");
        }

        existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new UserExistsException("User with the email address '" + user.getEmail() + "' already exists");
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

            return userDTOMapper.apply(user);
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



    /**
     * This method retrieves a user with the specified ID and returns a UserDTO containing the user's data.
     *
     * @param id the ID of the user to retrieve
     *
     * @return a UserDTO containing the retrieved user's data
     *
     * @throws ResourceNotFoundException if no user with the specified ID exists in the repository
     */
    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return userDTOMapper.apply(user.get());
        }
        else {
            throw new ResourceNotFoundException("User not found");
        }
   }



    /**
     * This method deletes a user with the ID specified.
     *
     * @param id the ID of the user to delete.
     *
     * @throws ResourceNotFoundException if no user with the specified ID exists in the repository.
     */
    @Override
    public void deleteUserById(Long id) {
       Optional<User> user = userRepository.findById(id);

       if(user.isPresent()) {
           userRepository.deleteById(id);
       }
       else {
           throw new ResourceNotFoundException("User not found");
       }
   }

    @Override
    public UserDTO userResetPassword(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null || !existingUser.getEmail().equals(user.getEmail())) {
            throw new UserExistsException("Username cannot be found");
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
    }


}
