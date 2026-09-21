package com.juanda.powerup.plazoletaservice.domain.usecase;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import com.juanda.powerup.plazoletaservice.domain.exception.OwnerValidationUnavailableException;
import com.juanda.powerup.plazoletaservice.domain.model.OwnerValidation;
import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;
import com.juanda.powerup.plazoletaservice.domain.spi.IOwnerValidationPort;
import com.juanda.powerup.plazoletaservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    @Mock private IRestaurantPersistencePort persistence;
    @Mock private IOwnerValidationPort owners;
    private RestaurantUseCase useCase;
    private final UUID ownerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        useCase = new RestaurantUseCase(persistence, owners);
    }

    @Test
    void shouldSaveRestaurantForValidOwner() {
        when(owners.validateOwner(ownerId)).thenReturn(new OwnerValidation(true, true));
        when(persistence.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant result = create("Pizza 123", "Calle 10", "logo.png");

        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        verify(persistence).save(captor.capture());
        verify(owners).validateOwner(ownerId);
        assertSame(captor.getValue(), result);
        assertAll(
                () -> assertNotNull(result.id().value()),
                () -> assertEquals("Pizza 123", result.name().value()),
                () -> assertEquals("900123456", result.nit().value()),
                () -> assertEquals("Calle 10", result.address()),
                () -> assertEquals("+573001234567", result.phone().value()),
                () -> assertEquals("logo.png", result.urlLogo()),
                () -> assertEquals(ownerId, result.ownerId().value()));
    }

    @Test
    void shouldRejectUserWithoutOwnerRole() {
        when(owners.validateOwner(ownerId)).thenReturn(new OwnerValidation(true, false));
        assertThrows(InvalidRestaurantException.class, () -> create("Pizza", "Calle 10", "logo.png"));
        verifyNoInteractions(persistence);
    }

    @Test
    void shouldRejectMissingUser() {
        when(owners.validateOwner(ownerId)).thenReturn(new OwnerValidation(false, false));
        assertThrows(InvalidRestaurantException.class, () -> create("Pizza", "Calle 10", "logo.png"));
        verifyNoInteractions(persistence);
    }

    @Test
    void shouldNotSaveWhenOwnerServiceIsUnavailable() {
        when(owners.validateOwner(ownerId))
                .thenThrow(new OwnerValidationUnavailableException("Unavailable", null));
        assertThrows(OwnerValidationUnavailableException.class,
                () -> create("Pizza", "Calle 10", "logo.png"));
        verifyNoInteractions(persistence);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void shouldRejectMissingAddressBeforeCallingPorts(String address) {
        assertThrows(InvalidRestaurantException.class, () -> create("Pizza", address, "logo.png"));
        verifyNoInteractions(owners, persistence);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void shouldRejectMissingLogoBeforeCallingPorts(String logo) {
        assertThrows(InvalidRestaurantException.class, () -> create("Pizza", "Calle 10", logo));
        verifyNoInteractions(owners, persistence);
    }

    @Test
    void shouldRejectNullOwnerBeforeCallingPorts() {
        assertThrows(InvalidRestaurantException.class,
                () -> useCase.createRestaurant("Pizza", "900", "Calle 10", "123", "logo.png", null));
        verifyNoInteractions(owners, persistence);
    }

    @Test
    void shouldRejectNumericNameSurroundedBySpaces() {
        assertThrows(InvalidRestaurantException.class, () -> create(" 123 ", "Calle 10", "logo.png"));
        verifyNoInteractions(owners, persistence);
    }

    private Restaurant create(String name, String address, String logo) {
        return useCase.createRestaurant(name, "900123456", address, "+573001234567", logo, ownerId);
    }
}
