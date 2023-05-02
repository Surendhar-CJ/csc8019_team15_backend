package com.app.foodfinder.service;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.model.ReviewSubmit;
import java.util.List;


/**
 * This interface defines methods for accessing restaurant data.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface ReviewService {


    /**
     * Creates a new review for a restaurant based on the provided review data, and returns a ReviewDTO containing the
     * newly created review's data.
     *
     * @param reviewSubmit the data for the review to create
     * @return a ReviewDTO containing the newly created review's data
     */
    ReviewDTO createReview(ReviewSubmit reviewSubmit);



    /**
     * Retrieves all reviews for a specified restaurant and returns a list of ReviewDTOs containing the reviews' data.
     *
     * @param restaurantId the ID of the restaurant for which to retrieve reviews
     * @return a list of ReviewDTOs containing the retrieved reviews' data
     */
    List<ReviewDTO> getAllReviews(Long restaurantId);



    /**
     * Updates an existing review with the specified ID based on the provided review data, and returns a ReviewDTO
     * containing the updated review's data.
     *
     * @param reviewId the ID of the review to update
     * @param reviewSubmit the data to use for updating the review
     * @return a ReviewDTO containing the updated review's data
     */
    ReviewDTO updateReview(Long reviewId, ReviewSubmit reviewSubmit);



    /**
     * Deletes an existing review with the specified ID, and throws an exception if the review was not created by the
     * user with the specified user ID.
     *
     * @param reviewId the ID of the review to delete
     * @param userId the ID of the user attempting to delete the review
     */
    void deleteReview(Long reviewId, Long userId);


}
