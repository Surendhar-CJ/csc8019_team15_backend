package uk.ac.ncl.tastetracker.controller;


import uk.ac.ncl.tastetracker.dto.RestaurantDTO;
import uk.ac.ncl.tastetracker.exception.custom.ResourceNotFoundException;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.requestBody.UserLocation;
import uk.ac.ncl.tastetracker.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.ncl.tastetracker.entity.Restaurant;
import java.util.List;



/**
 * The RestaurantController class defines REST API endpoints handles HTTP requests and returns the response in a RESTful way.
 * This class contains two end points for fetching restaurants details. One that fetches a restaurant by restaurant id and
 * the other method fetches a list of open restaurants within 1-mile radius of user's current location.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
 */
@RestController
@RequestMapping("/food_finder")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestaurantController {


    /**
     * Service {@link RestaurantService} for handling Restaurant related operations
     */
    private final RestaurantService restaurantService;



    /**
     * Constructor for creating RestaurantController object with injected dependencies.
     *
     * @param restaurantService Service class for handling Restaurant related operations.
     */
    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }




    /**
     * Handles HTTP POST requests to "/food_finder/restaurants/{restaurantId}".
     * This API endpoint requires {@link UserLocation} object containing latitude and longitude in the request body
     * and the restaurantId in the request parameter.
     * A successful request returns a ResponseEntity with a {@link RestaurantDTO} object representing a {@link Restaurant}.
     * HTTP Status code : 200 OK
     *
     * @param restaurantId the ID of the restaurant to be retrieved.
     *
     * @return a ResponseEntity with a RestaurantDTO object and an HTTP status code : 200 OK.
     *
     * @throws ResourceNotFoundException if a restaurant with passed restaurantId is not found with an HTTP status code : 404 NOT FOUND
     * @throws InvalidInputException if the latitude and longitude present in the object is not valid or the UserLocation object is null
     *         with a status code : 400 BAD REQUEST
     */
    @PostMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable("restaurantId") Long restaurantId, @RequestBody UserLocation userLocation) {
        if(userLocation == null) {
            throw new InvalidInputException("Invalid user location");
        }
        return new ResponseEntity<RestaurantDTO>(restaurantService.getRestaurantById(restaurantId, userLocation.getLatitude(), userLocation.getLongitude()), HttpStatus.OK);
    }





    /**
     * Handles HTTP POST requests to "/food_finder/restaurants".
     * This API endpoint requires {@link UserLocation} in the request body.
     * A successful request returns a ResponseEntity with a list of {@link RestaurantDTO} objects that represents
     * all the open {@link Restaurant} within 1-mile radius from user's location.
     * HTTP Status code : 200 OK.
     *
     * @param userLocation - UserLocation object which contains user's latitude and user's longitude.
     *
     * @return a ResponseEntity with a list of RestaurantDTO objects and an HTTP status code of 200 OK.
     *
     * @throws InvalidInputException if the latitude and longitude present in the object is not valid the UserLocation object is null
     *         with a status code : 400 BAD REQUEST.
     */
    @PostMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsWithinAMileRadius(@RequestBody UserLocation userLocation) {
        if(userLocation == null) {
            throw new InvalidInputException("Invalid user location");
        }
        return new ResponseEntity<>(restaurantService.getRestaurantsByLocation(userLocation.getLatitude(), userLocation.getLongitude()), HttpStatus.OK);
    }



}
