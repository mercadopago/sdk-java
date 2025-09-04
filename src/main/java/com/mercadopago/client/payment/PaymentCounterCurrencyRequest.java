package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentCounterCurrencyRequest {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;
}
