package com.app.foodfinder.model;

public class ReviewModel {

    private String comment;

    private Integer rating;

    private Long userId;

    private Long restaurantId;

    public ReviewModel() {
    }

    public ReviewModel(String comment, Integer rating, Long userId, Long restaurantId) {
        this.comment = comment;
        this.rating = rating;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

   public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
