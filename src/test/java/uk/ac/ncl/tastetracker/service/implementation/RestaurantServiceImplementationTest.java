package uk.ac.ncl.tastetracker.service.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.tastetracker.dto.RestaurantDTO;
import uk.ac.ncl.tastetracker.dto.dtomapper.RestaurantDTOMapper;
import uk.ac.ncl.tastetracker.entity.*;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.repository.RestaurantRepository;
import uk.ac.ncl.tastetracker.service.RestaurantService;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * RestaurantServiceImplementationTest is used to test RestaurantServiceImplementation class.
 *
 * @author Surendhar Chandran Jayapal
 * @since 1.5 (06-05-2023)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantServiceImplementationTest {

    /**
     * RestaurantServiceImplementation dependency to test the methods of that class
     */
    @Autowired
   private RestaurantServiceImplementation restaurantServiceImplementation;

    /**
     * RestaurantDTOMapper that maps Restaurant objects to RestaurantDTOs
     */
    @Autowired
   private RestaurantDTOMapper restaurantDTOMapper;

    /**
     * Mock of Restaurant Repository
     */
    @MockBean
   private RestaurantRepository restaurantRepository;


    /**
     * This method tests getRestaurantById() method of RestaurantServiceImplementation class
     */
    @Test
    public void testGetRestaurantById() {

        //Creating a Restaurant object
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
        operationHours.add(new OperationHour(1L, "MONDAY","11:30:00","21:30:00"));
        operationHours.add(new OperationHour(1L, "TUESDAY","11:30:00","21:30:00"));
        operationHours.add(new OperationHour(1L, "WEDNESDAY","11:30:00","21:30:00"));
        operationHours.add(new OperationHour(1L, "THURSDAY","11:30:00","21:30:00"));
        operationHours.add(new OperationHour(1L, "FRIDAY","11:30:00","22:00:00"));
        operationHours.add(new OperationHour(1L, "SATURDAY","11:30:00","22:00:00"));
        operationHours.add(new OperationHour(1L, "SUNDAY","11:30:00","21:10:00"));
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

        RestaurantDTO expectedRestaurantDTO = restaurantDTOMapper.apply(restaurant);
        System.out.println(expectedRestaurantDTO);
        when(restaurantRepository.findByRestaurantID(1L)).thenReturn(restaurant);



        /*
         *  Creating a spy for RestaurantServiceImplementation to mock the walkingTimeFromUser which calls Google Directions API
         *  to get the approximate walking time in the actual implementation. By creating a spy and using a custom-made "walkingTimeFromUserTest" method
         *  instead of walkingTimeFromUser method of the RestaurantServiceImplementation class we can avoid calling Google Directions API during testing.
         *  The walkingTimeFromUserTest method uses a constant walking speed of 1.4m/s.
         */
        RestaurantServiceImplementation restaurantServiceSpy = Mockito.spy(restaurantServiceImplementation);

        Mockito.doAnswer(invocation -> {
            double latitude1 = invocation.getArgument(0);
            double longitude1 = invocation.getArgument(1);
            double latitude2 = invocation.getArgument(2);
            double longitude2 = invocation.getArgument(3);
            return walkingTimeFromUserTest(latitude1, longitude1, latitude2, longitude2);
        }).when(restaurantServiceSpy).walkingTimeFromUser(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble());

        RestaurantDTO actualRestaurantDTO = restaurantServiceSpy.getRestaurantById(restaurant.getRestaurantID(), userLatitude, userLongitude);



        assertEquals(expectedRestaurantDTO, actualRestaurantDTO);

        //Checks if the actual value equals to the expected value
        assertEquals(expectedRestaurantDTO.id(), actualRestaurantDTO.id());
        assertEquals(expectedRestaurantDTO.name(), actualRestaurantDTO.name());
        assertEquals(expectedRestaurantDTO.address(), actualRestaurantDTO.address());
        assertEquals(expectedRestaurantDTO.phoneNumber(), actualRestaurantDTO.phoneNumber());
        assertEquals(expectedRestaurantDTO.latitude(), actualRestaurantDTO.latitude());
        assertEquals(expectedRestaurantDTO.longitude(), actualRestaurantDTO.longitude());
        assertEquals(expectedRestaurantDTO.overallRating(), actualRestaurantDTO.overallRating());
        assertEquals(expectedRestaurantDTO.cuisine(), actualRestaurantDTO.cuisine());
        assertEquals(expectedRestaurantDTO.operatingHoursOfTheWeek(), actualRestaurantDTO.operatingHoursOfTheWeek());
        assertEquals(expectedRestaurantDTO.menuLink(), actualRestaurantDTO.menuLink());
        assertEquals(expectedRestaurantDTO.websiteLink(), actualRestaurantDTO.websiteLink());
        assertEquals(expectedRestaurantDTO.averageCostOfADish(), actualRestaurantDTO.averageCostOfADish());
        assertEquals(expectedRestaurantDTO.distanceFromUser(), actualRestaurantDTO.distanceFromUser());
        assertEquals(expectedRestaurantDTO.approximateWalkingTimeFromUser(), actualRestaurantDTO.approximateWalkingTimeFromUser());
        assertEquals(expectedRestaurantDTO.imagesLink(), actualRestaurantDTO.imagesLink());

    }


    @Test
    public void testGetRestaurantByIdInvalidId() {
    Long restaurantId = null;
    Double latitude = 54.98394008492414;
    Double longitude = -1.602402965403959;

    assertThrows(InvalidInputException.class, () -> {
        restaurantServiceImplementation.getRestaurantById(restaurantId, latitude, longitude);
    });
    }


    @Test
    public void testGetRestaurantByIdInvalidLatitude() {
    Long restaurantId = 1L;
    Double latitude = null;
    Double longitude = -1.602402965403959;

    assertThrows(InvalidInputException.class, () -> {
        restaurantServiceImplementation.getRestaurantById(restaurantId, latitude, longitude);
    });
    }


    @Test
    public void testGetRestaurantByIdInvalidLongitude() {
    Long restaurantId = 1L;
    Double latitude = 54.98394008492414;
    Double longitude = null;

    assertThrows(InvalidInputException.class, () -> {
        restaurantServiceImplementation.getRestaurantById(restaurantId, latitude, longitude);
    });
    }

    @Test
    public void testGetRestaurantByIdInvalidLatitude1() {
        Long restaurantId = 1L;
        Double latitude = null;
        Double longitude = -1.602402965403959;

        assertThrows(InvalidInputException.class, () -> {
            restaurantServiceImplementation.getRestaurantById(restaurantId, latitude, longitude);
        });
    }



    @Test
    public void testGetRestaurantsByLocation()
    {

    }














    /**
     * This method returns the approximate walking time between two locations by assuming a constant speed of 1.4 m/s
     * This method is used to avoid calling Google Directions API during testing.
     *
     * @param startLatitude latitude of the starting point
     * @param startLongitude longitude of the starting point
     * @param endLatitude latitude of the ending point
     * @param endLongitude longitude of the ending point
     * @return approximate walking time between two locations
     */
    private double walkingTimeFromUserTest(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        // Calculate approximate walking time based on distance and average walking speed
        double distance = calculateDistance(startLatitude, startLongitude, endLatitude, endLongitude);
        double walkingSpeed = 1.4; // Average walking speed in meters per second
        double walkingTimeSeconds = distance / walkingSpeed;
        double walkingTimeMinutes =  walkingTimeSeconds / 60;
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(walkingTimeMinutes));
    }



    /**
     * This method returns the distance between two locations by using their latitude and longitude
     *
     * @param startLatitude latitude of the starting point
     * @param startLongitude longitude of the starting point
     * @param endLatitude latitude of the ending point
     * @param endLongitude longitude of the ending point
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
