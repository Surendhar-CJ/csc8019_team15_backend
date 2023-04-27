package com.app.foodfinder.service;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;

public interface UserService {

    //POST
    UserDTO userRegister(User user);

    //POST - Authenticate
    UserDTO userLogin(String username, String password);

   //GET
    UserDTO getUserById(Long id);

    //PUT
   // User updateUser(Long id, User user);

    //DELETE
    void deleteUserById(Long id);
}
