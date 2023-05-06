package uk.ac.ncl.tastetracker.config;

import uk.ac.ncl.tastetracker.entity.User;
import uk.ac.ncl.tastetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Implementation of Spring Security's UserDetailsService interface.
 * This class is responsible for loading a user by their username from the database and constructing
 * a UserDetails object from the retrieved User object.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserRepository userRepository;



    /**
     * Constructs a new UserDetailsServiceImplementation instance with the given UserRepository.
     *
     * @param userRepository the repository to retrieve User objects from the database.
     */
    @Autowired
    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    /**
     * Loads a user by their username and constructs a UserDetails object from the retrieved User object.
     *
     * @param username the username of the user to load
     *
     * @return UserDetails object for the loaded user
     *
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username);
        return new UserDetailsImplementation(user);
    }
}
