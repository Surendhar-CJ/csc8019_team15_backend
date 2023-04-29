package com.app.foodfinder.service;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.exception.NotFoundException;
import com.app.foodfinder.mapper.RestaurantDTOMapper;
import com.app.foodfinder.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImplementation implements RestaurantService{

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
            throw new NotFoundException("Restaurant not found");
        }

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
            double distance = calculateDistance(latitude, longitude, restaurantLatitude, restaurantLongitude);

            boolean isOpen = restaurant.isOpen();

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
     * This method calculates and returns the distance between the two points using latitude and longitude.
     *
     * @param latitude1 - latitude of the first point
     * @param longitude1 - longitude of the first point
     * @param latitude2 - latitude of the second point
     * @param longitude2 - longitude of the second point
     * @return distance (in metres)
     */
    private double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2)
    {
        final int RADIUS_OF_EARTH = 6371; // Earth's radius in km

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = RADIUS_OF_EARTH * c * 1000; // convert to meters

        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(distance));
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

        return allRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }
}
