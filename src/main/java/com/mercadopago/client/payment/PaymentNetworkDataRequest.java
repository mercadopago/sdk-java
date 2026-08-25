package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentNetworkDataRequest {
  private final String networkTransactionId;
  private final String transactionLinkId;
}
