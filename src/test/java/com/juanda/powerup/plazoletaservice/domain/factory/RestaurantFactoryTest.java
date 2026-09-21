package com.juanda.powerup.plazoletaservice.domain.factory;


import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;

import com.juanda.powerup.plazoletaservice.domain.model.RestaurantData;
import com.juanda.powerup.plazoletaservice.mother.RestaurantMother;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class RestaurantFactoryTest {


    @Test
    void shouldCreateRestaurantSuccessfully() {


        Restaurant restaurant =
                RestaurantFactory.create(

                        new RestaurantData(

                                RestaurantMother.validName(),

                                RestaurantMother.validNit(),

                                "Calle 10",

                                RestaurantMother.validPhone(),

                                "logo.png",

                                RestaurantMother.validOwnerId()
                        )
                );


        assertNotNull(
                restaurant
        );


        assertNotNull(
                restaurant.id()
        );
    }
}