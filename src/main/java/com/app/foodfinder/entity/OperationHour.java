package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "operationHours")
    private List<Restaurant> restaurants;

    public RestaurantOperationHours(Long id, Integer dayOfWeek, String openingTime, String closingTime)
    {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.restaurants = new ArrayList<>();
    }

}

