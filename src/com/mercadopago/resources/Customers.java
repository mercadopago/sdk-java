package com.mercadopago.resources;

import com.mercadopago.core.MPBaseArray;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Customer Array class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class Customers extends MPBaseArray {

    @Override
    protected Class getComponentType() {
        return Customer.class;
    }


    public ArrayList<Customer> getCustomers() {
        return (ArrayList<Customer>)super.resourceArray;
    }


    public MPBaseResponse search() throws MPException {
        return search(WITHOUT_CACHE);
    }

    @GET(path="/v1/customers/search")
    public MPBaseResponse search(Boolean useCache) throws MPException {
        return super.processMethod("search", useCache);
    }

}
