package com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.adapter;


import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;
import com.juanda.powerup.plazoletaservice.domain.spi.IRestaurantPersistencePort;
import com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.entity.RestaurantEntity;
import com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.mapper.RestaurantEntityMapper;
import com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.repository.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter
        implements IRestaurantPersistencePort {


    private final RestaurantJpaRepository restaurantJpaRepository;

    private final RestaurantEntityMapper restaurantEntityMapper;



    @Override
    public Restaurant save(
            Restaurant restaurant
    ) {


        RestaurantEntity entity =
                restaurantEntityMapper.toEntity(
                        restaurant
                );


        RestaurantEntity saved =
                restaurantJpaRepository.save(
                        entity
                );


        return restaurantEntityMapper.toDomain(
                saved
        );
    }
}