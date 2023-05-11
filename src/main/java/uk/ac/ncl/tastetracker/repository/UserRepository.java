package uk.ac.ncl.tastetracker.repository;

import uk.ac.ncl.tastetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * UserRepository interface extends JpaRepository to perform basic CRUD operations and provides additional methods
 * to retrieve User entities from the database.
 *
 * @author Jiang He
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
