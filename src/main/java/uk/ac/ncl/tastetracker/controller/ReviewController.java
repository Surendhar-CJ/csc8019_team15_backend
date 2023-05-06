package uk.ac.ncl.tastetracker.controller;

import uk.ac.ncl.tastetracker.dto.ReviewDTO;
import uk.ac.ncl.tastetracker.exception.custom.InvalidTokenException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceNotFoundException;
import uk.ac.ncl.tastetracker.utils.ReviewSubmit;
import uk.ac.ncl.tastetracker.service.ReviewService;
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
     * @return a ResponseEntity of ReviewDTO representing the created Review with HTTP status code of 201 CREATED.
     *
     * @throws ResourceNotFoundException if the restaurantId is not found with a status code of 404 NOT FOUND.
     * @throws InvalidTokenException if the token is expired or invalid with a status code of 400 BAD REQUEST.
     * @throws NullPointerException if the ReviewSubmit object is null.
     */
    @PostMapping("/reviews/{restaurantId}")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable("restaurantId") Long restaurantId, @RequestBody ReviewSubmit reviewSubmit) throws ResourceNotFoundException, InvalidTokenException {
        System.out.println(reviewSubmit);
        return new ResponseEntity<ReviewDTO>(reviewService.createReview(restaurantId, reviewSubmit), HttpStatus.CREATED);
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



}
