package com.juanda.powerup.plazoletaservice.domain.model;


import com.juanda.powerup.plazoletaservice.domain.model.valueobject.Nit;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.OwnerId;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantName;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantPhone;


public record RestaurantData(

        RestaurantName name,

        Nit nit,

        String address,

        RestaurantPhone phone,

        String urlLogo,

        OwnerId ownerId

) {
}