package com.app.foodfinder.service;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.exception.ResourceNotFoundException;
import com.app.foodfinder.dto.dtomapper.RestaurantDTOMapper;
import com.app.foodfinder.repository.RestaurantRepository;
import com.app.foodfinder.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImplementation implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantDTOMapper restaurantDTOMapper;

    @Autowired
    public RestaurantServiceImplementation(RestaurantRepository restaurantRepository, RestaurantDTOMapper restaurantDTOMapper)
    {
        this.restaurantRepository = restaurantRepository;
        this.restaurantDTOMapper = restaurantDTOMapper;
    }

    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId)
    {
        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null)
        {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        double averageCost = restaurant.averageCostOfADish();
        restaurant.setAverageCost(averageCost);

        return restaurantDTOMapper.apply(restaurant);
    }


    /**
     * This method returns a list of open restaurants within one-mile radius of the latitude and longitude passed.
     *
     * @param latitude - latitude of the user's location
     * @param longitude - longitude of the user's location
     * @return list of open restaurants with one-mile radius.
     */
    @Override
    public List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude)
    {
        final double ONE_MILE_IN_METERS = 1609.34; // 1 mile = 1609.34 meters

        List<Restaurant> nearbyRestaurants = new ArrayList<>();

        List<Restaurant> allRestaurants =  restaurantRepository.findAll();

        for (Restaurant restaurant : allRestaurants)
        {
            double restaurantLatitude = restaurant.getLatitude();
            double restaurantLongitude = restaurant.getLongitude();
            double distance = restaurant.distanceFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude);

            boolean isOpen = restaurant.isOpen();

            double averageCostOfDish = restaurant.averageCostOfADish();
            restaurant.setAverageCost(averageCostOfDish);

            if (distance <= ONE_MILE_IN_METERS && isOpen)
            {
                restaurant.setDistanceFromUser(distance);
                nearbyRestaurants.add(restaurant);
            }
        }

        return nearbyRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }




    /**
     * This method returns a list of all restaurants
     *
     * @return list of all restaurants.
     */
    @Override
    public List<RestaurantDTO> getAllRestaurants()
    {
        List<Restaurant> allRestaurants =  restaurantRepository.findAll();

        for(Restaurant restaurant : allRestaurants)
        {
            double averageCost = restaurant.averageCostOfADish();
            restaurant.setAverageCost(averageCost);
        }

        return allRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }
}
