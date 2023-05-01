package com.app.foodfinder.service;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.model.ReviewSubmit;
import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(ReviewSubmit reviewSubmit);

    List<ReviewDTO> getAllReviews(Long restaurantId);

    ReviewDTO updateReview(Long reviewId, ReviewSubmit reviewSubmit);

    void deleteReview(Long reviewId, Long userId);


}
