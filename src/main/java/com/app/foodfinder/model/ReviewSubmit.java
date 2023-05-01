package com.app.foodfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModel {

    private String comment;
    private Integer rating;
    private Long userId;
    private Long restaurantId;

}
