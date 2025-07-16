package com.mercadopago.client.payment;

import com.mercadopago.client.common.SubMerchant;
import lombok.Builder;
import lombok.Getter;

/** To use the Payment Facilitator integration, it is necessary to update the forward_data.sub_merchant property for sending the fields described below */
@Getter
@Builder
public class PaymentForwardDataRequest  {

    /** ForwardData for SubMerchant */
    private final SubMerchant subMerchant;

    /** Network transaction data for recurring payments with Visa/Master */
    private final PaymentNetworkTransactionDataRequest networkTransactionData;
}
