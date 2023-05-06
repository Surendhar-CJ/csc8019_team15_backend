package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



/**
 * This class represents a Restaurant entity.
 * It uses Lombok annotations to generate getters, setters, constructors, equals/hashcode and toString methods at compile-time.
 *
 * NOTE: Here, Spring Data JPA/Hibernate is used ONLY to fetch results from the database(so table mapping is required) and not creating a schema
 *       This has been disabled in application.properties file.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class  Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long restaurantID;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "rating")
    private Double overallRating;

    @Column(name = "menu_link")
    private String menuLink;

    @Column(name = "website_link")
    private String websiteLink;



    /**
     * "@Transient" represents that it is not part of the database table column of the restaurant entity.
     */
    @Transient
    private Double averageCost;

    @Transient
    private Double distanceFromUser;

    @Transient
    private String operatingHoursOfTheDay;

    @Transient
    private Double approximateWalkingTimeFromUser;

    @Transient
    private List<String> operatingHoursOfTheWeek;

    @Transient
    private List<String> imagesLink;




    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

    @OneToMany(mappedBy = "restaurant")
    private List<Image> images;

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





    /**
     * Calculates the average cost of a main course dish in the menu items of a restaurant.
     *
     * @return The average cost of a main course dish.
     */
    public Double averageCostOfADish() {
        List<Menu> menuItems = this.menuItems;

        double averageCost = 0;

        //Returns 0 if the there is no item to avoid division by zero error
        if(menuItems.size() == 0)
            return averageCost;

        for(Menu menu : menuItems){
            averageCost += menu.getPrice();
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(averageCost/menuItems.size()));
    }



    /**
     * This method calculates and returns the distance between the two points using latitudes and longitudes.
     *
     * @param latitude1 - latitude of the first point
     * @param longitude1 - longitude of the first point
     * @param latitude2 - latitude of the second point
     * @param longitude2 - longitude of the second point
     *
     * @return distance (in metres) with one value after decimal
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





    /**
     * This method returns the operating hours of the restaurant for the current day of the week.
     *
     * @return String representing the operating hours of the day in "HH:MM (opening time) - HH:MM (closing time)" format.
     *         Empty string if there is no operation hours available for the restaurant.
     *
     */
    public String operatingHoursOfTheDay() {
        LocalTime currentTime = LocalTime.now();
        int currentDayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();

        List<OperationHour> operationHours = this.getOperationHours();

        for (OperationHour hours : operationHours) {
            if (DayOfWeek.valueOf(hours.getDayOfWeek().toUpperCase()).getValue() == currentDayOfWeek) {
                LocalTime openingTime = LocalTime.parse(hours.getOpeningTime());
                LocalTime closingTime = LocalTime.parse(hours.getClosingTime());

                return String.format("%s - %s", openingTime.format(DateTimeFormatter.ofPattern("HH:mm")), closingTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        }

        //Returns an empty String if the restaurant doesn't have operating hours information
        return "";
    }


    /**
     * This method returns restaurant's operating hours of the week
     *
     * @return restaurant's operating hours
     */

    public List<String> operatingHoursOfTheWeek()
    {
        List<String> operatingHours = new ArrayList<>();
        List<OperationHour> restaurantOperatingHours = new ArrayList<>();
        restaurantOperatingHours = this.operationHours;

        for(OperationHour operationHour : restaurantOperatingHours)
        {
            LocalTime openingTime = LocalTime.parse(operationHour.getOpeningTime());
            LocalTime closingTime = LocalTime.parse(operationHour.getClosingTime());

            String timings = String.format("%s - %s", openingTime.format(DateTimeFormatter.ofPattern("HH:mm")), closingTime.format(DateTimeFormatter.ofPattern("HH:mm")));

            operatingHours.add(timings);
        }

        return operatingHours;
    }




    /**
     * This method returns a list images links associated with the restaurant
     *
     * @return list of restaurant's images links
     */
    public List<String> imagesLink()
    {
        List<Image> images = this.images;

        List<String> imagesLink = new ArrayList<>(images.size());

        for(Image image : images)
        {
            imagesLink.add(image.getImageLink());
        }

        return imagesLink;
    }

}
