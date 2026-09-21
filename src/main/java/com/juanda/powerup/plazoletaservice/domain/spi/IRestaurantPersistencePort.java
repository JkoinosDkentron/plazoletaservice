package com.juanda.powerup.plazoletaservice.domain.spi;

import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;


public interface IRestaurantPersistencePort {


    Restaurant save(
            Restaurant restaurant
    );

}