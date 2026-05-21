package com.mercadopago.client.payment;

import com.mercadopago.client.common.SubMerchant;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object carrying forward data for Payment Facilitator integrations.
 * Contains sub-merchant details required by card networks and network transaction
 * data used for recurring payments with Visa and Mastercard.
 */
@Getter
@Builder
public class PaymentForwardDataRequest {

    /** Sub-merchant information required for Payment Facilitator integrations. */
    private final SubMerchant subMerchant;

    /** Network transaction data used for recurring payments with Visa and Mastercard. */
    private final PaymentNetworkTransactionDataRequest networkTransactionData;
}
