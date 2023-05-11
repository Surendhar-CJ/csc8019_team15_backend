package uk.ac.ncl.tastetracker.repository;

import uk.ac.ncl.tastetracker.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * RestaurantRepository interface extends the JpaRepository interface to provide standard CRUD methods for the Restaurant entity.
 * It provides an additional method "findByRestaurantID" to retrieve a Restaurant entity by its restaurantID.
 *
 * @author Surendhar Chandran Jayapal
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Returns a Restaurant entity by its restaurantID.
     *
     * @param restaurantID the ID of the restaurant to find.
     *
     * @return the Restaurant entity corresponding to the given restaurantID, or null if not found.
     */
    Restaurant findByRestaurantID(Long restaurantID);
}
