package com.app.foodfinder.controller;


import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.model.UserLocation;
import com.app.foodfinder.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



/**
 * The RestaurantController class defines REST API endpoints for fetching restaurants details.
 *
 * This class handles HTTP requests and returns the response in a RESTful way.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@RestController
@RequestMapping("/food_finder")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestaurantController {

    private final RestaurantService restaurantService;


    /**
     * Constructs a new RestaurantController object with the specified {@link RestaurantService} object.
     *
     * @param restaurantService the RestaurantService object is injected into the RestaurantController.
     */
    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }



    /**
     * Handles HTTP GET requests to "/food_finder/restaurants/{restaurantId}".
     *
     * @param restaurantId the ID of the restaurant to be retrieved.
     *
     * @return a ResponseEntity with a {@link RestaurantDTO} object and an HTTP status code of 200 OK.
     *
     * @throws ResourceNotFoundException if a restaurant with passed restaurantId is not found with status code 404.
     */
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable("restaurantId") Long restaurantId) throws ResourceNotFoundException {
        return new ResponseEntity<RestaurantDTO>(restaurantService.getRestaurantById(restaurantId), HttpStatus.OK);
    }




    /**
     * Handles HTTP GET requests to "/food_finder/restaurants/{restaurantId}" and requires UserLocation object.
     *
     * @param restaurantId the ID of the restaurant to be retrieved.
     *
     * @return a ResponseEntity with a {@link RestaurantDTO} object and an HTTP status code of 200 OK.
     *
     * @throws ResourceNotFoundException if a restaurant with passed restaurantId is not found with status code 404.
     */
    @PostMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantByIdWithUserLocation(@PathVariable("restaurantId") Long restaurantId, @RequestBody UserLocation userLocation) throws ResourceNotFoundException {
        return new ResponseEntity<RestaurantDTO>(restaurantService.getRestaurantByIdWithUserLocation(restaurantId, userLocation.getLatitude(), userLocation.getLongitude()), HttpStatus.OK);
    }




    /**
     * Handles HTTP POST requests to "/food_finder/restaurants".
     *
     * Returns a ResponseEntity with a list of {@link RestaurantDTO} objects
     * that represents all the open restaurants within 1-mile radius from user's location
     * and HTTP status code of 200 OK.
     *
     * @param userLocation - UserLocation object which contains user's latitude and user's longitude.
     *
     * @return a ResponseEntity with a list of RestaurantDTO objects and an HTTP status code of 200 OK.
     */
    @PostMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsWithinAMileRadius(@RequestBody UserLocation userLocation) {
        return new ResponseEntity<>(restaurantService.getRestaurantsByLocation(userLocation.getLatitude(), userLocation.getLongitude()), HttpStatus.OK);
    }



    /**
     * Handles HTTP GET requests to "/food_finder/all-restaurants".
     *
     * @return a ResponseEntity with a list of all {@link RestaurantDTO} objects and an HTTP status code of 200 OK.
     */
    @GetMapping("/all-restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK );
    }


}
