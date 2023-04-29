package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long reviewId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Review(String comment, Integer rating, User user, Restaurant restaurant)
    {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.restaurant = restaurant;
    }
}
