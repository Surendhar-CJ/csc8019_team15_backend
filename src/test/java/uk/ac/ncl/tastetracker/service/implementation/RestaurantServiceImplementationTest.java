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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * RestaurantServiceImplementationTest is used to test RestaurantServiceImplementation class.
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


    /*
    @Test
    public void testGetRestaurantsByLocation()
    {
        double userLatitude = 54.973372684718505;
        double userLongitude = -1.6257900913904035;


        //Creating a Restaurant object
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantID(2L);
        restaurant1.setName("Five Guys");
        restaurant1.setAddress("2/4 Northumberland St, Newcastle upon Tyne NE1 7DE");
        restaurant1.setPhoneNumber("0191 261 5755");
        restaurant1.setLatitude(54.9744764300842);
        restaurant1.setLongitude(-1.61149876837645);
        restaurant1.setOverallRating(Double.valueOf(4));
        restaurant1.setMenuLink("https://fiveguys.co.uk/menu/?y_source=1_MTI0MzE1NzAtNzE1LWxvY2F0aW9uLm1lbnVfdXJs#tab-burgers");
        restaurant1.setWebsiteLink("https://restaurants.fiveguys.co.uk/north-east/2/4-northumberland-st");
        Cuisine cuisine1 = new Cuisine();
        cuisine1.setName("American");
        restaurant1.setCuisine(cuisine1);

        List<Menu> menuList1 = new ArrayList<>();
        menuList1.add(new Menu(1L, "Hamburger",8.65));
        menuList1.add(new Menu(2L, "Cheeseburger",9.95));
        menuList1.add(new Menu(3L, "Bacon Burger",10.45));
        menuList1.add(new Menu(4L, "Bacon Cheeseburger",10.65));
        menuList1.add(new Menu(5L, "Cheese Dog",7.35));
        menuList1.add(new Menu(6L, "Bacon Dog",7.85));
        menuList1.add(new Menu(7L, "Veggie Sandwich",5.5));

        restaurant1.setMenuItems(menuList1);
        restaurant1.setAverageCost(restaurant1.averageCostOfADish());

        List<OperationHour> operationHours1 = new ArrayList<>();
        operationHours1.add(new OperationHour(1L, "MONDAY", 	"11:00:00", 	"23:00:00"));
        operationHours1.add(new OperationHour(2L, "TUESDAY",	"11:00:00",	"21:00:00"));
        operationHours1.add(new OperationHour(3L, "WEDNESDAY","11:00:00","21:00:00"));
        operationHours1.add(new OperationHour(4L, "THURSDAY","11:00:00", "23:59:59"));
        operationHours1.add(new OperationHour(5L, "FRIDAY","09:00:00", "23:59:59"));
        operationHours1.add(new OperationHour(6L, "SATURDAY","09:00:00", "23:59:59"));
        operationHours1.add(new OperationHour(7L, "SUNDAY","11:00:00",	"23:00:00"));
        restaurant1.setOperationHours(operationHours1);
        restaurant1.setOperatingHoursOfTheWeek(restaurant1.operatingHoursOfTheWeek());




        restaurant1.setDistanceFromUser(restaurant1.distanceFromUser(userLatitude, userLongitude, restaurant1.getLatitude(), restaurant1.getLongitude()));
        restaurant1.setApproximateWalkingTimeFromUser(walkingTimeFromUserTest(userLatitude, userLongitude, restaurant1.getLatitude(), restaurant1.getLongitude()));

        List<Image> imageList1 = new ArrayList<>();
        imageList1.add(new Image(1L, "https://3.bp.blogspot.com/-LhnD8UlH7OA/V8GPoyarhYI/AAAAAAAAEhA/uur0DTQ1G8QEc9jYquJVT9tnPY_L4LwswCLcB/s1600/Interior.jpg", restaurant1));
        imageList1.add(new Image(2L, "https://fiveguys.co.uk/app/uploads/2020/12/fiveguys-burgers-1024x683.jpg", restaurant1));
        imageList1.add(new Image(3L, "https://4.bp.blogspot.com/-KeLK7SEAIM8/V8GPoBXERGI/AAAAAAAAEg4/bmQTia0RU8ssXkt6DqPpjAE00IeYUUV4ACLcB/s1600/Exterior.jpg", restaurant1));
        restaurant1.setImages(imageList1);
        restaurant1.setImagesLink(restaurant1.imagesLink());

        RestaurantDTO RestaurantDTO1 = restaurantDTOMapper.apply(restaurant1);


        //Creating a Restaurant object
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantID(2L);
        restaurant2.setName("Kafenoeon");
        restaurant2.setAddress("24, Stoddart Street, Newcastle upon Tyne NE1 7DE");
        restaurant2.setPhoneNumber("0191 261 5755");
        restaurant2.setLatitude(54.96934973360401);
        restaurant2.setLongitude(-1.609098028164953);
        restaurant2.setOverallRating(Double.valueOf(3.5));
        restaurant2.setMenuLink("https://menu-kafenoeon.com");
        restaurant2.setWebsiteLink("https://kafenoeon.com");
        Cuisine cuisine2 = new Cuisine();
        cuisine2.setName("Greek");
        restaurant2.setCuisine(cuisine2);

        List<Menu> menuList2 = new ArrayList<>();
        menuList2.add(new Menu(1L, "Ribeye Steak",23.5));
        menuList2.add(new Menu(2L, "Crispy Chicken Burger",15.5));
        menuList2.add(new Menu(3L, "Plant Based Burger",15.5));
        menuList2.add(new Menu(4L, "Panko Breaded Chicken",16.95));
        menuList2.add(new Menu(5L, "Pan Seared Seabass",16.95));
        menuList2.add(new Menu(6L, "Thai Red Vegetable Curry",14.95));
        menuList2.add(new Menu(7L, "Chef''s Special Braised Pork Belly",13.8));
        menuList2.add(new Menu(8L, "Chef''s Special Braised Pork Belly",13.8));

        restaurant2.setMenuItems(menuList2);
        restaurant2.setAverageCost(restaurant2.averageCostOfADish());

        List<OperationHour> operationHours2 = new ArrayList<>();
        operationHours2.add(new OperationHour(1L, "MONDAY", 	"11:00:00", 	"23:00:00"));
        operationHours2.add(new OperationHour(2L, "TUESDAY",	"11:00:00",	"21:00:00"));
        operationHours2.add(new OperationHour(3L, "WEDNESDAY","11:00:00","21:00:00"));
        operationHours2.add(new OperationHour(4L, "THURSDAY","11:00:00", "23:59:59"));
        operationHours2.add(new OperationHour(5L, "FRIDAY","09:00:00", "23:59:59"));
        operationHours2.add(new OperationHour(6L, "SATURDAY","09:00:00", "23:59:59"));
        operationHours2.add(new OperationHour(7L, "SUNDAY","11:00:00",	"23:00:00"));
        restaurant2.setOperationHours(operationHours2);
        restaurant2.setOperatingHoursOfTheWeek(restaurant2.operatingHoursOfTheWeek());



        restaurant2.setDistanceFromUser(restaurant2.distanceFromUser(userLatitude, userLongitude, restaurant2.getLatitude(), restaurant2.getLongitude()));
        restaurant2.setApproximateWalkingTimeFromUser(walkingTimeFromUserTest(userLatitude, userLongitude, restaurant2.getLatitude(), restaurant2.getLongitude()));

        List<Image> imageList2 = new ArrayList<>();
        imageList2.add(new Image(1L, "https://image2.jpg", restaurant2));
        imageList2.add(new Image(2L, "https://image4.jpg", restaurant2));
        imageList2.add(new Image(3L, "https://image4.jpg", restaurant2));
        restaurant2.setImages(imageList2);
        restaurant2.setImagesLink(restaurant2.imagesLink());

        RestaurantDTO RestaurantDTO2 = restaurantDTOMapper.apply(restaurant2);


        Restaurant restaurant3 = new Restaurant();
        restaurant3.setRestaurantID(2L);
        restaurant3.setName("Restaurant3");
        restaurant3.setAddress("24, Stoddart Street, Newcastle upon Tyne NE1 7DE");
        restaurant3.setPhoneNumber("0191 261 5755");
        restaurant3.setLatitude(44.96934973360401);
        restaurant3.setLongitude(-1.609098028164953);
        restaurant3.setOverallRating(Double.valueOf(3));
        restaurant3.setMenuLink("https://menu.com");
        restaurant3.setWebsiteLink("https://restaurant3.com");
        Cuisine cuisine3 = new Cuisine();
        cuisine3.setName("Greek");
        restaurant3.setCuisine(cuisine3);

        List<Menu> menuList3 = new ArrayList<>();
        menuList3.add(new Menu(1L, "Ribeye Steak",23.5));
        menuList3.add(new Menu(2L, "Crispy Chicken Burger",15.5));
        menuList3.add(new Menu(3L, "Plant Based Burger",15.5));
        menuList3.add(new Menu(4L, "Panko Breaded Chicken",16.95));
        menuList3.add(new Menu(5L, "Pan Seared Seabass",16.95));
        menuList3.add(new Menu(6L, "Thai Red Vegetable Curry",14.95));
        menuList3.add(new Menu(7L, "Chef''s Special Braised Pork Belly",13.8));
        menuList3.add(new Menu(8L, "Chef''s Special Braised Pork Belly",13.8));

        restaurant3.setMenuItems(menuList3);
        restaurant3.setAverageCost(restaurant3.averageCostOfADish());

        List<OperationHour> operationHours3 = new ArrayList<>();
        operationHours3.add(new OperationHour(1L, "MONDAY", 	"11:00:00", 	"23:00:00"));
        operationHours3.add(new OperationHour(2L, "TUESDAY",	"11:00:00",	"21:00:00"));
        operationHours3.add(new OperationHour(3L, "WEDNESDAY","11:00:00","21:00:00"));
        operationHours3.add(new OperationHour(4L, "THURSDAY","11:00:00", "23:59:59"));
        operationHours3.add(new OperationHour(5L, "FRIDAY","09:00:00", "23:59:59"));
        operationHours3.add(new OperationHour(6L, "SATURDAY","09:00:00", "23:59:59"));
        operationHours3.add(new OperationHour(7L, "SUNDAY","11:00:00",	"23:00:00"));
        restaurant3.setOperationHours(operationHours3);
        restaurant3.setOperatingHoursOfTheWeek(restaurant3.operatingHoursOfTheWeek());

        restaurant3.setDistanceFromUser(restaurant2.distanceFromUser(userLatitude, userLongitude, restaurant2.getLatitude(), restaurant2.getLongitude()));
        restaurant3.setApproximateWalkingTimeFromUser(walkingTimeFromUserTest(userLatitude, userLongitude, restaurant2.getLatitude(), restaurant2.getLongitude()));

        List<Image> imageList3 = new ArrayList<>();
        imageList3.add(new Image(1L, "https://image2.jpg", restaurant3));
        imageList3.add(new Image(2L, "https://image4.jpg", restaurant3));
        imageList3.add(new Image(3L, "https://image4.jpg", restaurant3));
        restaurant3.setImages(imageList3);
        restaurant3.setImagesLink(restaurant3.imagesLink());


        List<RestaurantDTO> expectedRestaurantDTOList = new ArrayList<>();

        when(restaurantRepository.findByRestaurantID(1L)).thenReturn(restaurant2);
    }
 */






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
