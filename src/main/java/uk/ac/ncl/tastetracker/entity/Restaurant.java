package uk.ac.ncl.tastetracker.entity;

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
 * Restaurant class represents the Restaurant entity. An instance of the class can be represented by a field in the database.
 * This class uses Lombok annotations to generate getters, setters, no argument constructor, all argument constructor, equals/hashcode and toString methods.
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required)
 *       and not for creating the schema (so, constraints are not mentioned in the fields explicitly)
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class  Restaurant {

    /**
     * The unique identifier of the restaurant.
     * The column name corresponds to the column name "id" in the restaurant table in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long restaurantID;

    /**
     * The name of the restaurant
     * The column name corresponds to the column name "name" in the restaurant table in the database.
     */
    @Column(name = "name")
    private String name;

    /**
     * The address of the restaurant
     * The column name corresponds to the column name "address" in the restaurant table in the database.
     */
    @Column(name = "address")
    private String address;

    /**
     * The phone number of the restaurant
     * The column name corresponds to the column name "phone_number" in the restaurant table in the database.
     */
    @Column(name = "phone_number")
    public String phoneNumber;

    /**
     * The latitude of the restaurant's location
     * The column name corresponds to the column name "latitude" in the restaurant table in the database.
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * The longitude of the restaurant's location
     * The column name corresponds to the column name "longitude" in the restaurant table in the database.
     */
    @Column(name = "longitude")
    private Double longitude;

    /**
     * The overall rating of the restaurant
     * The column name corresponds to the column name "rating" in the restaurant table in the database.
     */
    @Column(name = "rating")
    private Double overallRating;

    /**
     * The URL link to the menu of the restaurant.
     * The column name corresponds to the column name "menu_link" in the restaurant table in the database.
     */
    @Column(name = "menu_link")
    private String menuLink;

    /**
     * The website URL link of the restaurant.
     * The column name corresponds to the column name "website_link" in the restaurant table in the database.
     */
    @Column(name = "website_link")
    private String websiteLink;

    /**
     * The cuisine of the restaurant.
     * The column name corresponds to the column name "cuisine_id" in the restaurant table in the database.
     */
    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

    /**
     * The average cost of a meal at the restaurant.
     * "@Transient" represents that it is not part of the database table column of the restaurant entity.
     */
    @Transient
    private Double averageCost;

    /**
     *The distance of the restaurant from the user's location.
     * "@Transient" represents that it is not part of the database table column of the restaurant entity.
     */
    @Transient
    private Double distanceFromUser;

    /**
     * The approximate walking time from the user's location to the restaurant.
     * "@Transient" represents that it is not part of the database table column of the restaurant entity.
     */
    @Transient
    private Double approximateWalkingTimeFromUser;

    /**
     * The opening and closing hours of the restaurant for each day of the week.
     * "@Transient" represents that it is not part of the database table column of the restaurant entity.
     */
    @Transient
    private List<String> operatingHoursOfTheWeek;

    /**
     * The image URL links of the restaurant.
     * "@Transient" represents that it is not part of the database table column of the restaurant entity
     */
    @Transient
    private List<String> imagesLink;


    /**
     *  Represents the reviews of the restaurant.
     * This field is mapped to the "review" table in the database, hence annotated with "@OneToMany".
     */
    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

    /**
     * Represents the images of the restaurant.
     * This field is mapped to the "image" table in the database, hence annotated with "@OneToMany".
     */
    @OneToMany(mappedBy = "restaurant")
    private List<Image> images;

    /**
     * Represents the operating hours of the restaurant.
     * This field is mapped to the "restaurant_operation_hour" table in the database, hence annotated with "@ManyToMany".
     */
    @ManyToMany
    @JoinTable(
            name = "restaurant_operation_hour",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_hour_id")
    )
    private List<OperationHour> operationHours;

    /**
     * Represents the menu items of the restaurant.
     * This field is mapped to the "restaurant_menu" table in the database, hence annotated with "@OneToMany".
     */
    @ManyToMany
    @JoinTable(
            name = "restaurant_menu",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menuItems;





    /**
     * This method checks if the restaurant is open by validating against the operation hours of the restaurant.
     *
     * @return true if the restaurant is open, else false.
     */
    public Boolean isOpen() {
        LocalTime currentTime = LocalTime.now();
        // get current day of week
        int currentDayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();

        List<OperationHour> operationHours = this.getOperationHours();

        for (OperationHour hours : operationHours) {
            // if the restaurant is open on the current day of week
            if (DayOfWeek.valueOf(hours.getDayOfWeek().toUpperCase()).getValue() == currentDayOfWeek) {
                LocalTime openingTime = LocalTime.parse(hours.getOpeningTime());
                LocalTime closingTime = LocalTime.parse(hours.getClosingTime());

                // return whether the current time is between opening and closing time
                return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime);
            }
        }
        return false;
    }





    /**
     * Calculates the average cost of a main course dish from the menu items of the restaurant.
     *
     * @return The average cost of a main course dish.
     */
    public Double averageCostOfADish() {

        List<Menu> menuItems = this.getMenuItems();

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
     * This method returns restaurant's operating hours of the week
     *
     * @return restaurant's operating hours
     */
    public List<String> operatingHoursOfTheWeek()
    {
        List<OperationHour> restaurantOperatingHours = this.operationHours;
        List<String> operatingHours = new ArrayList<>(restaurantOperatingHours.size());

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
