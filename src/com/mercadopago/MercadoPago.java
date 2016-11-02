package com.mercadopago;

import com.mercadopago.conf.Configuration;

/**
 * Mercado Pago SDK
 *
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MercadoPago {

    public Configuration settings;

    public MercadoPago() {
        settings = new Configuration();
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Settings:\n%s\n", settings.toString()))
                .toString();
    }

}
