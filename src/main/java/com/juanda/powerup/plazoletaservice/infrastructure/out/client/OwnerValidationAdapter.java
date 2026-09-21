package com.juanda.powerup.plazoletaservice.infrastructure.out.client;


import com.juanda.powerup.plazoletaservice.domain.model.OwnerValidation;
import com.juanda.powerup.plazoletaservice.domain.exception.OwnerValidationUnavailableException;
import feign.FeignException;
import com.juanda.powerup.plazoletaservice.domain.spi.IOwnerValidationPort;
import com.juanda.powerup.plazoletaservice.infrastructure.out.client.dto.OwnerValidationResponse;
import com.juanda.powerup.plazoletaservice.infrastructure.out.client.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.UUID;


@Component
@RequiredArgsConstructor
public class OwnerValidationAdapter
        implements IOwnerValidationPort {


    private final UserClient userClient;



    @Override
    public OwnerValidation validateOwner(
            UUID ownerId
    ) {


        OwnerValidationResponse response;
        try {
            response = userClient.validateOwner(ownerId);
        } catch (FeignException exception) {
            throw new OwnerValidationUnavailableException("Could not validate owner", exception);
        }
        if (response == null || response.exists() == null || response.owner() == null
                || (!response.exists() && response.owner())) {
            throw new OwnerValidationUnavailableException("Invalid owner validation response", null);
        }



        return new OwnerValidation(
                response.exists(),
                response.owner()
        );
    }
}
