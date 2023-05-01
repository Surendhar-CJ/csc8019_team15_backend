package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class  Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long restaurantID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

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

    @Transient
    private Double averageCost;

    @Transient
    private Double distanceFromUser;

    @Transient
    private LocalTime openingTime;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "restaurant_operation_hour",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_hour_id")
    )
    private List<OperationHour> operationHours;

    @ManyToMany
    @JoinTable(
            name = "restaurant_menu",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menuItems;



    /**
     * This method checks if the restaurant is open.
     *
     * @return true if the restaurant is open, else false.
     */
    public Boolean isOpen() {
        LocalTime currentTime = LocalTime.now();
        int currentDayOfWeek = LocalDateTime.now().getDayOfWeek().getValue(); // get current day of week

        List<OperationHour> operationHours = this.getOperationHours();

        for (OperationHour hours : operationHours) {
            if (DayOfWeek.valueOf(hours.getDayOfWeek().toUpperCase()).getValue() == currentDayOfWeek) { // if the restaurant is open on the current day of week
                LocalTime openingTime = LocalTime.parse(hours.getOpeningTime());
                LocalTime closingTime = LocalTime.parse(hours.getClosingTime());
                return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime); // return whether the current time is between opening and closing time
            }
        }
        return false;
    }



    public Double averageCostOfADish() {
        List<Menu> menuItems = this.menuItems;
        System.out.println("Size "+menuItems.size());

        double averageCost = 0;

        if(menuItems.size() == 0)
            return averageCost;

        for(Menu menu : menuItems){
            averageCost += menu.getPrice();
            System.out.println(menu);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(averageCost/menuItems.size()));
    }



    /**
     * This method calculates and returns the distance between the two points using latitude and longitude.
     *
     * @param latitude1 - latitude of the first point
     * @param longitude1 - longitude of the first point
     * @param latitude2 - latitude of the second point
     * @param longitude2 - longitude of the second point
     * @return distance (in metres)
     */
    public Double distanceFromUser(double latitude1, double longitude1, double latitude2, double longitude2)
    {
        final int RADIUS_OF_EARTH = 6371; // Earth's radius in km

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = RADIUS_OF_EARTH * c * 1000; // convert to meters

        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(distance));
    }


}
