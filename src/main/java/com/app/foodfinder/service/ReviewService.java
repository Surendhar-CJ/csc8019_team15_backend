package com.app.foodfinder.service;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.model.ReviewModel;

import java.util.List;

public interface ReviewService {

    //POST
    ReviewDTO createReview(ReviewModel reviewModel);

    //GET
    List<ReviewDTO> getAllReviews(Long restaurantId);

    //UPDATE
    ReviewDTO updateReview(Long reviewId, ReviewModel reviewModel);

   //DELETE
    void deleteReview(Long reviewId, Long userId);


}
