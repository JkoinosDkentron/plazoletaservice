package com.juanda.powerup.plazoletaservice.infrastructure.out.client.feign;


import com.juanda.powerup.plazoletaservice.infrastructure.out.client.dto.OwnerValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.UUID;


@FeignClient(
        name = "users-service",
        url = "${users-service.url}"
)
public interface UserClient {


    @GetMapping("/api/v1/users/{id}/owner-validation")
    OwnerValidationResponse validateOwner(
            @PathVariable("id") UUID id
    );

}
