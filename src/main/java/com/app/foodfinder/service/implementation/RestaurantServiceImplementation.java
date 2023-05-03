package com.app.foodfinder.service.implementation;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.exception.custom.ResourceNotFoundException;
import com.app.foodfinder.dto.dtomapper.RestaurantDTOMapper;
import com.app.foodfinder.repository.RestaurantRepository;
import com.app.foodfinder.service.RestaurantService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class implements the RestaurantService interface and provides methods to interact with the RestaurantRepository
 * to retrieve restaurant information.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Service
public class RestaurantServiceImplementation implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantDTOMapper restaurantDTOMapper;

    /**
     * Represents the Google Maps API key to fetch the approximate walking time
     */
    @Value("${api.key}")
    private String apiKey;

    /**
     * Represents the Google Maps API URL key to fetch the approximate walking time
     */
    @Value("${google.maps.api.url}")
    private String googleMapsURL;



    /**
     * Constructor for RestaurantServiceImplementation that initializes the RestaurantRepository and RestaurantDTOMapper
     * objects using dependency injection.
     *
     * @param restaurantRepository - an object of type RestaurantRepository that provides CRUD functionality for the Restaurant entity
     * @param restaurantDTOMapper - an object of type RestaurantDTOMapper that maps between Restaurant and RestaurantDTO objects
     */
    @Autowired
    public RestaurantServiceImplementation(RestaurantRepository restaurantRepository, RestaurantDTOMapper restaurantDTOMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantDTOMapper = restaurantDTOMapper;
    }



    /**
     * This method retrieves a single Restaurant object based on the restaurant ID provided
     * and maps it to RestaurantDTO.
     *
     * @param restaurantId - the ID of the restaurant to retrieved
     *
     * @return a RestaurantDTO object representing the restaurant with the provided ID
     *
     * @throws ResourceNotFoundException if no restaurant is found with the provided ID
     */
    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        restaurant.setAverageCost(restaurant.averageCostOfADish());
        restaurant.setOperatingHoursOfTheDay(restaurant.operatingHoursOfTheDay());

        return restaurantDTOMapper.apply(restaurant);
    }




    /**
     * This method retrieves a single Restaurant object based on the restaurant ID provided
     * and maps it to RestaurantDTO.
     *
     * @param restaurantId - the ID of the restaurant to retrieved
     *
     * @return a RestaurantDTO object representing the restaurant with the provided ID
     *
     * @throws ResourceNotFoundException if no restaurant is found with the provided ID
     */
    @Override
    public RestaurantDTO getRestaurantByIdWithUserLocation(Long restaurantId, Double latitude, Double longitude) {
        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        double restaurantLatitude = restaurant.getLatitude();
        double restaurantLongitude = restaurant.getLongitude();
        double distance = restaurant.distanceFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude);

        //Sets the distance from the user
        restaurant.setDistanceFromUser(distance);
        //Sets average cost of a main course dish
        restaurant.setAverageCost(restaurant.averageCostOfADish());
        //Sets operating hours of the day
        restaurant.setOperatingHoursOfTheDay(restaurant.operatingHoursOfTheDay());
        //Sets the approximate walking time from the user
        restaurant.setApproximateWalkingTimeFromUser(walkingTimeFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude));

        return restaurantDTOMapper.apply(restaurant);
    }



    /**
     * This method retrieves a list of Restaurants objects representing all the open restaurants
     * within one-mile radius of the latitude and longitude passed and maps it to the list of RestaurantDTO.
     *
     * @param latitude - latitude of the user's location
     * @param longitude - longitude of the user's location
     *
     * @return list of Restaurant DTO objects representing the open restaurants within one-mile radius.
     */
    @Override
    public List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude) {
        final double ONE_MILE_IN_METERS = 1609.34; // 1 mile = 1609.34 meters

        List<Restaurant> nearbyRestaurants = new ArrayList<>();
        List<Restaurant> allRestaurants =  restaurantRepository.findAll();

        for (Restaurant restaurant : allRestaurants) {
            double restaurantLatitude = restaurant.getLatitude();
            double restaurantLongitude = restaurant.getLongitude();
            double distance = restaurant.distanceFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude);

            boolean isOpen = restaurant.isOpen();

            //Checks if the restaurant is within one-mile radius and open and adds it to the list
            if (distance <= ONE_MILE_IN_METERS && isOpen) {

                //Sets the distance from the user
                restaurant.setDistanceFromUser(distance);
                //Sets average cost of a main course dish
                restaurant.setAverageCost(restaurant.averageCostOfADish());
                //Sets operating hours of the day
                restaurant.setOperatingHoursOfTheDay(restaurant.operatingHoursOfTheDay());
                //Sets the approximate walking time from the user
                restaurant.setApproximateWalkingTimeFromUser(walkingTimeFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude));


                nearbyRestaurants.add(restaurant);
            }
        }

        return nearbyRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }




    /**
     * This method retrieves a list of Restaurant objects representing all the restaurants and
     * maps it to the list of RestaurantDTO objects.
     *
     * @return list of RestaurantDTO objects representing all the restaurants.
     */
    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> allRestaurants =  restaurantRepository.findAll();

        for(Restaurant restaurant : allRestaurants) {
            restaurant.setAverageCost(restaurant.averageCostOfADish());
            restaurant.setOperatingHoursOfTheDay(restaurant.operatingHoursOfTheDay());
        }

        return allRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }



    /**
     * This method returns the approximate walking time between the two locations by making an external
     * API call to Google Directions API.
     *
     * @param latitude1 - latitude of the first point
     * @param longitude1 - longitude of the first point
     * @param latitude2 - latitude of the second point
     * @param longitude2 - longitude of the second point
     * @return approximate walking time between two locations
     */
    public Double walkingTimeFromUser(double latitude1, double longitude1, double latitude2, double longitude2) {
        String urlString = googleMapsURL+ "?origin=" + latitude1 + "," + longitude1 +
                "&destination=" + latitude2 + "," + longitude2 +
                "&mode=walking&key=" + apiKey;

        try {
            //Creating a URL object from the URL String and opens a connection to it using HttpURLConnection.
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Reading the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //Converting the response data to a JSONObject, then extracting the route from it.
            JSONObject json = new JSONObject(response.toString());
            System.out.println(json);
            JSONArray routes = json.getJSONArray("routes");
            JSONObject route = routes.getJSONObject(0);
            JSONArray legs = route.getJSONArray("legs"); //
            JSONObject leg = legs.getJSONObject(0);
            JSONObject duration = leg.getJSONObject("duration");

            DecimalFormat df = new DecimalFormat("#");
            df.setRoundingMode(RoundingMode.DOWN);

            Double time = duration.getDouble("value") / 60.0; // walking time in minutes

            return Double.parseDouble(df.format(time));
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL"); // indicate an invalid URL
        } catch (IOException e) {
            throw new IllegalArgumentException("Unexpected error in the connection"); // indicate an error in the connection or reading from the stream
        } catch (JSONException e) {
            throw new IllegalArgumentException("Unexpected error in the response"); // indicate an error in parsing the JSON response
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Please check the API key and the URL"); // indicate a missing API key or Google Maps URL
        }

    }

}





