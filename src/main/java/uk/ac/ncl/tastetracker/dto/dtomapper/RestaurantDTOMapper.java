package uk.ac.ncl.tastetracker.dto.dtomapper;

import uk.ac.ncl.tastetracker.dto.RestaurantDTO;
import uk.ac.ncl.tastetracker.entity.Restaurant;
import org.springframework.stereotype.Component;
import java.util.function.Function;


/**
 * This class is used to map a Restaurant object to a RestaurantDTO object by implementing Function interface.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
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
                                 restaurant.getOperatingHoursOfTheWeek(),
                                 restaurant.getMenuLink(),
                                 restaurant.getWebsiteLink(),
                                 restaurant.getAverageCost(),
                                 restaurant.getDistanceFromUser(),
                                 restaurant.getApproximateWalkingTimeFromUser(),
                                 restaurant.getImagesLink()

               );
    }

}
