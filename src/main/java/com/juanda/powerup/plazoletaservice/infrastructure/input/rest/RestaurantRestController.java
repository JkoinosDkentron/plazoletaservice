package com.juanda.powerup.plazoletaservice.infrastructure.input.rest;


import com.juanda.powerup.plazoletaservice.application.command.CreateRestaurantCommand;
import com.juanda.powerup.plazoletaservice.application.dto.request.CreateRestaurantRequest;
import com.juanda.powerup.plazoletaservice.application.dto.response.CreateRestaurantResponse;
import com.juanda.powerup.plazoletaservice.application.handler.CreateRestaurantHandler;
import com.juanda.powerup.plazoletaservice.application.mapper.RestaurantMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.juanda.powerup.plazoletaservice.infrastructure.exceptionhandler.ErrorResponse;


@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurants", description = "Restaurant creation")
public class RestaurantRestController {


    private final CreateRestaurantHandler handler;

    private final RestaurantMapper mapper;



    public RestaurantRestController(
            CreateRestaurantHandler handler,
            RestaurantMapper mapper
    ){

        this.handler = handler;
        this.mapper = mapper;
    }



    @PostMapping
    @Operation(summary = "Create restaurant",
            description = "Creates a restaurant for an existing OWNER. Authentication is introduced in HU5.")
    @ApiResponse(responseCode = "201", description = "Restaurant created",
            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid data or invalid owner",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "503", description = "Owner validation unavailable",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Unexpected server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<CreateRestaurantResponse> create(
            @RequestBody CreateRestaurantRequest request
    ){


        CreateRestaurantCommand command =
                mapper.toCommand(request);



        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        mapper.toResponse(
                                handler.execute(command)
                        )
                );
    }
}
