package com.app.foodfinder.repository;

import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByRestaurant(Restaurant restaurant);
}
