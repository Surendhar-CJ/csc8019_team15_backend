package com.app.foodfinder.dto.dtomapper;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.entity.Review;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public final class ReviewDTOMapper implements Function<Review, ReviewDTO> {

    @Override
    public ReviewDTO apply(Review review) {
        return new ReviewDTO(review.getReviewId(), review.getUser().getUsername(), review.getComment(), review.getRating(), review.getRestaurant().getRestaurantID(), review.getUser().getId());
    }
}
