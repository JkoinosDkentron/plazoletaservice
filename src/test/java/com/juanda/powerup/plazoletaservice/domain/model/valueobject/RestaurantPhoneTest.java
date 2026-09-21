package com.juanda.powerup.plazoletaservice.domain.model.valueobject;


import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class RestaurantPhoneTest {


    @Test
    void shouldCreatePhoneSuccessfully() {


        RestaurantPhone phone =
                new RestaurantPhone(
                        "+573001234567"
                );


        assertEquals(
                "+573001234567",
                phone.value()
        );
    }



    @Test
    void shouldNotCreatePhoneWithInvalidFormat() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new RestaurantPhone(
                        "300ABC"
                )
        );
    }



    @Test
    void shouldNotCreatePhoneGreaterThanMaximumLength() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new RestaurantPhone(
                        "+57300123456789"
                )
        );
    }
}