package com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "restaurants")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {


    @Id
    private UUID id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String nit;


    @Column(nullable = false)
    private String address;


    @Column(nullable = false)
    private String phone;


    @Column(nullable = false)
    private String urlLogo;


    @Column(nullable = false)
    private UUID ownerId;

}
