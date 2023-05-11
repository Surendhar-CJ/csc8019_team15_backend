package uk.ac.ncl.tastetracker.dto;

/**
 * This record represents a ReviewDTO (Data Transfer Object), denoting it as immutable to stop the external sources to manipulating it.
 * The purpose of this record is to provide a representation of a review object to be sent as a response to
 * a client request.
 * By default, the record denotes all the fields as private and final and contains only getter methods.
 *
 * @param id Review ID
 * @param username Username of the user, who submitted the review
 * @param comment Review comments
 * @param rating Rating
 *
 * @author Surendhar Chandran Jayapal
 */
public record ReviewDTO (

    Long id,
    String username,
    Double rating,
    String comment
)
{ }
