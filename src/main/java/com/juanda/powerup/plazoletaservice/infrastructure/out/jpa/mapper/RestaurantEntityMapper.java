package com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.mapper;


import com.juanda.powerup.plazoletaservice.domain.factory.RestaurantFactory;
import com.juanda.powerup.plazoletaservice.domain.model.Restaurant;
import com.juanda.powerup.plazoletaservice.domain.model.RestaurantRestoreData;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.*;
import com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.stereotype.Component;


@Component
public class RestaurantEntityMapper {


    public RestaurantEntity toEntity(
            Restaurant restaurant
    ){

        return RestaurantEntity.builder()
                .id(
                        restaurant.id().value()
                )
                .name(
                        restaurant.name().value()
                )
                .nit(
                        restaurant.nit().value()
                )
                .address(
                        restaurant.address()
                )
                .phone(
                        restaurant.phone().value()
                )
                .urlLogo(
                        restaurant.urlLogo()
                )
                .ownerId(
                        restaurant.ownerId().value()
                )
                .build();
    }



    public Restaurant toDomain(
            RestaurantEntity entity
    ){

        return RestaurantFactory.restore(
                new RestaurantRestoreData(

                        new RestaurantId(
                                entity.getId()
                        ),

                        new RestaurantName(
                                entity.getName()
                        ),

                        new Nit(
                                entity.getNit()
                        ),

                        entity.getAddress(),

                        new RestaurantPhone(
                                entity.getPhone()
                        ),

                        entity.getUrlLogo(),

                        new OwnerId(
                                entity.getOwnerId()
                        )
                )
        );
    }
}