package com.juanda.powerup.plazoletaservice.domain.api;

import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;

import java.util.UUID;

public interface IRestaurantServicePort {


    Restaurant createRestaurant(
            String name,
            String nit,
            String address,
            String phone,
            String urlLogo,
            UUID ownerId
    );

}