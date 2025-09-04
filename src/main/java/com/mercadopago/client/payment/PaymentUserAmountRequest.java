package com.mercadopago.client.payment;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentUserAmountRequest {
    /** Currency ID. ISO_4217 code. */
    private String currencyId;

    /** Transaction amount. */
    private BigDecimal transaction;
}
