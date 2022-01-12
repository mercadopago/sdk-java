package com.mercadopago.exceptions;

import com.mercadopago.net.MPResponse;
import lombok.Getter;

@Getter
public class MPApiException extends MPException{
    private final int statusCode;
    private final MPResponse apiResponse;

    public MPApiException(String message, MPResponse response) {
        this(message, null, response);
    }

    public MPApiException(String message, Throwable cause, MPResponse response) {
        super(message, cause);
        this.apiResponse = response;
        this.statusCode = response.getStatusCode();
    }

    public MPApiException(Throwable cause, MPResponse response) {
        this(null, cause, response);
    }
}
