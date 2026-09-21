package com.juanda.powerup.plazoletaservice.domain.model;


public record OwnerValidation(

        boolean exists,

        boolean owner

) {


    public boolean valid(){

        return exists && owner;
    }
}