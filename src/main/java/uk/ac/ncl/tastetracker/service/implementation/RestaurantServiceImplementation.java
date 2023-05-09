package uk.ac.ncl.tastetracker.service.implementation;

import uk.ac.ncl.tastetracker.dto.RestaurantDTO;
import uk.ac.ncl.tastetracker.entity.Restaurant;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.exception.custom.ResourceNotFoundException;
import uk.ac.ncl.tastetracker.dto.dtomapper.RestaurantDTOMapper;
import uk.ac.ncl.tastetracker.repository.RestaurantRepository;
import uk.ac.ncl.tastetracker.service.RestaurantService;
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
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
 */
@Service
public class RestaurantServiceImplementation implements RestaurantService {

    /**
     * RestaurantRepository that performs database related operations.
     */
    private final RestaurantRepository restaurantRepository;

    /**
     * RestaurantDTOMapper to map Restaurant objects to RestaurantDTO
     */
    private final RestaurantDTOMapper restaurantDTOMapper;

    /**
     * Represents the Google Maps API key to fetch the approximate walking time.
     * The value is fetched from application.properties file.
     */
    @Value("${api.key}")
    private String apiKey;

    /**
     * Represents the Google Maps API URL key to fetch the approximate walking time.
     * The value is fetched from application.properties file.
     */
    @Value("${google.directions.api.url}")
    private String googleDirectionsApiBaseUrl;



    /**
     * Constructor for RestaurantServiceImplementation that initializes the RestaurantRepository and RestaurantDTOMapper
     * objects using dependency injection.
     *
     * @param restaurantRepository - an object of type RestaurantRepository that provides CRUD functionality for the Restaurant entity
     * @param restaurantDTOMapper - an object of type RestaurantDTOMapper that maps between Restaurant and RestaurantDTO objects
     *
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
     * @throws InvalidInputException if the latitude, longitude or the restaurantId is invalid
     */
    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId, Double latitude, Double longitude) {

        if (restaurantId == null || Double.isNaN(restaurantId.doubleValue()) ) {
            throw new InvalidInputException("Invalid Restaurant ID");
        }

        //Validates location
        validateLocation(latitude, longitude);

        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }


        double restaurantLatitude = restaurant.getLatitude();
        double restaurantLongitude = restaurant.getLongitude();
        double distance = restaurant.distanceFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude);

        restaurant.setDistanceFromUser(distance);
        restaurant.setAverageCost(restaurant.averageCostOfADish());
        restaurant.setApproximateWalkingTimeFromUser(walkingTimeFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude));
        restaurant.setImagesLink(restaurant.imagesLink());
        restaurant.setOperatingHoursOfTheWeek(restaurant.operatingHoursOfTheWeek());


        //Maps Restaurant to RestaurantDTO
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
     *
     * @throws InvalidInputException if the latitude and longitude passed are invalid.
     */
    @Override
    public List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude) {

        validateLocation(latitude, longitude);

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

                restaurant.setDistanceFromUser(distance);
                restaurant.setAverageCost(restaurant.averageCostOfADish());
                restaurant.setApproximateWalkingTimeFromUser(walkingTimeFromUser(latitude, longitude, restaurantLatitude, restaurantLongitude));
                restaurant.setImagesLink(restaurant.imagesLink());
                restaurant.setOperatingHoursOfTheWeek(restaurant.operatingHoursOfTheWeek());

                nearbyRestaurants.add(restaurant);
            }
        }


        //Maps List of Restaurants to a List of Restaurant DTO
        return nearbyRestaurants.stream()
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
     *
     * @return approximate walking time between two locations
     *
     * @throws InvalidInputException if the latitudes or longitudes passed are invalid.
     *                               if the url is invalid, if the API key is invalid or missing
     * @throws IllegalArgumentException if there is any connectivity problem
     * @throws JSONException if there is an error in parsing JSON response
     */
    public Double walkingTimeFromUser(double latitude1, double longitude1, double latitude2, double longitude2) {

        validateLocation(latitude1, longitude1);
        validateLocation(latitude2, longitude2);

        String urlString = buildGoogleDirectionsApiUrl(latitude1, longitude1, latitude2, longitude2);

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
            throw new InvalidInputException("Invalid URL"); // indicate an invalid URL
        } catch (IOException e) {
            throw new IllegalArgumentException("Unexpected error in the connection"); // indicate an error in the connection or reading from the stream
        } catch (JSONException e) {
            throw new JSONException("Unexpected error in the response"); // indicate an error in parsing the JSON response
        } catch (NullPointerException e) {
            throw new InvalidInputException("Please check the API key and the URL"); // indicate a missing API key or Google Maps URL
        }

    }





    /**
     * This method validates if the location is valid or not
     *
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     *
     * @throws InvalidInputException if the latitude and longitude passed are invalid
     */
    private void validateLocation(Double latitude, Double longitude) {
        if (latitude == null || Double.isNaN(latitude)) {
            throw new InvalidInputException("Invalid latitude");
        }
        else if (longitude == null || Double.isNaN(longitude)) {
            throw new InvalidInputException("Invalid longitude");
        }
        else if (latitude.toString().trim().isEmpty() || longitude.toString().trim().isEmpty()) {
            throw new InvalidInputException("Latitude or longitude cannot be empty or blank");
        }
        else {
            try {
                double lat = Double.parseDouble(latitude.toString());
                double lng = Double.parseDouble(longitude.toString());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid latitude or longitude");
            }
        }

    }





    /**
     * Builds Google Directions API Url based on the latitudes and longitudes passed as the arguments.
     * It uses the Google Directions API base url and api key from the application.properties file
     * to construct the url.
     *
     * @param latitude1 - latitude of the first location
     * @param longitude1 - longitude of the first location
     * @param latitude2 - latitude of the second location
     * @param longitude2 - longitude of the second location
     *
     * @return the Google Maps API url build from the arguments passed
     */
    private String buildGoogleDirectionsApiUrl(double latitude1, double longitude1, double latitude2, double longitude2) {
        return googleDirectionsApiBaseUrl.replace("{latitude1}", String.valueOf(latitude1))
                .replace("{longitude1}", String.valueOf(longitude1))
                .replace("{latitude2}", String.valueOf(latitude2))
                .replace("{longitude2}", String.valueOf(longitude2))
                .replace("{apiKey}", apiKey);
    }



}





