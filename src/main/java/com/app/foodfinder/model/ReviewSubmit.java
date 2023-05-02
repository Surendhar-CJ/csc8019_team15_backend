package com.app.foodfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The ReviewSubmit class represents a review submission made by a user for a restaurant.
 * It uses Lombok annotations to generate getters, setters, all argument constructor, equals/hashcode and toString methods at compile-time.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSubmit {

    private String comment;
    private Integer rating;
    private Long userId;
    private Long restaurantId;

}
