package uk.ac.ncl.tastetracker.dto;

/**
 * This record represents a ReviewDTO (Data Transfer Object), denoting the class as immutable to stop the external sources to manipulating it.
 * The purpose of this record is to provide a representation of a review object to be sent as a response to
 * a client request.
 *
 * @param id Review ID
 * @param username Username of the user, who submitted the review
 * @param comment Review comments
 * @param rating Rating
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.1 (22-04-2023)
 */
public record ReviewDTO (

    Long id,
    String username,
    Double rating,
    String comment
)
{ }
