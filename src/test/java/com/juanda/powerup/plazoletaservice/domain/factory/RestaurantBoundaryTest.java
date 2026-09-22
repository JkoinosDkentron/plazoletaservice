package com.juanda.powerup.plazoletaservice.domain.factory;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantBoundaryTest {
    @Test
    void shouldRejectMissingFactoryData() {
        assertThrows(InvalidRestaurantException.class, () -> RestaurantFactory.create(null));
        assertThrows(InvalidRestaurantException.class, () -> RestaurantFactory.restore(null));
    }

    @Test
    void shouldRejectNumericNameSurroundedByUnicodeWhitespace() {
        assertThrows(InvalidRestaurantException.class, () -> new RestaurantName("\u2003123\u2003"));
    }
}
