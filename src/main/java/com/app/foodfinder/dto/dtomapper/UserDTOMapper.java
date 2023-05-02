package com.app.foodfinder.dto.dtomapper;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;
import org.springframework.stereotype.Component;
import java.util.function.Function;

/**
 * This class is used to map a User object to a USerDTO object by implementing Function interface.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
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
