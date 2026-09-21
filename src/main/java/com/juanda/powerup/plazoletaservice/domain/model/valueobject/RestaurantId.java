package com.juanda.powerup.plazoletaservice.domain.model.valueobject;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;

import java.util.UUID;

import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.RESTAURANT_ID_REQUIRED;


public record RestaurantId(UUID value) {


    public RestaurantId {

        if (value == null) {
            throw new InvalidRestaurantException(
                    RESTAURANT_ID_REQUIRED
            );
        }
    }
}