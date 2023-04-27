package com.app.foodfinder.mapper;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RestaurantDTOMapper implements Function<Restaurant, RestaurantDTO> {

    @Override
    public RestaurantDTO apply(Restaurant restaurant) {

               return new RestaurantDTO (
                                 restaurant.getRestaurantID(),
                                 restaurant.getName(),
                                 restaurant.getAddressLine1(),
                                 restaurant.getAddressLine2(),
                                 restaurant.getAddressLine3(),
                                 restaurant.getAverageCost(),
                                 restaurant.getPhoneNumber(),
                                 restaurant.getLatitude(),
                                 restaurant.getLongitude(),
                                 restaurant.getOverallRating(),
                                 restaurant.getCuisine().getName(),
                                 restaurant.getImagesLink(),
                                 restaurant.getMenu(),
                                 restaurant.getDistanceFromUser()
               );

    }
}
