package com.app.foodfinder.dto;

import com.app.foodfinder.entity.User;

public record ReviewDTO (

    Long id,
    String username,
    String comment,
    Integer rating,
    Long restaurantId,
    Long userId

)
{ }
