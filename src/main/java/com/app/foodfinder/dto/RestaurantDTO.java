package com.app.foodfinder.dto;

import com.app.foodfinder.entity.Image;

import java.util.List;

/**
 * This record represents a RestaurantDTO (Data Transfer Object), denoting the class as immutable to prevent external sources from manipulating it.
 *
 * The purpose of this record is to provide a representation of a restaurant object to be sent as a response to
 * a client request.
 *
 * @param id The ID of the restaurant
 * @param name The name of the restaurant
 * @param address The address of the restaurant
 * @param phoneNumber The phone number of the restaurant
 * @param latitude The latitude of the restaurant's location
 * @param longitude The longitude of the restaurant's location
 * @param overallRating The overall rating of the restaurant
 * @param cuisine The type of cuisine the restaurant serves
 * @param operatingHoursOfTheDay The operating hours of the restaurant for a given day
 * @param imagesLink The link to the images of the restaurant
 * @param menuLink The link to the menu of the restaurant
 * @param averageCostOfADish The average cost of a dish at the restaurant
 * @param distanceFromUser The distance from the user to the restaurant
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
public record RestaurantDTO (

    Long id,
    String name,
    String address,
    String phoneNumber,
    Double latitude,
    Double longitude,
    Double overallRating,
    String cuisine,
    List<String> operatingHoursOfTheWeek,
    String menuLink,
    String websiteLink,
    Double averageCostOfADish,
    Double distanceFromUser,
    Double approximateWalkingTimeFromUser,
    List<String> imagesLink

)
{}
