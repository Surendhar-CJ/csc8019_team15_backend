package com.app.foodfinder.dto;

public record RestaurantDTO (

    Long id,
    String name,
    String addressLine1,
    String addressLine2,
    String addressLine3,
    Double averageCost,
    String phoneNumber,
    Double latitude,
    Double longitude,
    Double overallRating,
    String cuisine,
    String imagesLink,
    String menu,
    Double distanceFromUser

)
{}
