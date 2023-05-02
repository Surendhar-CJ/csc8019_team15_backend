package com.app.foodfinder.dto;

/**
 * This record represents a ReviewDTO (Data Transfer Object), denoting the class as immutable to stop the external sources to manipulating it.
 *
 * The purpose of this record is to provide a representation of a review object to be sent as a response to
 * a client request.
 *
 * @param id Review ID
 * @param username Username of the user, who submitted the review
 * @param comment Review comments
 * @param rating Rating
 * @param restaurantId Restaurant ID of the restaurant to which the review is submitted for.
 * @param userId User ID of the user submitting the review.
 *
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public record ReviewDTO (

    Long id,
    String username,
    String comment,
    Integer rating,
    Long restaurantId,
    Long userId

)
{ }
