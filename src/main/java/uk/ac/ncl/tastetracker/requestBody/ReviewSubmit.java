package uk.ac.ncl.tastetracker.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * ReviewSubmit class represents a review submission made by a user for a restaurant.
 * It uses Lombok annotations to generate getters, setters, all argument constructor, equals/hashcode and toString methods.
 *
 * @author Surendhar Chandran Jayapal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSubmit {

    /**
     * Represents the JWT token to be sent by the client.
     */
    private String token;

    /**
     * Represents the rating given to the restaurant.
     */
    private Double rating;

    /**
     * Represents the comment provided in the review.
     */
    private String comment;


}
