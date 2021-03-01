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
    private String siteId;

    /**
     * @return country ID
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @return site ID
     */
    public String getSiteId() {
        return siteId;
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
