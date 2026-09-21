package com.juanda.powerup.plazoletaservice.domain.model.valueobject;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;

import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.*;
import static com.juanda.powerup.plazoletaservice.domain.pattern.RestaurantValidationPattern.NIT;


public record Nit(String value) {


    public Nit {

        if (value == null || value.isBlank()) {
            throw new InvalidRestaurantException(
                    RESTAURANT_NIT_REQUIRED
            );
        }


        if (!value.matches(NIT)) {
            throw new InvalidRestaurantException(
                    RESTAURANT_NIT_NUMERIC
            );
        }
    }
}