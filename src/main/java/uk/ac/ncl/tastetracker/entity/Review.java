package uk.ac.ncl.tastetracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Review class represents the Review entity. An instance of the class can be represented by a field in the database.
 * This class uses Lombok annotations to generate getters, setters, no argument constructor, equals/hashcode and toString methods.
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required)
 *       and not for creating the schema (so, constraints are not mentioned in the fields explicitly)
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.1 (22-04-2023)
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {


    /**
     * Unique identifier of the review
     * The column name corresponds to the column name "id" in the review table in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Represents the rating of the review
     * The column name corresponds to the column name "rating" in the review table in the database.
     */
    @Column(name = "rating")
    private Double rating;

    /**
     * Represents the comment of the review
     * The column name corresponds to the column name "comment" in the review table in the database.
     */
    @Column(name = "comment")
    private String comment;

    /**
     * Represents the User entity who gave the review
     * The column name corresponds to the column name "user_id" in the review table in the database.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Represents the Restaurant entity to which the review is given.
     * The column name corresponds to the column name "restaurant_id" in the review table in the database.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



    /**
     * Constructs a Review object with comment, rating, user and restaurant.
     * ID is not included as it is  auto-generated by the database.
     *
     * @param comment Review comment
     * @param rating Review rating
     * @param user User object of the user who submitted the review
     * @param restaurant Restaurant object of the restaurant that is being review for
     */
    public Review(Double rating, String comment, User user, Restaurant restaurant)
    {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.restaurant = restaurant;
    }



}
