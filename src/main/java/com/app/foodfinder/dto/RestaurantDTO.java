package com.app.foodfinder.dto;

public record RestaurantDTO (

    Long id,
    String name,
    String address,
    String phoneNumber,
    Double latitude,
    Double longitude,
    Double overallRating,
    String cuisine,
    String imagesLink,
    Double averageCostOfADish,
    Double distanceFromUser

)
{}
