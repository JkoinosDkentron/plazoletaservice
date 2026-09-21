package com.juanda.powerup.plazoletaservice.domain.pattern;

public final class RestaurantValidationPattern {

    private RestaurantValidationPattern() {
    }


    public static final String ONLY_NUMBERS =
            "\\d+";


    public static final String PHONE =
            "\\+?\\d+";


    public static final String NIT =
            "\\d+";
}