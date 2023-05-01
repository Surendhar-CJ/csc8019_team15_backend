package com.app.foodfinder.dto.dtomapper;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public final class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}
