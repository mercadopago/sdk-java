package com.mercadopago.client.common;

import lombok.Data;

@Data
public class PhoneRequest {
    /**
     * Area code
     */
    private String areaCode;

    /**
     * Phone number
     */
    private String number;
}
