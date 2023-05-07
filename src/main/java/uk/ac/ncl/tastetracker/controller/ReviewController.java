package uk.ac.ncl.tastetracker.controller;

import uk.ac.ncl.tastetracker.dto.ReviewDTO;
import uk.ac.ncl.tastetracker.entity.Review;
import uk.ac.ncl.tastetracker.exception.custom.InvalidCredentialsException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceNotFoundException;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceExistsException;
import uk.ac.ncl.tastetracker.requestBody.ReviewSubmit;
import uk.ac.ncl.tastetracker.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



/**
 * The ReviewController class defines REST API endpoints for managing reviews of restaurants and
 * handles HTTP requests and returns the response in a RESTful way.
 * This class contains two endpoints, one for submitting a review and one fetching all the reviews of a restaurant.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.1 (22-04-2023)
 */
@RestController
@RequestMapping("/food_finder/restaurants")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {


    /**
     * Service {@link ReviewService} for handling Review related operations
     */
    private final ReviewService reviewService;





    /**
     * Constructs a new ReviewController object with the specified {@link ReviewService} object.
     *
     * @param reviewService ReviewService object to use for dependency injection.
     */
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }





    /**
     * Handles HTTP POST requests to "/food_finder/restaurants/reviews/{restaurantId}".
     * Expects a {@link ReviewSubmit} object in the request body, which contains the review token, review rating and review comment and
     * the restaurantID to which the review is being submitted for.
     * A successful request returns a ResponseEntity with a {@link ReviewDTO} object representing a {@link Review} with an
     * HTTP Status code : 201 CREATED
     *
     * @param reviewSubmit ReviewSubmit object containing the review content and the user ID.
     * @param restaurantId ID of the restaurant being reviewed.
     *
     * @return a ResponseEntity of ReviewDTO representing the created Review with HTTP status code : 201 CREATED.
     *
     * @throws ResourceExistsException if the review is submitted by the user already for the restaurant with restaurantId passed
     *         with a status code 409 CONFLICT.
     * @throws ResourceNotFoundException if the restaurantId is not found with a status code : 404 NOT FOUND.
     * @throws InvalidCredentialsException if the token is expired or invalid with a status code : 403 FORBIDDEN.
     * @throws InvalidInputException if the rating or comment is not a valid and accepted one based on the requirements
     *         with a status code : 400 BAD REQUEST.
     */
    @PostMapping("/reviews/{restaurantId}")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable("restaurantId") Long restaurantId, @RequestBody ReviewSubmit reviewSubmit){
        return new ResponseEntity<ReviewDTO>(reviewService.createReview(restaurantId, reviewSubmit), HttpStatus.CREATED);
    }





    /**
     * Handles HTTP GET requests to "/food_finder/restaurants/reviews/{restaurantId}".
     * This endpoint expects the restaurantId in the request parameter.
     * A successful request returns a ResponseEntity with a {@link ReviewDTO} objects representing a list of {@link Review} objects
     * associated with the Restaurant with restaurantId passed in the parameter.
     *
     * @param restaurantId the ID of the restaurant to retrieve reviews for.
     *
     * @return a list of {@link ReviewDTO} objects representing all the reviews for the specified restaurant.
     *
     * @throws ResourceNotFoundException if the restaurantId is not found with a status code : 404 NOT FOUND.
     */
    @GetMapping("/reviews/{restaurantId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@PathVariable("restaurantId") Long restaurantId) {
        return new ResponseEntity<List<ReviewDTO>>(reviewService.getAllReviews(restaurantId), HttpStatus.OK);
    }



}
