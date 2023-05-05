package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class represents a Cuisine entity.
 * It uses Lombok annotations to generate getters, setters, constructors, equals/hashcode and toString methods at compile-time.
 *
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required) and not creating a schema.
 *       This has been disabled in application.properties file.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cuisine")
public class Cuisine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}

