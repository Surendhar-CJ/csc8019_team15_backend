package com.app.foodfinder.mapper;

import com.app.foodfinder.dto.CuisineDTO;
import com.app.foodfinder.entity.Cuisine;

import java.util.function.Function;

public class CuisineDTOMapper implements Function<Cuisine, CuisineDTO> {

    @Override
    public CuisineDTO apply(Cuisine cuisine) {
            return new CuisineDTO(cuisine.getId(), cuisine.getName());
    }
}
