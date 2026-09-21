package com.juanda.powerup.plazoletaservice.application.command;

import java.util.UUID;

public record CreateRestaurantCommand(

        String name,

        String nit,

        String address,

        String phone,

        String urlLogo,

        UUID ownerId

) {
}