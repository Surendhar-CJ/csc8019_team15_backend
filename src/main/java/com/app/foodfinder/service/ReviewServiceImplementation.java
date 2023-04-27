package com.app.foodfinder.service;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.entity.Review;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.exception.NotFoundException;
import com.app.foodfinder.mapper.ReviewDTOMapper;
import com.app.foodfinder.model.ReviewModel;
import com.app.foodfinder.repository.RestaurantRepository;
import com.app.foodfinder.repository.ReviewRepository;
import com.app.foodfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImplementation  implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReviewDTOMapper reviewDTOMapper;

    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, ReviewDTOMapper reviewDTOMapper)
    {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.reviewDTOMapper = reviewDTOMapper;
    }

    @Override
    public ReviewDTO createReview(ReviewModel reviewModel)
    {
            if (reviewModel == null)
            {
                throw new IllegalArgumentException("Review cannot be null");
            }

            Restaurant restaurant = restaurantRepository.findById(reviewModel.getRestaurantId())
                    .orElseThrow(() -> new NotFoundException("Restaurant not found"));

            User user = userRepository.findById(reviewModel.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

            Review review = new Review(reviewModel.getComment(), reviewModel.getRating(), user, restaurant);

            reviewRepository.save(review);

            //Updates restaurant's overall rating
            restaurant.setOverallRating(updateOverallRating(restaurant));
            restaurantRepository.save(restaurant);

            return reviewDTOMapper.apply(review);
    }

    @Override
    public List<ReviewDTO> getAllReviews(Long restaurantId)
    {
        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null)
        {
            throw new NotFoundException("Restaurant not found");
        }

        List<Review> reviews = restaurant.getReviews();

        return reviews.stream()
                .map(reviewDTOMapper::apply)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewModel reviewModel)
    {
        Optional<Review> existingReview = reviewRepository.findById(reviewId);

        Review updatedReview = existingReview
                                .orElseThrow(() -> new NotFoundException("Review not found"));

        if(!updatedReview.getUser().getId().equals(reviewModel.getUserId()))
        {
            throw new IllegalArgumentException("User can only update their reviews and not others");
        }

        updatedReview.setComment(reviewModel.getComment());
        updatedReview.setRating(reviewModel.getRating());

        reviewRepository.save(updatedReview);

        //Updates restaurant's overall rating
        Restaurant restaurant = updatedReview.getRestaurant();

        setOverallRating(restaurant);

        return reviewDTOMapper.apply(updatedReview);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId)
    {
        Optional<Review> review = reviewRepository.findById(reviewId);

        Review existingReview;

        if(review.isPresent())
        {
            existingReview = review.get();
        }
        else
        {
            throw new NotFoundException("Review not found");
        }

        if(!existingReview.getUser().getId().equals(userId))
        {
            throw new IllegalArgumentException("User can only delete their reviews and not others");
        }

        reviewRepository.deleteById(reviewId);

        //Updates overall rating
        Restaurant restaurant = existingReview.getRestaurant();
        setOverallRating(restaurant);

    }

    /**
     * This method returns the updated overall rating of the restaurant
     *
     * @param restaurant - restaurant
     * @return updated overall rating
     */
    private Double updateOverallRating(Restaurant restaurant)
    {
        List<Review> reviews = restaurant.getReviews();

        double updatedRating = 0;

        if(reviews.size() == 0)
            return updatedRating;

        for(Review review : reviews)
        {
            updatedRating += review.getRating();
        }

        double overallRating = updatedRating / reviews.size();

        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(overallRating));
    }

    /**
     * This method sets the updated overall rating of the restaurant
     *
     * @param restaurant
     */
    private void setOverallRating(Restaurant restaurant)
    {
        restaurant.setOverallRating(updateOverallRating(restaurant));

        restaurantRepository.save(restaurant);
    }

}
