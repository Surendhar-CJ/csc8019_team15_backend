package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
public class  Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long restaurantID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address_line1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "address_line3")
    private String addressLine3;

    @Column(name = "average_cost")
    private Double averageCost;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "rating")
    private Double overallRating;

    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

    @Column(name = "images_link", nullable = false)
    private String imagesLink;

    @Column(name ="menu")
    private String menu;

    @Transient
    private Double distanceFromUser;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<RestaurantOperationHours> operationHours;

    public Restaurant(String name, String addressLine1, String addressLine2, String addressLine3, Double averageCost, String phoneNumber, Double latitude, Double longitude, Double overallRating, Cuisine cuisine, String imagesLink, String menu)
    {
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.averageCost = averageCost;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.overallRating = overallRating;
        this.cuisine = cuisine;
        this.imagesLink = imagesLink;
        this.menu = menu;
        this.reviews = new ArrayList<>();
        this.operationHours = new ArrayList<>();
    }

    /**
     * This method checks if the restaurant is open.
     *
     * @return true if the restaurant is open, else false.
     */
    public Boolean isOpen()
    {
        LocalTime currentTime = LocalTime.now();
        int currentDayOfWeek = LocalDateTime.now().getDayOfWeek().getValue(); // get current day of week
        List<RestaurantOperationHours> operationHours = this.getOperationHours();
        for (RestaurantOperationHours hours : operationHours)
        {
            if (hours.getDayOfWeek() == currentDayOfWeek)
            { // if the restaurant is open on the current day of week
                LocalTime openingTime = LocalTime.parse(hours.getOpeningTime());
                LocalTime closingTime = LocalTime.parse(hours.getClosingTime());
                return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime); // return whether the current time is between opening and closing time
            }
        }
        return false;
    }
}
