package uk.ac.ncl.tastetracker.service;

import uk.ac.ncl.tastetracker.dto.RestaurantDTO;
import java.util.List;

/**
 * Defines methods for accessing restaurant data.
 * This interface serves the interface for implementing Restaurant based server logic and functions.
 * It has two methods, one method is to get restaurant by id and the another method is to get the list of all the restaurants by location.
 *
 * @author Surendhar Chandran Jayapal
 */
public interface RestaurantService {




    /**
     * Retrieves a restaurant with the specified ID and User Location and returns a RestaurantDTO containing the restaurant's data.
     *
     * @param restaurantId the ID of the restaurant to retrieve
     *
     * @return a RestaurantDTO containing the retrieved restaurant's data
     */
    RestaurantDTO getRestaurantById(Long restaurantId, Double latitude, Double longitude);




    /**
     * Retrieves a list of restaurants located within a specified radius of a given latitude and longitude and returns a list
     * of RestaurantDTOs containing the restaurants' data.
     *
     * @param latitude the latitude of the location around which to search for restaurants
     * @param longitude the longitude of the location around which to search for restaurants
     * @return a list of RestaurantDTOs containing the retrieved restaurants' data
     */
    List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude);



}
