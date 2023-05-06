package com.app.foodfinder.service.implementation;

import com.app.foodfinder.config.jwt.JWTService;
import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.entity.Review;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.exception.custom.InvalidTokenException;
import com.app.foodfinder.exception.custom.ResourceExistsException;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.dto.dtomapper.ReviewDTOMapper;
import com.app.foodfinder.utils.ReviewSubmit;
import com.app.foodfinder.repository.RestaurantRepository;
import com.app.foodfinder.repository.ReviewRepository;
import com.app.foodfinder.repository.UserRepository;
import com.app.foodfinder.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
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
    private final JWTService jwtService;



    /**
     * Constructor for ReviewServiceImplementation that initializes the ReviewRepository, RestaurantRepository, UserRepository,
     * ReviewDTOMapper, JWTService objects using dependency injection.
     *
     * @param reviewRepository - repository for Review entity
     * @param restaurantRepository - repository for Restaurant entity
     * @param userRepository - repository for User entity
     * @param reviewDTOMapper - mapper for converting Review entities to DTOs
     * @param jwtService - jwtService to validate the token and username
     */
    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, ReviewDTOMapper reviewDTOMapper, JWTService jwtService) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.reviewDTOMapper = reviewDTOMapper;
        this.jwtService = jwtService;
    }



    /**
     * This method creates a review based on the ReviewSubmit object
     * and stores it in the database and maps the created review to a ReviewDTO.
     *
     * @param reviewSubmit - review submission DTO containing the review details
     *
     *
     * @throws NullPointerException if reviewSubmit is null
     * @throws ResourceNotFoundException if the restaurant or user associated with the review do not exist
     * @throws InvalidTokenException if the token is expired or invalid.
     */
    @Override
    public ReviewDTO createReview(Long restaurantId, ReviewSubmit reviewSubmit) {

        //Checks if the restaurantId is valid
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        String token = reviewSubmit.getToken();
        //Validates the token and returns the user object
        User user = validateToken(token);

        //Check if the user has already submitted a review for the restaurant
        Review userReview = reviewRepository.findByUserAndRestaurant(user, restaurant);
        if (userReview != null) {
            throw new ResourceExistsException("You have submitted a review already");
        }

        //Adds a review
        Review review = new Review(reviewSubmit.getRating(), reviewSubmit.getComment(), user, restaurant);
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
                .map(reviewDTOMapper)
                .collect(Collectors.toList());
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



    /**
     * This method validates the token and returns the user object.
     *
     * @param token JWT
     * @return user object
     */
    private User validateToken(String token)
    {
        String username = jwtService.getUsernameFromToken(token);

        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        if(!jwtService.isTokenExpired(token)) {
            if (username.equals(user.getUsername())) {
                return user;
            }
            else {
                throw new InvalidTokenException("Token does not belong to the user");
            }
        } else {
            throw new InvalidTokenException("Token expired");
        }
    }


}
