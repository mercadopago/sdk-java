package com.mercadopago.net;

import com.mercadopago.exceptions.MPRestException;
import org.apache.http.client.methods.HttpDelete;

/**
 * Mercado Pago SDK
 * Implementation of MPRestClient for DELETE requests
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public class MPRestDelete extends MPRestClient {

    @Override
    protected HttpDelete getRequestMethod(String url) throws MPRestException {
        return new HttpDelete(url);
    }

}
