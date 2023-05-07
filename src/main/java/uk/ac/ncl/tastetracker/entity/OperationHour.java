package uk.ac.ncl.tastetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * OperationHour class represents the OperationHour entity. An instance of the class can be represented by a field in the database.
 * This class uses Lombok annotations to generate getters, setters, no argument constructor, all argument constructor, equals/hashcode and toString methods.
 * NOTE: Here, Spring Data JPA/Hibernate is used only to fetch results from the database(so table mapping is required)
 *       and not for creating the schema (so, constraints are not mentioned in the fields explicitly)
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (06-05-2023)
 * @since 1.0 (17-04-2023)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation_hour")
public class OperationHour
{

    /**
     * The unique identifier of the operation hour.
     * The column name corresponds to the column name "id" in the operation_hour table in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The day of the week for which the operation hours are applicable.
     * The column name corresponds to the column name "day_of_week" in the operation_hour table in the database.
     */
    @Column(name = "day_of_Week")
    private String dayOfWeek;

    /**
     * The opening time of the restaurant for the given day of the week.
     * The column name corresponds to the column name "opening_time" in the operation_hour table in the database.
     */
    @Column(name = "opening_time")
    private String openingTime;

    /**
     * The closing time of the restaurant for the given day of the week.
     * The column name corresponds to the column name "closing_time" in the operation_hour table in the database.
     */
    @Column(name = "closing_time")
    private String closingTime;


}

