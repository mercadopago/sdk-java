package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;

public class User extends MPBase {

    private String countryId;

    public String getCountryId() {
        return countryId;
    }


    @GET(path="/users/me")
    public static User find() throws MPException {
        return processMethod(User.class,"find", WITHOUT_CACHE, MPRequestOptions.createDefault());
    }


}
