package uk.ac.ncl.tastetracker.dto;

/**
 * This record represents a UserDTO (Data Transfer Object), denoting it as immutable to stop the external sources to manipulating it.
 * The purpose of this record is to provide a representation of a User object to be sent as a response to
 * a client request.
 * By default, the record denotes all the fields as private and final and contains only getter methods.
 *
 * @param id the ID of the user
 * @param username the Username of the user
 *
 * @author Jiang He
 */
public record UserDTO (

    Long id,
    String username

)
{ }
