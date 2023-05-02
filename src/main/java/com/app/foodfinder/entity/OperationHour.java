package com.app.foodfinder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class represents a OperationHour entity.
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
@Table(name = "operation_hour")
public class OperationHour
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_Week", unique = true, nullable = false)
    private String dayOfWeek;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;
}

