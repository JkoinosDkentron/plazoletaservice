package com.juanda.powerup.plazoletaservice.infrastructure.out.client;

import com.juanda.powerup.plazoletaservice.domain.exception.OwnerValidationUnavailableException;
import com.juanda.powerup.plazoletaservice.domain.model.OwnerValidation;
import com.juanda.powerup.plazoletaservice.infrastructure.out.client.dto.OwnerValidationResponse;
import com.juanda.powerup.plazoletaservice.infrastructure.out.client.feign.UserClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerValidationAdapterTest {
    private final UserClient client = mock(UserClient.class);
    private final OwnerValidationAdapter adapter = new OwnerValidationAdapter(client);
    private final UUID ownerId = UUID.randomUUID();

    @Test
    void shouldPreserveEachBusinessValidationResult() {
        for (OwnerValidationResponse response : new OwnerValidationResponse[] {
                new OwnerValidationResponse(true, true),
                new OwnerValidationResponse(true, false),
                new OwnerValidationResponse(false, false)}) {
            when(client.validateOwner(ownerId)).thenReturn(response);
            assertEquals(new OwnerValidation(response.exists(), response.owner()), adapter.validateOwner(ownerId));
        }
    }

    @Test
    void shouldRejectIncompleteOrInconsistentResponses() {
        for (OwnerValidationResponse response : new OwnerValidationResponse[] {
                null, new OwnerValidationResponse(null, true),
                new OwnerValidationResponse(true, null), new OwnerValidationResponse(false, true)}) {
            when(client.validateOwner(ownerId)).thenReturn(response);
            assertThrows(OwnerValidationUnavailableException.class, () -> adapter.validateOwner(ownerId));
        }
    }

    @Test
    void shouldTranslateFeignFailuresWithoutLeakingTransportToDomain() {
        FeignException failure = mock(FeignException.class);
        when(client.validateOwner(ownerId)).thenThrow(failure);
        var exception = assertThrows(OwnerValidationUnavailableException.class,
                () -> adapter.validateOwner(ownerId));
        assertSame(failure, exception.getCause());
    }
}
