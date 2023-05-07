package uk.ac.ncl.tastetracker.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * This class represents the user location which contains latitude and longitude
 * It uses Lombok annotations to generate getters, setters, all argument constructor, equals/hashcode and toString methods.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.3 (28-04-2023)
 */
@Data
@AllArgsConstructor
public class UserLocation {

    /**
     * Represents the latitude of the user location.
     */
    private Double latitude;

    /**
     * Represents the longitude of the user location.
     */
    private Double longitude;
}
