package uk.ac.ncl.tastetracker.repository;

import uk.ac.ncl.tastetracker.entity.Restaurant;
import uk.ac.ncl.tastetracker.entity.Review;
import uk.ac.ncl.tastetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The ReviewRepository interface extends the JpaRepository interface to provide standard CRUD methods for the Review entity.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     *
     * @param user
     * @param restaurant
     * @return
     */
    Review findByUserAndRestaurant(User user, Restaurant restaurant);
}