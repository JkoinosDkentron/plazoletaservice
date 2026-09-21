package com.juanda.powerup.plazoletaservice.domain.model.valueobject;


import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import org.junit.jupiter.api.Test;


import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;


class OwnerIdTest {


    @Test
    void shouldCreateOwnerIdSuccessfully() {


        OwnerId ownerId =
                new OwnerId(
                        UUID.randomUUID()
                );


        assertNotNull(
                ownerId.value()
        );
    }



    @Test
    void shouldNotCreateOwnerIdWithoutValue() {


        assertThrows(
                InvalidRestaurantException.class,
                () -> new OwnerId(null)
        );
    }
}