package uk.ac.ncl.tastetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * User class represents the User entity. An instance of the class can be represented by a field in the database.
 * This class uses Lombok annotations to generate getters, setters, no argument constructor, equals/hashcode and toString methods.
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required)
 *      and not creating a schema (so, constraints are not mentioned in the fields).
 *
 * @author Jiang He
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    /**
     *  Unique identifier for the user
     * The column name corresponds to the column name "id" in the user table in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The username of the user
     * The column name corresponds to the column name "id" in the user table in the database.
     */
    @Column(name = "username")
    private String username;

    /**
     * The email address of the user
     * The column name corresponds to the column name "id" in the user table in the database.
     */
    @Column(name = "email")
    private String email;

    /**
     * The password of the user. The password is hashed and stored in the database using BCryptPasswordEncoder
     * The column name corresponds to the column name "id" in the user table in the database.
     */
    @Column(name = "password")
    private String password;


    /**
     * Constructs a User object with username, email, and password.
     * ID is not included as it is  auto-generated.
     *
     * @param username Username entered by the user
     * @param email Email address of the user
     * @param password password of the user
     */
    public User(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
