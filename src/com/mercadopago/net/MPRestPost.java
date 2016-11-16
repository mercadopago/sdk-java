package com.mercadopago.net;

import com.mercadopago.exceptions.MPRestException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

/**
 * Mercado Pago SDK
 * Implementation of MPRestClient for POST requests
 *
 * Created by Eduardo Paoletta  on 11/11/16.
 */
public class MPRestPost extends MPRestClient {

    @Override
    protected HttpPost getRequestMethod(String url, HttpEntity payload) throws MPRestException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(payload);
        return httpPost;
    }

}
