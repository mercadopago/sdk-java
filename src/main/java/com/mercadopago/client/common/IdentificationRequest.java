package com.mercadopago.client.common;

import lombok.Data;

@Data
public class IdentificationRequest {
    /**
     * Identification type
     */
    private String type;

    /**
     * Identification number
     */
    private String number;
}
