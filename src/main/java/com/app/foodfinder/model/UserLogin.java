package com.app.foodfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin {

    private String username;
    private String password;
}
