package com.app.foodfinder.controller;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.model.ReviewSubmit;
import com.app.foodfinder.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * The ReviewController class defines REST API endpoints for managing reviews of restaurants.
 *
 * It handles HTTP requests and returns the response in a RESTful way.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@RestController
@RequestMapping("/food_finder/restaurants")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {

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
     *
     * Expects a {@link ReviewSubmit} object in the request body, which contains the review content and the user ID.
     * The restaurant ID is provided in the path parameter.
     *
     * @param reviewSubmit ReviewSubmit object containing the review content and the user ID.
     * @param restaurantId ID of the restaurant being reviewed.
     *
     * @return a ResponseEntity with a {@link ReviewDTO} object and an HTTP status code of 201 CREATED.
     *
     * @throws ResourceNotFoundException if the restaurantId is not found with a status code of 404 NOT FOUND.
     * @throws NullPointerException if the ReviewSubmit object is null.
     */
    @PostMapping("/reviews/{restaurantId}")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewSubmit reviewSubmit, @PathVariable("restaurantId") Long restaurantId) throws ResourceNotFoundException {
        reviewSubmit.setRestaurantId(restaurantId);
        return new ResponseEntity<ReviewDTO>(reviewService.createReview(reviewSubmit), HttpStatus.CREATED);
    }




    /**
     * Handles HTTP GET requests to "/food_finder/restaurants/reviews/{restaurantId}".
     *
     * @param restaurantId the ID of the restaurant to retrieve reviews for.
     *
     * @return a list of {@link ReviewDTO} objects representing all the reviews for the specified restaurant.
     *
     * @throws ResourceNotFoundException if the restaurantId is not found with a status code of 404 NOT FOUND.
     */
    @GetMapping("/reviews/{restaurantId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@PathVariable("restaurantId") Long restaurantId) throws ResourceNotFoundException {
        return new ResponseEntity<List<ReviewDTO>>(reviewService.getAllReviews(restaurantId), HttpStatus.OK);
    }




    /**
     * Handles HTTP PUT requests to "/food_finder/restaurants/reviews/{reviewId}".
     *
     * Expects a ReviewSubmit object in the request body, which contains the updated review content and the user ID.
     * The review ID is provided in the path parameter.
     *
     * @param reviewId the ID of the review to update.
     * @param reviewSubmit the ReviewSubmit object containing the updated review content and the user ID.
     *
     * @return a ResponseEntity with a {@link ReviewDTO} object and an HTTP status code of 200 OK.
     *
     * @throws ResourceNotFoundException if the reviewId is not found with a status code of 404 NOT FOUND.
     * @throws NullPointerException if the ReviewSubmit object is null.
     */
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewSubmit reviewSubmit) throws ResourceNotFoundException {
        return new ResponseEntity<ReviewDTO>(reviewService.updateReview(reviewId, reviewSubmit), HttpStatus.OK);
    }




    /**
     * Handles HTTP DELETE requests to "/food_finder/restaurants/reviews/{reviewId}/{userId}".
     *
     * Deletes the review with the specified ID if it was written by the user with the specified ID
     *
     * @param reviewId the ID of the review to delete.
     * @param userId the ID of the user who wrote the review to delete.
     *
     * @return a ResponseEntity of String containing a message and a status code 200 OK.
     *
     * @throws ResourceNotFoundException if the reviewId or userId is not found with a status code of 404 NOT FOUND.
     */
    @DeleteMapping("/reviews/{reviewId}/{userId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") Long reviewId, @PathVariable("userId") Long userId) throws ResourceNotFoundException {
        reviewService.deleteReview(reviewId, userId);
        return new ResponseEntity<String>("Review deleted successfully", HttpStatus.OK);
    }

}
