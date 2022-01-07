package com.mercadopago.exceptions;


public class MPMalformedRequestException extends MPException {
    public MPMalformedRequestException(String message) {
        super(message);
    }
    public MPMalformedRequestException(Throwable cause) {
        super(cause);
    }
}
