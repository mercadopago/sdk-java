package com.mercadopago.resources.customer;

import lombok.Data;

@Data
public class CustomerCardPaymentMethod {
    private String id;

    private String name;

    private String paymentTypeId;

    private String thumbnail;

    private String secureThumbnail;
}
