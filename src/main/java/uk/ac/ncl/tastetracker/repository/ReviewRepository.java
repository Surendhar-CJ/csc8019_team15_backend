package uk.ac.ncl.tastetracker.repository;

import uk.ac.ncl.tastetracker.entity.Restaurant;
import uk.ac.ncl.tastetracker.entity.Review;
import uk.ac.ncl.tastetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * ReviewRepository interface extends the JpaRepository interface to provide standard CRUD methods for the Review entity.
 * It provides an additional method "findByUserAndRestaurant" for finding the review based on user who reviewed
 * and the restaurant which is associated with.
 *
 * @author Surendhar Chandran Jayapal
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Returns the review posted by the user for a particular restaurant.
     *
     * @param user User object representing the user
     * @param restaurant Restaurant object representing the restaurant
     *
     * @return Review posted by the user for the restaurant or null if not found
     */
    Review findByUserAndRestaurant(User user, Restaurant restaurant);
}
