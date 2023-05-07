package uk.ac.ncl.tastetracker.dto.dtomapper;

import uk.ac.ncl.tastetracker.dto.UserDTO;
import uk.ac.ncl.tastetracker.entity.User;
import org.springframework.stereotype.Component;
import java.util.function.Function;

/**
 * This class is used to map a User object to a USerDTO object by implementing Function interface.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.1 (22-04-2023)
 */
@Component
public final class UserDTOMapper implements Function<User, UserDTO> {

    /**
     * This method maps the User object to a UserDTO object.
     *
     * @param user User object
     *
     * @return UserDTO object
     */
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

}
