package com.juanda.powerup.plazoletaservice.domain.model.valueobject;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;

import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.*;
import static com.juanda.powerup.plazoletaservice.domain.pattern.RestaurantValidationPattern.ONLY_NUMBERS;


public record RestaurantName(String value) {


    public RestaurantName {

        if (value == null || value.isBlank()) {
            throw new InvalidRestaurantException(
                    RESTAURANT_NAME_REQUIRED
            );
        }


        value = value.trim();
        if (value.matches(ONLY_NUMBERS)) {
            throw new InvalidRestaurantException(
                    RESTAURANT_NAME_ONLY_NUMBERS
            );
        }


    }
}
