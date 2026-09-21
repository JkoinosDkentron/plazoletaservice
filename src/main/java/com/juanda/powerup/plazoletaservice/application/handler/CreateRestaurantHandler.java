package com.juanda.powerup.plazoletaservice.application.handler;


import com.juanda.powerup.plazoletaservice.application.command.CreateRestaurantCommand;
import com.juanda.powerup.plazoletaservice.domain.api.IRestaurantServicePort;
import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;


public class CreateRestaurantHandler {


    private final IRestaurantServicePort restaurantServicePort;


    public CreateRestaurantHandler(
            IRestaurantServicePort restaurantServicePort
    ) {
        this.restaurantServicePort = restaurantServicePort;
    }



    public Restaurant execute(
            CreateRestaurantCommand command
    ) {

        return restaurantServicePort.createRestaurant(
                command.name(),
                command.nit(),
                command.address(),
                command.phone(),
                command.urlLogo(),
                command.ownerId()
        );
    }
}