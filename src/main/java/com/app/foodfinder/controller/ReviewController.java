package com.app.foodfinder.controller;

import com.app.foodfinder.dto.ReviewDTO;
import com.app.foodfinder.model.ReviewSubmit;
import com.app.foodfinder.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food_finder/restaurants")
public class ReviewController {

    private final ReviewService reviewService;


    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    

    @PostMapping("/reviews/{restaurantId}")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewSubmit reviewSubmit, @PathVariable("restaurantId") Long restaurantId) {
        reviewSubmit.setRestaurantId(restaurantId);
        return new ResponseEntity<ReviewDTO>(reviewService.createReview(reviewSubmit), HttpStatus.CREATED);
    }



    @GetMapping("/reviews/{restaurantId}")
    public List<ReviewDTO> getAllReviews(@PathVariable("restaurantId") Long restaurantId) {
        return reviewService .getAllReviews(restaurantId);
    }



    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewSubmit reviewSubmit) {
        return new ResponseEntity<ReviewDTO>(reviewService.updateReview(reviewId, reviewSubmit), HttpStatus.OK);
    }



    @DeleteMapping("/reviews/{reviewId}/{userId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") Long reviewId, @PathVariable("userId") Long userId) {
        reviewService.deleteReview(reviewId, userId);
        return new ResponseEntity<String>("Review deleted successfully", HttpStatus.OK);
    }

}
