package com.app.foodfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLocation {

    private Double latitude;
    private Double longitude;
}
