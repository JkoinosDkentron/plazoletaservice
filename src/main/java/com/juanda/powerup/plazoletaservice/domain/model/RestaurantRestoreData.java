package com.juanda.powerup.plazoletaservice.domain.model;


import com.juanda.powerup.plazoletaservice.domain.model.valueobject.*;


public record RestaurantRestoreData(

        RestaurantId id,

        RestaurantName name,

        Nit nit,

        String address,

        RestaurantPhone phone,

        String urlLogo,

        OwnerId ownerId

) {
}