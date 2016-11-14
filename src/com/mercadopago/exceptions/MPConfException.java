package com.mercadopago.exceptions;

/**
 * Mercado Pago SDK
 * Mercado Pago MPConf Exception Class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MPConfException extends MPException {

    public MPConfException(String message) {
        super(message);
    }

    public MPConfException(Throwable cause) {
        super(cause);
    }
}
