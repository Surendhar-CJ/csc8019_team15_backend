package com.app.foodfinder.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


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

    @Column(name = "opening_hours")
    private String openingHours; //Format -> HH:MM

    @Column(name = "closing_hours")
    private String closingHours; //Format -> HH:MM

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



    public Restaurant()
    {

    }

    public Restaurant(String name, String addressLine1, String addressLine2, String addressLine3, Double averageCost, String openingHours, String closingHours, String phoneNumber, Double latitude, Double longitude, Double overallRating, Cuisine cuisine, String imagesLink, String menu)
    {
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine1;
        this.addressLine3 = addressLine1;
        this.averageCost = averageCost;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.overallRating = overallRating;
        this.cuisine = cuisine;
        this.imagesLink = imagesLink;
        this.menu = menu;
        this.reviews = new ArrayList<>();
    }

    public Long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Double overallRating) {
        this.overallRating = overallRating;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getImagesLink()
    {
        return imagesLink;
    }

    public void setImagesLink()
    {
        this.imagesLink = imagesLink;
    }

    public Double getDistanceFromUser()
    {
        return distanceFromUser;
    }

    public void setDistanceFromUser(Double distance)
    {
        this.distanceFromUser = distance;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    /**
     * This method checks if the restaurant is open or not
     *
     * @param currentTime - current time
     * @return true, if the current time is within the restaurant's operating time.
     *         false, if the current time is not within the restaurant's operating time.
     */
    public Boolean isOpen(LocalTime currentTime)
    {
        LocalTime openingTime = LocalTime.parse(openingHours);
        LocalTime closingTime = LocalTime.parse(closingHours);

        return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime);
    }
}
