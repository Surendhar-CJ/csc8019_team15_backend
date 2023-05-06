package uk.ac.ncl.tastetracker.dto;

/**
 * This record represents a UserDTO (Data Transfer Object), denoting the class as immutable to stop the external sources to manipulating it.
 *
 * The purpose of this record is to provide a representation of a User object to be sent as a response to
 * a client request.
 *
 * @param id the ID of the user
 * @param username the Username of the user
 *
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public record UserDTO (

    Long id,
    String username

)
{ }
