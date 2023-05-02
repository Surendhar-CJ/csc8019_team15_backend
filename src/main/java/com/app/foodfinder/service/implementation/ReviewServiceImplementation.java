package com.app.foodfinder.service.implementation;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.entity.Review;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.dto.dtomapper.ReviewDTOMapper;
import com.app.foodfinder.model.ReviewSubmit;
import com.app.foodfinder.repository.RestaurantRepository;
import com.app.foodfinder.repository.ReviewRepository;
import com.app.foodfinder.repository.UserRepository;
import com.app.foodfinder.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



/**
 * This class implements the ReviewService interface that provides methods to create, read, update, and delete restaurant reviews.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Service
public class ReviewServiceImplementation  implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReviewDTOMapper reviewDTOMapper;



    /**
     * Constructor for ReviewServiceImplementation that initializes the ReviewRepository, RestaurantRepository, UserRepository,
     * ReviewDTOMapper objects using dependency injection.
     *
     * @param reviewRepository - repository for Review entity
     * @param restaurantRepository - repository for Restaurant entity
     * @param userRepository - repository for User entity
     * @param reviewDTOMapper - mapper for converting Review entities to DTOs
     */
    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, ReviewDTOMapper reviewDTOMapper) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.reviewDTOMapper = reviewDTOMapper;
    }



    /**
     * This method creates a review based on the ReviewSubmit object
     * and stores it in the database and maps the created review to a ReviewDTO and returns it.
     *
     * @param reviewSubmit - review submission DTO containing the review details
     *
     * @return ReviewDTO representing the created review.
     *
     * @throws NullPointerException if reviewSubmit is null
     * @throws ResourceNotFoundException if the restaurant or user associated with the review do not exist
     */
    @Override
    public ReviewDTO createReview(ReviewSubmit reviewSubmit) {
            if (reviewSubmit == null) {
                throw new NullPointerException("Review cannot be null");
            }

            Restaurant restaurant = restaurantRepository.findById(reviewSubmit.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

            User user = userRepository.findById(reviewSubmit.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            Review review = new Review(reviewSubmit.getComment(), reviewSubmit.getRating(), user, restaurant);
            reviewRepository.save(review);

            //Updates restaurant's overall rating
            restaurant.setOverallRating(updateOverallRating(restaurant));
            restaurantRepository.save(restaurant);

            return reviewDTOMapper.apply(review);
    }



    /**
     * This method retrieves a list of Review objects from the database based on the restaurantID passed
     * and maps it to the list of ReviewDTO.
     *
     * @param restaurantId - the ID of the restaurant to retrieve reviews for
     *
     * @return List of ReviewDTOs representing the reviews for the restaurant,
     *          or an empty list if no review is present for the restaurant.
     *
     * @throws ResourceNotFoundException if the restaurant does not exist
     */
    @Override
    public List<ReviewDTO> getAllReviews(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        List<Review> reviews = restaurant.getReviews();

        return reviews.stream()
                .map(reviewDTOMapper::apply)
                .collect(Collectors.toList());
    }



    /**
     * This method updates the existing review in the database based on the ReviewSubmit object passed
     * and maps the updated review to the ReviewDTO and returns it.
     *
     * @param reviewId - ID of the review to update
     * @param reviewSubmit - review submission DTO containing the updated review details
     *
     * @return ReviewDTO representing the updated review
     *
     * @throws ResourceNotFoundException if the review or user associated with the review do not exist
     * @throws IllegalArgumentException if the user trying to update the review is not the same as the user who created the review
     */
    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewSubmit reviewSubmit) {
        Optional<Review> existingReview = reviewRepository.findById(reviewId);

        Review updatedReview = existingReview
                                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        if(!updatedReview.getUser().getId().equals(reviewSubmit.getUserId())) {
            throw new IllegalArgumentException("User can only update their reviews and not others");
        }

        updatedReview.setComment(reviewSubmit.getComment());
        updatedReview.setRating(reviewSubmit.getRating());

        reviewRepository.save(updatedReview);

        //Updates restaurant's overall rating
        Restaurant restaurant = updatedReview.getRestaurant();
        setOverallRating(restaurant);

        return reviewDTOMapper.apply(updatedReview);
    }



    /**
     * This method deletes the existing review in the database based on the reviewID and userID (the user who deletes it).
     *
     * @param reviewId - ID of the review to delete
     * @param userId - ID of the user trying to delete the review
     *
     * @throws ResourceNotFoundException if the review or user associated with the review do not exist
     * @throws IllegalArgumentException if the user trying to delete the review is not the same as the user who created the review
     */
    @Override
    public void deleteReview(Long reviewId, Long userId) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        Review existingReview = review
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        if(!existingReview.getUser().getId().equals(userId)) {
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
     * @param restaurant restaurant which is being reviewed.
     *
     * @return updated overall rating
     */
    private Double updateOverallRating(Restaurant restaurant) {
        List<Review> reviews = restaurant.getReviews();

        double updatedRating = 0;

        if(reviews.size() == 0)
            return updatedRating;

        for(Review review : reviews) {
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
     * @param restaurant restaurant which is being reviewed for
     */
    private void setOverallRating(Restaurant restaurant) {
        restaurant.setOverallRating(updateOverallRating(restaurant));
        restaurantRepository.save(restaurant);
    }



}
