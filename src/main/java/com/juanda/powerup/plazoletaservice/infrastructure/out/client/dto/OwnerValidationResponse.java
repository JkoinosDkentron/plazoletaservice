package com.juanda.powerup.plazoletaservice.infrastructure.out.client.dto;


public record OwnerValidationResponse(

        Boolean exists,

        Boolean owner

) {
}
