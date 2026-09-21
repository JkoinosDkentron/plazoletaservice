package com.juanda.powerup.plazoletaservice.domain.model.valueobject;


import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class NitTest {


    @Test
    void shouldCreateNitSuccessfully() {


        Nit nit =
                new Nit(
                        "900123456"
                );


        assertEquals(
                "900123456",
                nit.value()
        );
    }



    @Test
    void shouldNotCreateNitWithLetters() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new Nit("900ABC")
        );
    }



    @Test
    void shouldNotCreateEmptyNit() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new Nit("")
        );
    }
}