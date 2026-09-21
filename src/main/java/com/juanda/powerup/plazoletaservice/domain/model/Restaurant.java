package com.juanda.powerup.plazoletaservice.domain.model;


import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantId;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantName;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.Nit;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.RestaurantPhone;
import com.juanda.powerup.plazoletaservice.domain.model.valueobject.OwnerId;

import static com.juanda.powerup.plazoletaservice.domain.message.RestaurantValidationMessage.*;


public class Restaurant {

    private final RestaurantId id;
    private final RestaurantName name;
    private final Nit nit;
    private final String address;
    private final RestaurantPhone phone;
    private final String urlLogo;
    private final OwnerId ownerId;


    public Restaurant(
            RestaurantId id,
            RestaurantName name,
            Nit nit,
            String address,
            RestaurantPhone phone,
            String urlLogo,
            OwnerId ownerId
    ) {
        if (id == null || name == null || nit == null || phone == null || ownerId == null) {
            throw new InvalidRestaurantException(RESTAURANT_FIELDS_REQUIRED);
        }
        if (address == null || address.isBlank()) {
            throw new InvalidRestaurantException(RESTAURANT_ADDRESS_REQUIRED);
        }
        if (urlLogo == null || urlLogo.isBlank()) {
            throw new InvalidRestaurantException(RESTAURANT_LOGO_REQUIRED);
        }
        this.id = id;
        this.name = name;
        this.nit = nit;
        this.address = address;
        this.phone = phone;
        this.urlLogo = urlLogo;
        this.ownerId = ownerId;
    }


    public RestaurantId id() {
        return id;
    }

    public RestaurantName name() {
        return name;
    }

    public Nit nit() {
        return nit;
    }

    public String address() {
        return address;
    }

    public RestaurantPhone phone() {
        return phone;
    }

    public String urlLogo() {
        return urlLogo;
    }

    public OwnerId ownerId() {
        return ownerId;
    }
}
