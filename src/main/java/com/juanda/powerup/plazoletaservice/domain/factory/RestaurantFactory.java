package com.juanda.powerup.plazoletaservice.domain.factory;


import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;
import com.juanda.powerup.plazoletaservice.domain.model.RestaurantData;
import com.juanda.powerup.plazoletaservice.domain.model.RestaurantRestoreData;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantId;
import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.RESTAURANT_DATA_REQUIRED;

import java.util.UUID;


public final class RestaurantFactory {


    private RestaurantFactory(){

    }



    public static Restaurant create(
            RestaurantData data
    ){
        if (data == null) {
            throw new InvalidRestaurantException(RESTAURANT_DATA_REQUIRED);
        }

        return new Restaurant(
                new RestaurantId(UUID.randomUUID()),
                data.name(),
                data.nit(),
                data.address(),
                data.phone(),
                data.urlLogo(),
                data.ownerId()
        );
    }



    public static Restaurant restore(
            RestaurantRestoreData data
    ){
        if (data == null) {
            throw new InvalidRestaurantException(RESTAURANT_DATA_REQUIRED);
        }

        return new Restaurant(
                data.id(),
                data.name(),
                data.nit(),
                data.address(),
                data.phone(),
                data.urlLogo(),
                data.ownerId()
        );
    }
}
