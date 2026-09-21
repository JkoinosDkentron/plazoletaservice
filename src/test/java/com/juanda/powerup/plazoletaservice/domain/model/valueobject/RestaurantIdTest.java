package com.juanda.powerup.plazoletaservice.domain.model.valueobject;


import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import org.junit.jupiter.api.Test;


import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;


class RestaurantIdTest {


    @Test
    void shouldCreateRestaurantIdSuccessfully() {


        RestaurantId id =
                new RestaurantId(
                        UUID.randomUUID()
                );


        assertNotNull(
                id.value()
        );
    }



    @Test
    void shouldNotCreateRestaurantIdWithoutValue() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new RestaurantId(null)
        );
    }
}