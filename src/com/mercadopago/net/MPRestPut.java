package com.mercadopago.net;

import com.mercadopago.exceptions.MPRestException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPut;

/**
 * Mercado Pago SDK
 * Implementation of MPRestClient for PUT requests
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public class MPRestPut extends MPRestClient {

    @Override
    protected HttpPut getRequestMethod(String url, HttpEntity payload) throws MPRestException {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(payload);
        return httpPut;
    }

}
