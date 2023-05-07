package uk.ac.ncl.tastetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Menu class represents the Menu entity.An instance of the class can be represented by a field in the database.
 * This class uses Lombok annotations to generate getters, setters, no argument constructor, all argument constructor, equals/hashcode and toString methods.
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required)
 *       and not for creating the schema (so, constraints are not mentioned in the fields explicitly).
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.4 (01-05-2023)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {


    /**
     * The unique identifier of the menu item.
     * The column name corresponds to the column name "id" in the menu table in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the menu item.
     * The column name corresponds to the column name "itemName" in the menu table in the database.
     */
    @Column(name = "item_name")
    private String itemName;

    /**
     * The price of the menu item.
     * The column name corresponds to the column name "price" in the menu table in the database.
     */
    @Column(name = "price")
    private Double price;
}
