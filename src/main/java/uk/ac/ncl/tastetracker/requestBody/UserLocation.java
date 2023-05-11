package uk.ac.ncl.tastetracker.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * User Location class represents the user location which contains latitude and longitude
 * It uses Lombok annotations to generate getters, setters, all argument constructor, equals/hashcode and toString methods.
 *
 * @author Surendhar Chandran Jayapal
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
