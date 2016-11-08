package com.mercadopago.exceptions;

import org.apache.commons.lang3.StringUtils;

/**
 * Mercado Pago SDK
 * Mercado Pago Base Exception Class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
abstract class MPException extends Exception {

    private String requestId;
    private Integer statusCode;

    MPException(String message, String requestId, Integer statusCode) {
        super(message, null);
        this.requestId = requestId;
        this.statusCode = statusCode;
    }

    MPException(String message, String requestId, Integer statusCode, Throwable cause) {
        super(message, cause);
        this.requestId = requestId;
        this.statusCode = statusCode;
    }

    MPException(Throwable cause) {
        super(cause);
    }

    public String getRequestId() {
        return requestId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        String reqIdStr = "";
        if (StringUtils.isNotEmpty(getRequestId()))
            reqIdStr = "; request-id: " + getRequestId();
        String statCodeStr = "";
        if (getStatusCode() != null)
            statCodeStr = "; status_code: " + getStatusCode();
        return super.toString() + reqIdStr + statCodeStr;
    }

}
