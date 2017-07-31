package com.mercadopago.resources.datastructures.preference;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Excluded Payment Methods class
 *
 * Created by Eduardo Paoletta on 12/14/16.
 */
public class ExcludedPaymentMethod {

    @Size(max=256) private String id = null;


    public ExcludedPaymentMethod setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return this.id;
    }

}
