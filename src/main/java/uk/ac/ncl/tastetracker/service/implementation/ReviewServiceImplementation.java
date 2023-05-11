package uk.ac.ncl.tastetracker.service.implementation;

import uk.ac.ncl.tastetracker.config.jwt.JWTService;
import uk.ac.ncl.tastetracker.dto.ReviewDTO;
import uk.ac.ncl.tastetracker.entity.Restaurant;
import uk.ac.ncl.tastetracker.entity.Review;
import uk.ac.ncl.tastetracker.entity.User;
import uk.ac.ncl.tastetracker.exception.custom.InvalidCredentialsException;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceExistsException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceNotFoundException;
import uk.ac.ncl.tastetracker.dto.dtomapper.ReviewDTOMapper;
import uk.ac.ncl.tastetracker.requestBody.ReviewSubmit;
import uk.ac.ncl.tastetracker.repository.RestaurantRepository;
import uk.ac.ncl.tastetracker.repository.ReviewRepository;
import uk.ac.ncl.tastetracker.repository.UserRepository;
import uk.ac.ncl.tastetracker.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;



/**
 * ReviewServiceImplementation class implements the ReviewService interface that provides methods to create, read, update, and delete restaurant reviews.
 * It implements methods for creating a review and fetching all the reviews.
 * It contains all the review based server logics and functions.
 *
 * @author Surendhar Chandran Jayapal
 */
@Service
public class ReviewServiceImplementation  implements ReviewService {

    /**
     * Repository interface for Restaurant entity to handle database operations.
     */
    private final ReviewRepository reviewRepository;

    /**
     * Repository interface for Restaurant entity to handle database operations.
     */
    private final RestaurantRepository restaurantRepository;

    /**
     * Repository interface for User entity to handle database operations.
     */
    private final UserRepository userRepository;

    /**
     * The ReviewDTOMapper to map Review entities to  DTOs.
     */
    private final ReviewDTOMapper reviewDTOMapper;

    /**
     * Service class to handle JWT-related operations.
     */
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
     * @throws ResourceNotFoundException if the restaurant or user associated with the review do not exist
     * @throws InvalidCredentialsException if the token is expired or invalid.
     */
    @Override
    public ReviewDTO createReview(Long restaurantId, ReviewSubmit reviewSubmit) {

        if (restaurantId == null || Double.isNaN(restaurantId.doubleValue()) ) {
            throw new InvalidInputException("Invalid Restaurant ID");
        }

        //Checks if the restaurantId is valid
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));


        String token = reviewSubmit.getToken();
        User user = validateToken(token);
        validateReview(reviewSubmit.getRating(), reviewSubmit.getComment());


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

        //Maps Review to ReviewDTO
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

        //To check if the restaurantId is null or not a number
        if (restaurantId == null || Double.isNaN(restaurantId.doubleValue()) ) {
            throw new InvalidInputException("Invalid Restaurant ID");
        }

        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        List<Review> reviews = restaurant.getReviews();

        //Maps a list of Reviews to a list of ReviewDTO
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
     * @param token JWT that needs to be validated
     *
     * @return user object
     */
    private User validateToken(String token)
    {
        if(token == null || token.length() <= 0 || token.matches("(?i)null")) {
            throw new InvalidInputException("Invalid token");
        }

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
                throw new InvalidCredentialsException("Token does not belong to the user");
            }
        }
        else {
            throw new InvalidCredentialsException("Token expired");
        }
    }




    /**
     * This method validates the rating and comment of the review posted
     *
     * @param rating rating of the restaurant reviewed
     * @param comment comments in the review.
     *
     * @throws InvalidInputException if the comment or rating is invalid and not as per the allowed requirements
     */
    private void validateReview(Double rating, String comment) {
        if (rating < 0.5 || rating > 5) {
            throw new InvalidInputException("Rating should be from 0.5 to 5");
        } else if (rating.isInfinite() || rating.isNaN()) {
            throw new InvalidInputException("Invalid rating");
        }

        if (comment == null || comment.matches("(?i)null")) {
            throw new InvalidInputException("Invalid comment");
        }

    }


}
