package com.app.foodfinder.service.implementation;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.dto.dtomapper.RestaurantDTOMapper;
import com.app.foodfinder.repository.RestaurantRepository;
import com.app.foodfinder.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class implements the RestaurantService interface and provides methods to interact with the RestaurantRepository
 * to retrieve restaurant information.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Service
public class RestaurantServiceImplementation implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantDTOMapper restaurantDTOMapper;


    /**
     * Constructor for RestaurantServiceImplementation that initializes the RestaurantRepository and RestaurantDTOMapper
     * objects using dependency injection.
     *
     * @param restaurantRepository - an object of type RestaurantRepository that provides CRUD functionality for the Restaurant entity
     * @param restaurantDTOMapper - an object of type RestaurantDTOMapper that maps between Restaurant and RestaurantDTO objects
     */
    @Autowired
    public RestaurantServiceImplementation(RestaurantRepository restaurantRepository, RestaurantDTOMapper restaurantDTOMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantDTOMapper = restaurantDTOMapper;
    }



    /**
     * This method retrieves a single Restaurant object based on the restaurant ID provided
     * and maps it to RestaurantDTO.
     *
     * @param restaurantId - the ID of the restaurant to retrieved
     *
     * @return a RestaurantDTO object representing the restaurant with the provided ID
     *
     * @throws ResourceNotFoundException if no restaurant is found with the provided ID
     */
    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        restaurant.setAverageCost(restaurant.averageCostOfADish());
        restaurant.setOperatingHoursOfTheDay(restaurant.operatingHoursOfTheDay());

        return restaurantDTOMapper.apply(restaurant);
    }



    /**
     * This method retrieves a list of Restaurants objects representing all the open restaurants
     * within one-mile radius of the latitude and longitude passed and maps it to the list of RestaurantDTO.
     *
     * @param latitude - latitude of the user's location
     * @param longitude - longitude of the user's location
     *
     * @return list of Restaurant DTO objects representing the open restaurants within one-mile radius.
     */
    @Override
    public List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude) {
        final double ONE_MILE_IN_METERS = 1609.34; // 1 mile = 1609.34 meters

        List<Restaurant> nearbyRestaurants = new ArrayList<>();
        List<Restaurant> allRestaurants =  restaurantRepository.findAll();

        for (Restaurant restaurant : allRestaurants) {
            double restaurantLatitude = restaurant.getLatitude();
            double restaurantLongitude = restaurant.getLongitude();
            double distance = restaurant.distanceFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude);

            boolean isOpen = restaurant.isOpen();
            //Sets average cost of a main course dish
            restaurant.setAverageCost(restaurant.averageCostOfADish());
            //Sets operating hours of the day
            restaurant.setOperatingHoursOfTheDay(restaurant.operatingHoursOfTheDay());
            //Checks if the restaurant is within one-mile radius and open
            if (distance <= ONE_MILE_IN_METERS && isOpen) {
                restaurant.setDistanceFromUser(distance);
                nearbyRestaurants.add(restaurant);
            }
        }

        return nearbyRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }




    /**
     * This method retrieves a list of Restaurant objects representing all the restaurants and
     * maps it to the list of RestaurantDTO objects.
     *
     * @return list of RestaurantDTO objects representing all the restaurants.
     */
    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> allRestaurants =  restaurantRepository.findAll();

        for(Restaurant restaurant : allRestaurants) {
            restaurant.setAverageCost(restaurant.averageCostOfADish());
            restaurant.setOperatingHoursOfTheDay(restaurant.operatingHoursOfTheDay());
        }

        return allRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }


}
