package com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.repository;


import com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface RestaurantJpaRepository
        extends JpaRepository<RestaurantEntity, UUID> {

}