package uk.ac.ncl.tastetracker.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Image class represents the Image entity.An instance of the class can be represented by a field in the database.
 * This class uses Lombok annotations to generate getters, setters, no argument constructor, all argument constructor, equals/hashcode and toString methods.
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required)
 *       and not for creating the schema (so, constraints are not mentioned in the fields).
 *
 * @author Surendhar Chandran Jayapal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {


    /**
     * The unique identifier of the image.
     * The column name corresponds to the column name "id" in the image table in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The link to the image file
     * The column name corresponds to the column name "image_link" in the image table in the database.
     */
    @Column(name = "image_link")
    private String imageLink;

    /**
     * The restaurant that the image is associated with.
     * The column name corresponds to the column name "restaurant_id" in the image table in the database.
     * JsonIgnore ignores the restaurant information while parsing.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;


}
