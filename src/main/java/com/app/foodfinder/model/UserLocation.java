package com.app.foodfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * This class represents the user location which contains latitude and longitude
 * It uses Lombok annotations to generate getters, setters, all argument constructor, equals/hashcode and toString methods at compile-time.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Data
@AllArgsConstructor
public class UserLocation {

    private Double latitude;
    private Double longitude;
}
