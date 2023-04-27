package com.app.foodfinder.repository;

import com.app.foodfinder.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
}
