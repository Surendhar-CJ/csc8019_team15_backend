package com.app.foodfinder.repository;

import com.app.foodfinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The UserRepository interface extends JpaRepository to perform basic CRUD operations and provides additional methods
 * to retrieve User entities from the database.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface UserRepository extends JpaRepository<User, Long> {


    /**
     * Returns the User entity for the given username.
     *
     * @param username the username of the User entity to be retrieved.
     *
     * @return the User entity for the given username.
     */
    User findByUsername(String username);



    /**
     * Returns the User entity for the given email address.
     *
     * @param email the email address of the User entity to be retrieved
     *
     * @return the User entity for the given email address
     */
    User findByEmail(String email);
}
