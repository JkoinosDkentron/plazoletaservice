package com.juanda.powerup.plazoletaservice.mother;


import com.juanda.powerup.plazoletaservice.domain.factory.RestaurantFactory;
import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;
import com.juanda.powerup.plazoletaservice.domain.model.RestaurantData;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.Nit;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.OwnerId;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantName;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantPhone;

import java.util.UUID;


public final class RestaurantMother {


    private RestaurantMother() {
    }



    public static Restaurant validRestaurant() {


        return validRestaurant(
                UUID.randomUUID()
        );
    }



    public static Restaurant validRestaurant(
            UUID ownerId
    ) {


        return RestaurantFactory.create(
                new RestaurantData(

                        validName(),

                        validNit(),

                        "Calle 10 #20-30",

                        validPhone(),

                        "https://logo.com/logo.png",

                        new OwnerId(
                                ownerId
                        )
                )
        );
    }



    public static RestaurantName validName() {

        return new RestaurantName(
                "Pizza House"
        );
    }



    public static Nit validNit() {

        return new Nit(
                "900123456"
        );
    }



    public static RestaurantPhone validPhone() {

        return new RestaurantPhone(
                "+573001234567"
        );
    }



    public static OwnerId validOwnerId() {

        return new OwnerId(
                UUID.randomUUID()
        );
    }
}