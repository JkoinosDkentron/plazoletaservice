package com.juanda.powerup.plazoletaservice.application.dto.request;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateRestaurantRequest(

        @Schema(description = "Required name; cannot contain only numbers", example = "Pizza 123",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "Required numeric NIT", example = "900123456",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String nit,

        @Schema(example = "Calle 10 #20-30", requiredMode = Schema.RequiredMode.REQUIRED)
        String address,

        @Schema(description = "Digits with optional leading +; maximum 13 characters", example = "+573001234567",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String phone,

        @Schema(example = "https://example.com/logo.png", requiredMode = Schema.RequiredMode.REQUIRED)
        String urlLogo,

        @Schema(description = "UUID of an existing user with OWNER role",
                requiredMode = Schema.RequiredMode.REQUIRED)
        UUID ownerId

) {
}
