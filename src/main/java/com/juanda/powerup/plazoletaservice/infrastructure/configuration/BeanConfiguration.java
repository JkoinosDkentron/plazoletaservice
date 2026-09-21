package com.juanda.powerup.plazoletaservice.infrastructure.configuration;


import com.juanda.powerup.plazoletaservice.application.handler.CreateRestaurantHandler;
import com.juanda.powerup.plazoletaservice.application.mapper.RestaurantMapper;
import com.juanda.powerup.plazoletaservice.domain.api.IRestaurantServicePort;
import com.juanda.powerup.plazoletaservice.domain.spi.IOwnerValidationPort;
import com.juanda.powerup.plazoletaservice.domain.spi.IRestaurantPersistencePort;
import com.juanda.powerup.plazoletaservice.domain.usecase.RestaurantUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {


    @Bean
    public IRestaurantServicePort restaurantServicePort(
            IRestaurantPersistencePort restaurantPersistencePort,
            IOwnerValidationPort ownerValidationPort
    ) {


        return new RestaurantUseCase(
                restaurantPersistencePort,
                ownerValidationPort
        );
    }



    @Bean
    public CreateRestaurantHandler createRestaurantHandler(
            IRestaurantServicePort restaurantServicePort
    ) {

        return new CreateRestaurantHandler(
                restaurantServicePort
        );
    }



    @Bean
    public RestaurantMapper restaurantMapper(){

        return new RestaurantMapper();
    }
}