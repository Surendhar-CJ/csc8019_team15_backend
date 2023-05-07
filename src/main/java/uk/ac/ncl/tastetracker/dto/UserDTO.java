package uk.ac.ncl.tastetracker.dto;

/**
 * This record represents a UserDTO (Data Transfer Object), denoting the class as immutable to stop the external sources to manipulating it.
 * The purpose of this record is to provide a representation of a User object to be sent as a response to
 * a client request.
 *
 * @param id the ID of the user
 * @param username the Username of the user
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (22-04-2023)
 */
public record UserDTO (

    Long id,
    String username

)
{ }
