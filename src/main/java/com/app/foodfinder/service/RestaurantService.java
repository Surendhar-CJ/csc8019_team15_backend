package com.app.foodfinder.service;

import com.app.foodfinder.dto.RestaurantDTO;
import java.util.List;

/**
 * This interface defines methods for accessing restaurant data.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface RestaurantService {


    /**
     * Retrieves a restaurant with the specified ID and returns a RestaurantDTO containing the restaurant's data.
     *
     * @param restaurantId the ID of the restaurant to retrieve
     *
     * @return a RestaurantDTO containing the retrieved restaurant's data
     */
    RestaurantDTO getRestaurantById(Long restaurantId);




    /**
     * Retrieves a restaurant with the specified ID and User Location and returns a RestaurantDTO containing the restaurant's data.
     *
     * @param restaurantId the ID of the restaurant to retrieve
     *
     * @return a RestaurantDTO containing the retrieved restaurant's data
     */
    RestaurantDTO getRestaurantByIdWithUserLocation(Long restaurantId, Double latitude, Double longitude);




    /**
     * Retrieves a list of restaurants located within a specified radius of a given latitude and longitude and returns a list
     * of RestaurantDTOs containing the restaurants' data.
     *
     * @param latitude the latitude of the location around which to search for restaurants
     * @param longitude the longitude of the location around which to search for restaurants
     * @return a list of RestaurantDTOs containing the retrieved restaurants' data
     */
    List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude);



    /**
     * Retrieves all restaurants in the repository and returns a list of RestaurantDTOs containing the restaurants' data.
     *
     * @return a list of RestaurantDTOs containing all retrieved restaurants' data
     */
    List<RestaurantDTO> getAllRestaurants();


}
