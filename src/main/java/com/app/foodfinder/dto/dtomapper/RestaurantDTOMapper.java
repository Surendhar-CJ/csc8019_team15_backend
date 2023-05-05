package com.app.foodfinder.dto.dtomapper;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import org.springframework.stereotype.Component;
import java.util.function.Function;


/**
 * This class is used to map a Restaurant object to a RestaurantDTO object by implementing Function interface.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Component
public final class RestaurantDTOMapper implements Function<Restaurant, RestaurantDTO> {

    /**
     * This method maps the Restaurant object to a RestaurantDTO object.
     *
     * @param restaurant Restaurant object
     *
     * @return RestaurantDTO object
     */
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
                                 restaurant.getOperatingHoursOfTheDay(),
                                 restaurant.getMenuLink(),
                                 restaurant.getWebsiteLink(),
                                 restaurant.getAverageCost(),
                                 restaurant.getDistanceFromUser(),
                                 restaurant.getApproximateWalkingTimeFromUser(),
                                 restaurant.getImagesLink()

               );
    }

}
