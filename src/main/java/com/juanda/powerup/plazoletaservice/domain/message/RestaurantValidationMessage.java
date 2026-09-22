package com.juanda.powerup.plazoletaservice.domain.message;

public final class RestaurantValidationMessage {
    public static final String RESTAURANT_DATA_REQUIRED = "Restaurant data is required";
    public static final String RESTAURANT_FIELDS_REQUIRED =
            "Restaurant identifiers, name, nit and phone are required";
    public static final String RESTAURANT_ADDRESS_REQUIRED = "Restaurant address is required";
    public static final String RESTAURANT_LOGO_REQUIRED = "Restaurant logo is required";

    private RestaurantValidationMessage() {
    }

    public static final String RESTAURANT_NAME_REQUIRED =
            "Restaurant name is required";

    public static final String RESTAURANT_NAME_ONLY_NUMBERS =
            "Restaurant name cannot contain only numbers";


    public static final String RESTAURANT_NIT_REQUIRED =
            "Restaurant nit is required";

    public static final String RESTAURANT_NIT_NUMERIC =
            "Restaurant nit must contain only numbers";


    public static final String RESTAURANT_PHONE_REQUIRED =
            "Restaurant phone is required";

    public static final String RESTAURANT_PHONE_FORMAT =
            "Restaurant phone format is invalid";

    public static final String RESTAURANT_PHONE_MAX_LENGTH =
            "Restaurant phone cannot exceed maximum length";


    public static final String RESTAURANT_ID_REQUIRED =
            "Restaurant id is required";


    public static final String OWNER_ID_REQUIRED =
            "Owner id is required";

    public static final String USER_IS_NOT_OWNER =
            "The user is not a valid owner";
}
