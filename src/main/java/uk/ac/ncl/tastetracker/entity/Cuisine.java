package uk.ac.ncl.tastetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Cuisine class represents the Cuisine entity. An instance of the class can be represented by a field in the database.
 * This class uses Lombok annotations to generate getters, setters, no argument constructor, all argument constructor, equals/hashcode and toString methods.
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required)
 *       and not for creating the schema (so, constraints are not mentioned in the fields explicitly).
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cuisine")
public class Cuisine {


    /**
     * Represents the ID of the Cuisine.
     * The column name corresponds to the column name "id" in the cuisine table in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    /**
     * Represents the name of the cuisine
     * The column name corresponds to the column name "name" in the cuisine table in the database.
     */
    @Column(name = "name")
    private String name;
}

