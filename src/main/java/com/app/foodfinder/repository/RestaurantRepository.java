package com.app.foodfinder.repository;

import com.app.foodfinder.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The RestaurantRepository interface extends the JpaRepository interface to provide standard CRUD methods for the Restaurant entity.
 * It provides an additional method "findByRestaurantID" to retrieve a Restaurant entity by its restaurantID.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Finds a Restaurant entity by its restaurantID.
     *
     * @param restaurantID the ID of the restaurant to find.
     *
     * @return the Restaurant entity corresponding to the given restaurantID, or null if not found.
     */
    Restaurant findByRestaurantID(Long restaurantID);
}
