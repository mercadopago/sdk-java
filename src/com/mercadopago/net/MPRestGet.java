package com.mercadopago.net;

import org.apache.http.client.methods.HttpGet;

/**
 * Mercado Pago SDK
 * Implementation of MPRestClient for GET requests
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public class MPRestGet extends MPRestClient{

    @Override
    protected HttpGet getRequestMethod(String url) {
        return new HttpGet(url);
    }

}
