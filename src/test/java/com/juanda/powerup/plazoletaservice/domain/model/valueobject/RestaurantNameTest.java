package com.juanda.powerup.plazoletaservice.domain.model.valueobject;


import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class RestaurantNameTest {


    @Test
    void shouldCreateNameSuccessfully() {


        RestaurantName name =
                new RestaurantName(
                        "Pizza 123"
                );


        assertEquals(
                "Pizza 123",
                name.value()
        );
    }



    @Test
    void shouldNotCreateNameEmpty() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new RestaurantName("")
        );
    }



    @Test
    void shouldNotCreateNameWithOnlyNumbers() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new RestaurantName("123456")
        );
    }
}