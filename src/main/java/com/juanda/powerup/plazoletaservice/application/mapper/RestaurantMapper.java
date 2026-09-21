package com.juanda.powerup.plazoletaservice.application.mapper;


import com.juanda.powerup.plazoletaservice.application.command.CreateRestaurantCommand;
import com.juanda.powerup.plazoletaservice.application.dto.request.CreateRestaurantRequest;
import com.juanda.powerup.plazoletaservice.application.dto.response.CreateRestaurantResponse;
import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;


public class RestaurantMapper {


    public CreateRestaurantCommand toCommand(
            CreateRestaurantRequest request
    ) {

        return new CreateRestaurantCommand(
                request.name(),
                request.nit(),
                request.address(),
                request.phone(),
                request.urlLogo(),
                request.ownerId()
        );
    }



    public CreateRestaurantResponse toResponse(
            Restaurant restaurant
    ) {

        return new CreateRestaurantResponse(
                restaurant.id().value(),
                restaurant.name().value(),
                restaurant.nit().value(),
                restaurant.address(),
                restaurant.phone().value(),
                restaurant.urlLogo(),
                restaurant.ownerId().value()
        );
    }
}