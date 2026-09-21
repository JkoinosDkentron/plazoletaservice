package com.juanda.powerup.plazoletaservice.domain.spi;


import com.juanda.powerup.plazoletaservice.domain.model.OwnerValidation;

import java.util.UUID;


public interface IOwnerValidationPort {


    OwnerValidation validateOwner(
            UUID ownerId
    );

}