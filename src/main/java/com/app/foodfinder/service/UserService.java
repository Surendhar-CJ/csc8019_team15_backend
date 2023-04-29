package com.app.foodfinder.service;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;

public interface UserService {

    UserDTO userRegister(User user);

    UserDTO userLogin(String username, String password);

    UserDTO getUserById(Long id);

   // User updateUser(Long id, User user);

    void deleteUserById(Long id);
}
