package com.mercadopago.client.order;

import lombok.Getter;

/** Order split request. */
@Getter
public class OrderSplitRequest {

    /** Access token. */
    private String oauthToken;

    /** Split type. */
    private String type;

    /** Split value. */
    private String value;
}
