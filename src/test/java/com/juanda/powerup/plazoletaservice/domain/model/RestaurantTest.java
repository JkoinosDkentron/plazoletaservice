package com.juanda.powerup.plazoletaservice.domain.model;


import com.juanda.powerup.plazoletaservice.mother.RestaurantMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class RestaurantTest {


    @Test
    void shouldCreateRestaurantSuccessfully() {


        Restaurant restaurant =
                RestaurantMother.validRestaurant();


        assertNotNull(restaurant);

        assertEquals(
                "Pizza House",
                restaurant.name().value()
        );

        assertEquals(
                "900123456",
                restaurant.nit().value()
        );
    }


    @Test
    void shouldAssignOwnerSuccessfully() {


        Restaurant restaurant =
                RestaurantMother.validRestaurant();


        assertNotNull(
                restaurant.ownerId()
        );

        assertNotNull(
                restaurant.ownerId().value()
        );
    }
}