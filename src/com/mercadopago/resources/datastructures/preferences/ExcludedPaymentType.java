package com.mercadopago.resources.datastructures.preferences;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Excluded Payment Types class
 *
 * Created by Eduardo Paoletta on 12/14/16.
 */
public class ExcludedPaymentType {

    @Size(max=256) private String id = null;


    public ExcludedPaymentType setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return this.id;
    }

}
