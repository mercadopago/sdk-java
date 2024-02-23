package com.mercadopago.resources.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentBarcode class. */
@Getter
@Builder
public class PaymentBarcode {
    /** Barcode content. */
    private String content;
}
