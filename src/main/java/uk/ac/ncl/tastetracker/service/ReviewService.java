package uk.ac.ncl.tastetracker.service;

import uk.ac.ncl.tastetracker.dto.ReviewDTO;
import uk.ac.ncl.tastetracker.requestBody.ReviewSubmit;
import java.util.List;


/**
 * Defines methods for accessing restaurant data.
 * This interface serves the interface for implementing Review based server logic and functions.
 * It has two methods, one method is to create review and the another method is to fetch all the reviews.
 *
 * @author Surendhar Chandran Jayapal
 */
public interface ReviewService {


    /**
     * Creates a new review for a restaurant based on the provided review data.
     *
     * @param reviewSubmit the data for the review to create
     *
     * @return ReviewDTO that represents the created review
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
