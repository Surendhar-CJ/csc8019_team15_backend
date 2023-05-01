package com.app.foodfinder.service;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import java.io.IOException;
import java.util.List;

public interface RestaurantService {

    RestaurantDTO getRestaurantById(Long restaurantId);

    List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude);

    List<RestaurantDTO> getAllRestaurants();


}
