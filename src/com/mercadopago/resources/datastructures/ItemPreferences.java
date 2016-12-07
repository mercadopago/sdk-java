package com.mercadopago.resources.datastructures;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Preferences Item class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class ItemPreferences extends Item {

    @Size(min=3, max=3) private String currency_id = null;

    public String getCurrencyId() {
        return currency_id;
    }

    public Item setCurrencyId(String currencyId) {
        this.currency_id = currencyId;
        return this;
    }

}
