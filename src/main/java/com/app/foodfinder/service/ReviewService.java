package com.app.foodfinder.service;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.model.ReviewModel;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(ReviewModel reviewModel);

    List<ReviewDTO> getAllReviews(Long restaurantId);

    ReviewDTO updateReview(Long reviewId, ReviewModel reviewModel);

    void deleteReview(Long reviewId, Long userId);


}
