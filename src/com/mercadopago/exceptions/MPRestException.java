package com.mercadopago.exceptions;

/**
 * Mercado Pago SDK
 * Mercado Pago MPRest Exception Class
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public class MPRestException extends MPException {

    public MPRestException(String message) {
        super(message);
    }

    public MPRestException(Throwable cause) {
        super(cause);
    }

}
