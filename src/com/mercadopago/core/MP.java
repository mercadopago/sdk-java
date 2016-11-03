package com.mercadopago.core;

import com.mercadopago.Conf;

/**
 * Mercado Pago SDK
 *
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MP {

    public Conf settings;

    public MP() {
        settings = new Conf();
    }

/*
    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Settings:\n%s\n", settings.toString()))
                .toString();
    }
*/
}
