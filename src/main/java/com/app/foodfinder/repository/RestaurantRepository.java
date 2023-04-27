package com.app.foodfinder.repository;

import com.app.foodfinder.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByRestaurantID(Long restaurantID);
}
