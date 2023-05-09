package uk.ac.ncl.tastetracker.service.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.tastetracker.config.jwt.JWTService;
import uk.ac.ncl.tastetracker.dto.dtomapper.RestaurantDTOMapper;
import uk.ac.ncl.tastetracker.entity.*;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceNotFoundException;
import uk.ac.ncl.tastetracker.repository.RestaurantRepository;
import uk.ac.ncl.tastetracker.requestBody.ReviewSubmit;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceImplementationTest {

    @Autowired
    private ReviewServiceImplementation reviewServiceImplementation;

    @Autowired
    private RestaurantServiceImplementation restaurantServiceImplementation;

    @Autowired
    private RestaurantDTOMapper restaurantDTOMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Service class to handle JWT-related operations.
     */
    @Autowired
    private JWTService jwtService;

    @Test
    public void testCreateReviewtByIdInvalidId() {
        String token = jwtService.generateToken("test");
        ReviewSubmit reviewSubmit = new ReviewSubmit();
        reviewSubmit.setToken(token);
        reviewSubmit.setRating(5d);
        reviewSubmit.setComment("comment");

        assertThrows(InvalidInputException.class, () -> {
            reviewServiceImplementation.createReview(null, reviewSubmit);
        });
    }

    @Test
    public void testCreateReviewtByNotRestaurantId() {
        Long restaurantId = 1L;
        String token = "test";
        ReviewSubmit reviewSubmit = new ReviewSubmit();
        reviewSubmit.setToken(token);
        reviewSubmit.setRating(5d);
        reviewSubmit.setComment("comment");

        assertThrows(ResourceNotFoundException.class, () -> {
            reviewServiceImplementation.createReview(restaurantId, reviewSubmit);
        });
    }

    @Test
    public void testCreateReviewtByInvalidToken() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantID(1L);
        restaurant.setName("Restaurant1");
        restaurant.setAddress("Address1");
        restaurant.setPhoneNumber("1234567890");
        restaurant.setLatitude(54.98394008492414);
        restaurant.setLongitude(-1.602402965403959);
        restaurant.setOverallRating(4.5);
        restaurant.setMenuLink("https://example.com/menu");
        restaurant.setWebsiteLink("https://example.com/restaurant");
        Cuisine cuisine = new Cuisine();
        cuisine.setId(1L);
        cuisine.setName("Cuisine1");
        restaurant.setCuisine(cuisine);

        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(1L, "Six Wings", 14.5));
        menuList.add(new Menu(1L, "Nine Wings", 19.0));
        menuList.add(new Menu(1L, "Ultimate Cheese Dipper", 19.25));
        menuList.add(new Menu(1L, "The Deluxe Wagyu Burger", 19.25));
        menuList.add(new Menu(1L, "To Vegan and Beyond", 14.5));
        menuList.add(new Menu(1L, "Fridays® Glazed Chicken Burger", 14.5));
        menuList.add(new Menu(1L, "Chicken Quesadilla", 14.5));
        menuList.add(new Menu(1L, "Tex-Mex Salad", 14.5));
        menuList.add(new Menu(1L, "Chicken Fingers", 14.5));
        menuList.add(new Menu(1L, "Cajun Spiced Chicken", 14.5));
        restaurant.setMenuItems(menuList);
        restaurant.setAverageCost(restaurant.averageCostOfADish());

        List<OperationHour> operationHours = new ArrayList<>();
        operationHours.add(new OperationHour(1L, "MONDAY", "11:30:00", "21:30:00"));
        operationHours.add(new OperationHour(1L, "TUESDAY", "11:30:00", "21:30:00"));
        operationHours.add(new OperationHour(1L, "WEDNESDAY", "11:30:00", "21:30:00"));
        operationHours.add(new OperationHour(1L, "THURSDAY", "11:30:00", "21:30:00"));
        operationHours.add(new OperationHour(1L, "FRIDAY", "11:30:00", "22:00:00"));
        operationHours.add(new OperationHour(1L, "SATURDAY", "11:30:00", "22:00:00"));
        operationHours.add(new OperationHour(1L, "SUNDAY", "11:30:00", "21:10:00"));
        restaurant.setOperationHours(operationHours);
        restaurant.setOperatingHoursOfTheWeek(restaurant.operatingHoursOfTheWeek());

        double userLatitude = 54.973372684718505;
        double userLongitude = -1.6257900913904035;

        restaurant.setDistanceFromUser(restaurant.distanceFromUser(userLatitude, userLongitude, restaurant.getLatitude(), restaurant.getLongitude()));
        restaurant.setApproximateWalkingTimeFromUser(walkingTimeFromUserTest(userLatitude, userLongitude, restaurant.getLatitude(), restaurant.getLongitude()));

        List<Image> imageList = new ArrayList<>();
        imageList.add(new Image(1L, "https://www.dawnvale.com/wp-content/uploads/projects/tgi-fridays-newcastle/TGI-Newcastle-Hero-1920x1080.jpg", restaurant));
        imageList.add(new Image(2L, "https://www.dawnvale.com/wp-content/uploads/projects/tgi-fridays-newcastle/TGI-Newcastle-1540x900-01.jpg", restaurant));
        imageList.add(new Image(3L, "https://www.tgifridays.co.uk/media/2582/legendary-dishes.jpg", restaurant));
        restaurant.setImages(imageList);
        restaurant.setImagesLink(restaurant.imagesLink());


        String token = "test";
        ReviewSubmit reviewSubmit = new ReviewSubmit();
        reviewSubmit.setToken(token);
        reviewSubmit.setRating(5d);
        reviewSubmit.setComment("comment");

        assertThrows(InvalidInputException.class, () -> {
            reviewServiceImplementation.createReview(restaurant.getRestaurantID(), reviewSubmit);
        });
    }


    private double walkingTimeFromUserTest(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        // Calculate approximate walking time based on distance and average walking speed
        double distance = calculateDistance(startLatitude, startLongitude, endLatitude, endLongitude);
        double walkingSpeed = 1.4; // Average walking speed in meters per second
        double walkingTimeSeconds = distance / walkingSpeed;
        double walkingTimeMinutes = walkingTimeSeconds / 60;
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(walkingTimeMinutes));
    }


    /**
     * This method returns the distance between two locations by using their latitude and longitude
     *
     * @param startLatitude  latitude of the starting point
     * @param startLongitude longitude of the starting point
     * @param endLatitude    latitude of the ending point
     * @param endLongitude   longitude of the ending point
     * @return distance between two locations
     */
    private double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        // Implementation of distance calculation using Haversine formula
        final int RADIUS_OF_EARTH = 6371; // Earth's radius in km

        double latDistance = Math.toRadians(endLatitude - startLatitude);
        double lonDistance = Math.toRadians(endLongitude - startLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(startLatitude)) * Math.cos(Math.toRadians(endLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = RADIUS_OF_EARTH * c * 1000; // convert to meters

        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(distance));
    }
}
