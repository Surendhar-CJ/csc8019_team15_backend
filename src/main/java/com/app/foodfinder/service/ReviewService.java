package com.app.foodfinder.service;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.utils.ReviewSubmit;
import java.util.List;


/**
 * This interface defines methods for accessing restaurant data.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface ReviewService {


    /**
     * Creates a new review for a restaurant based on the provided review data.
     *
     * @param reviewSubmit the data for the review to create
     */
    ReviewDTO createReview(Long restaurantId, ReviewSubmit reviewSubmit);



    /**
     * Retrieves all reviews for a specified restaurant and returns a list of ReviewDTOs containing the reviews' data.
     *
     * @param restaurantId the ID of the restaurant for which to retrieve reviews
     * @return a list of ReviewDTOs containing the retrieved reviews' data
     */
    List<ReviewDTO> getAllReviews(Long restaurantId);

}
