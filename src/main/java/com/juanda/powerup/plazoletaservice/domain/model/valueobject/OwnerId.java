package com.juanda.powerup.plazoletaservice.domain.model.valueobject;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;

import java.util.UUID;

import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.OWNER_ID_REQUIRED;


public record OwnerId(UUID value) {


    public OwnerId {

        if (value == null) {
            throw new InvalidRestaurantException(
                    OWNER_ID_REQUIRED
            );
        }
    }
}