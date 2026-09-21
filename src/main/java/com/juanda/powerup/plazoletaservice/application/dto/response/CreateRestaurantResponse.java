package com.juanda.powerup.plazoletaservice.application.dto.response;

import java.util.UUID;

public record CreateRestaurantResponse(

        UUID id,

        String name,

        String nit,

        String address,

        String phone,

        String urlLogo,

        UUID ownerId

) {
}