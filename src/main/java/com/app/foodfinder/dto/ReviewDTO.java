package com.app.foodfinder.dto;

public record ReviewDTO (

    Long id,
    String username,
    String comment,
    Integer rating,
    Long restaurantId,
    Long userId

)
{ }
