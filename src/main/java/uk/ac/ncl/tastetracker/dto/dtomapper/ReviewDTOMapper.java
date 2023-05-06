package uk.ac.ncl.tastetracker.dto.dtomapper;

import uk.ac.ncl.tastetracker.dto.ReviewDTO;
import uk.ac.ncl.tastetracker.entity.Review;
import org.springframework.stereotype.Component;

import java.util.function.Function;


/**
 *This class is used to map a Review object to a ReviewDTO object by implementing Function interface.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Component
public final class ReviewDTOMapper implements Function<Review, ReviewDTO> {

    /**
     * This method maps the Review object to a ReviewDTO object.
     *
     * @param review Review object
     *
     * @return ReviewDTO object
     */
    @Override
    public ReviewDTO apply(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getUser().getUsername(),
                review.getRating(),
                review.getComment());
    }

}
