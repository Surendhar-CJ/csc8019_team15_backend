package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "restaurant_operation_hours")
public class RestaurantOperationHours
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_Week", unique = true, nullable = false)
    private Integer dayOfWeek;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public RestaurantOperationHours(Long id, Integer dayOfWeek, String openingTime, String closingTime, Restaurant restaurant)
    {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.restaurant = restaurant;
    }

}

