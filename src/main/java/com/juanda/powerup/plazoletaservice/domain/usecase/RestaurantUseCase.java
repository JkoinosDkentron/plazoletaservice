package com.juanda.powerup.plazoletaservice.domain.usecase;


import com.juanda.powerup.plazoletaservice.domain.api.IRestaurantServicePort;
import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import com.juanda.powerup.plazoletaservice.domain.factory.RestaurantFactory;
import com.juanda.powerup.plazoletaservice.domain.model.OwnerValidation;
import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;
import com.juanda.powerup.plazoletaservice.domain.model.RestaurantData;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.*;
import com.juanda.powerup.plazoletaservice.domain.spi.IOwnerValidationPort;
import com.juanda.powerup.plazoletaservice.domain.spi.IRestaurantPersistencePort;


import java.util.UUID;

import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.USER_IS_NOT_OWNER;


public class RestaurantUseCase implements IRestaurantServicePort {


    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IOwnerValidationPort ownerValidationPort;



    public RestaurantUseCase(
            IRestaurantPersistencePort restaurantPersistencePort,
            IOwnerValidationPort ownerValidationPort
    ) {

        this.restaurantPersistencePort = restaurantPersistencePort;
        this.ownerValidationPort = ownerValidationPort;
    }



    @Override
    public Restaurant createRestaurant(
            String name,
            String nit,
            String address,
            String phone,
            String urlLogo,
            UUID ownerId
    ) {




        Restaurant restaurant =
                RestaurantFactory.create(
                        new RestaurantData(

                                new RestaurantName(name),

                                new Nit(nit),

                                address,

                                new RestaurantPhone(phone),

                                urlLogo,

                                new OwnerId(ownerId)
                        )
                );


        validateOwner(ownerId);
        return restaurantPersistencePort.save(
                restaurant
        );
    }


    private void validateOwner(
            UUID ownerId
    ) {


        OwnerValidation validation =
                ownerValidationPort.validateOwner(ownerId);



        if (!validation.valid()) {

            throw new InvalidRestaurantException(
                    USER_IS_NOT_OWNER
            );
        }
    }
}
