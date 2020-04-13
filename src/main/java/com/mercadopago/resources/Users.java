package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;

public class Users extends MPBase {

    private String countryId;

    public String getCountryId() {
        return countryId;
    }

    public Users getCountry() throws MPException {
        return find(MPRequestOptions.createDefault());
    }

    @GET(path="/users/me")
    public Users find(MPRequestOptions requestOptions) throws MPException {
        if (requestOptions == null) {
            requestOptions = MPRequestOptions.createDefault();
        }
        addTrackingHeaders(requestOptions);
        return processMethod("find", WITHOUT_CACHE, requestOptions);
    }


}
