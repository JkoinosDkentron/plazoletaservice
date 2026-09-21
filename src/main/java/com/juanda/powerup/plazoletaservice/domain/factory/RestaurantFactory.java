package com.juanda.powerup.plazoletaservice.domain.factory;


import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;
import com.juanda.powerup.plazoletaservice.domain.model.RestaurantData;
import com.juanda.powerup.plazoletaservice.domain.model.RestaurantRestoreData;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantId;

import java.util.UUID;


public final class RestaurantFactory {


    private RestaurantFactory(){

    }



    public static Restaurant create(
            RestaurantData data
    ){

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