package com.mercadopago.client.common;

import lombok.Data;

/** Phone data used in requests. */
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
