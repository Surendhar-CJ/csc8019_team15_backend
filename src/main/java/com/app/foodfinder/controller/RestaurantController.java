package com.app.foodfinder.controller;


import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.model.UserLocation;
import com.app.foodfinder.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food_finder")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService)
    {
        this.restaurantService = restaurantService;
    }


    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable("restaurantId") Long restaurantId)
    {
        return new ResponseEntity<RestaurantDTO>(restaurantService.getRestaurantById(restaurantId), HttpStatus.OK);
    }

    /**
     * This method returns all the open restaurants within 1-mile radius from user's location
     *
     * @param userLocation - UserLocation object which contains user's latitude and user's longitude
     * @return all open restaurants within 1-mile radius from user's location
     */
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsWithinAMileRadius(@RequestBody UserLocation userLocation)
    {
        return new ResponseEntity<>(restaurantService.getRestaurantsByLocation(userLocation.getLatitude(), userLocation.getLongitude()), HttpStatus.OK );
    }

    @GetMapping("/all-restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants()
    {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK );
    }
}
