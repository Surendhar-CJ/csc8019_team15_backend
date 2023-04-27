package com.app.foodfinder.service;

import com.app.foodfinder.dto.RestaurantDTO;
import com.app.foodfinder.entity.Restaurant;
import com.app.foodfinder.exception.NotFoundException;
import com.app.foodfinder.mapper.RestaurantDTOMapper;
import com.app.foodfinder.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImplementation implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final RestaurantDTOMapper restaurantDTOMapper;

    /* private static String API_KEY;

    @Value("${api.key}")
    public void setApiKey(String apiKey) {
        API_KEY = apiKey;
    } */

    @Autowired
    public RestaurantServiceImplementation(RestaurantRepository restaurantRepository, RestaurantDTOMapper restaurantDTOMapper)
    {
        this.restaurantRepository = restaurantRepository;
        this.restaurantDTOMapper = restaurantDTOMapper;
    }

    /*   @Override
    public List<Restaurant> getRestaurantsByArea(String area) throws IOException
    {
            List<Restaurant> restaurants = new ArrayList<>();

            //Constructing API url

            String apiUrl = "https://api.yelp.com/v3/businesses/search?location="+area;

            URL url = new URL(apiUrl);
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer "+API_KEY);

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while((line = input.readLine()) != null)
            {
                response.append(line);
            }
            input.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray businesses = jsonObject.getJSONArray("businesses");


            for(int i = 0; i < businesses.length(); i++)
            {
                JSONObject business =  businesses.getJSONObject(i);
                Boolean isClosed = business.getBoolean("is_closed");
                if(!isClosed)
                {
                    String name = business.getString("name");
                    JSONObject coordinates = business.getJSONObject("coordinates");
                    Double latitude = coordinates.getDouble("latitude");
                    Double longitude = coordinates.getDouble("longitude");
                    JSONObject location = business.getJSONObject("location");
                    String address = location.getString("address1") + ", " + location.getString("city") + " " + location.getString("zip_code");
                    /*JSONArray categories = business.getJSONArray("categories");
                    Cuisine cuisine = Cuisine.valueOf(categories.getJSONObject(0).getString("title").toUpperCase());*/
           /*         Cuisine cuisine = null;
                    Integer rating = business.getInt("rating");
                    Double averagePrice = null; // TODO: extract average price from the "price" field
                    String openingHours = null; // TODO: extract opening hours from the "hours" field


                    Restaurant restaurant = new Restaurant(name, address,  averagePrice, openingHours, latitude, longitude, rating, cuisine);
                    restaurants.add(restaurant);
                }
            }

            return restaurants;
    } */

    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId)
    {
        Restaurant restaurant = restaurantRepository.findByRestaurantID(restaurantId);

        if(restaurant == null)
        {
            throw new NotFoundException("Restaurant not found");
        }

        return restaurantDTOMapper.apply(restaurant);
    }




    /**
     * This method returns a list of open restaurants within one-mile radius of the latitude and longitude passed.
     *
     * @param latitude - latitude of the user's location
     * @param longitude - longitude of the user's location
     * @return list of open restaurants with one-mile radius.
     */
    @Override
    public List<RestaurantDTO> getRestaurantsByLocation(Double latitude, Double longitude)
    {
        final double ONE_MILE_IN_METERS = 1609.34; // 1 mile = 1609.34 meters

        List<Restaurant> nearbyRestaurants = new ArrayList<>();

        List<Restaurant> allRestaurants =  restaurantRepository.findAll();

        for (Restaurant restaurant : allRestaurants)
        {
            double restaurantLatitude = restaurant.getLatitude();
            double restaurantLongitude = restaurant.getLongitude();
            double distance = calculateDistance(latitude, longitude, restaurantLatitude, restaurantLongitude);

            boolean isOpen = restaurant.isOpen(LocalTime.now());

            if (distance <= ONE_MILE_IN_METERS && isOpen)
            {
                restaurant.setDistanceFromUser(distance);
                nearbyRestaurants.add(restaurant);
            }
        }

        return nearbyRestaurants.stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
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
    private double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2)
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
