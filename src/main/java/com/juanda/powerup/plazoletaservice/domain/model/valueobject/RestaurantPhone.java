package com.juanda.powerup.plazoletaservice.domain.model.valueobject;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;

import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.*;
import static com.juanda.powerup.plazoletaservice.domain.pattern.RestaurantValidationPattern.PHONE;
import static com.juanda.powerup.plazoletaservice.domain.rule.RestaurantValidationRule.MAX_PHONE_LENGTH;


public record RestaurantPhone(String value) {


    public RestaurantPhone {


        if (value == null || value.isBlank()) {
            throw new InvalidRestaurantException(
                    RESTAURANT_PHONE_REQUIRED
            );
        }


        if (value.length() > MAX_PHONE_LENGTH) {
            throw new InvalidRestaurantException(
                    RESTAURANT_PHONE_MAX_LENGTH
            );
        }


        if (!value.matches(PHONE)) {
            throw new InvalidRestaurantException(
                    RESTAURANT_PHONE_FORMAT
            );
        }
    }
}