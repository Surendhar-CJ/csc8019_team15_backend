package com.app.foodfinder.repository;

import com.app.foodfinder.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The ReviewRepository interface extends the JpaRepository interface to provide standard CRUD methods for the Review entity.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
