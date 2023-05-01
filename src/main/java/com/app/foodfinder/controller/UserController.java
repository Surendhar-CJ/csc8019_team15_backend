package com.app.foodfinder.controller;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.config.jwt.JWTService;
import com.app.foodfinder.model.UserLogin;
import com.app.foodfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food_finder/users")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    
    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.userRegister(user);

        final String jwtToken = jwtService.generateToken(user.getUsername());

        return new ResponseEntity<String>(jwtToken, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        userService.userLogin(username, password);

        final String jwtToken = jwtService.generateToken(username);

        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<UserDTO>(user, HttpStatus.FOUND);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
