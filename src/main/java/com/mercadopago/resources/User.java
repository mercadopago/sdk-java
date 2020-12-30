package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;

/**
 * Access the user
 */
public class User extends MPBase {

    private String countryId;

    /**
     * @return country ID
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * Get current user information
     * @return the user
     * @throws MPException an error if the request fails
     */
    @GET(path="/users/me")
    public static User find() throws MPException {
        return processMethod(User.class,"find", WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

}
