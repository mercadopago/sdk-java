package com.mercadopago.resources;

import com.mercadopago.core.MPBaseArray;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Customer Card Array class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class Cards extends MPBaseArray {

    @Override
    protected Class getComponentType() {
        return Card.class;
    }


    public ArrayList<Card> getCards() {
        return (ArrayList<Card>)super.resourceArray;
    }


    public MPBaseResponse loadAll(String customerId) throws MPException {
        return loadAll(customerId, WITHOUT_CACHE);
    }

    @GET(path="/v1/customers/:customer_id/cards")
    public MPBaseResponse loadAll(String customerId, Boolean useCache) throws MPException {
        return super.processMethod("loadAll", customerId, useCache);
    }

}
