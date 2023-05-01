package com.app.foodfinder.dto.dtomapper;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public final class RestaurantDTOMapper implements Function<Restaurant, RestaurantDTO> {

    @Override
    public RestaurantDTO apply(Restaurant restaurant) {

               return new RestaurantDTO (
                                 restaurant.getRestaurantID(),
                                 restaurant.getName(),
                                 restaurant.getAddress(),
                                 restaurant.getPhoneNumber(),
                                 restaurant.getLatitude(),
                                 restaurant.getLongitude(),
                                 restaurant.getOverallRating(),
                                 restaurant.getCuisine().getName(),
                                 restaurant.getImagesLink(),
                                 restaurant.getAverageCost(),
                                 restaurant.getDistanceFromUser()

               );

    }
}
