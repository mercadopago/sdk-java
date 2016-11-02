package com.mercadopago.exception;

/**
 * Mercado Pago SDK
 * Mercado Pago Configuration Exception Class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MercadoPagoConfigurationException extends MercadoPagoException {

    public MercadoPagoConfigurationException(String message) {
        super(message, null, null);
    }

    public MercadoPagoConfigurationException(Throwable cause) {
        super(cause);
    }
}
