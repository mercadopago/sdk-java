package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentNetworkTransactionDataRequest class. */
@Getter
@Builder
public class PaymentNetworkTransactionDataRequest {
  /** Network transaction ID associated with the card brand identifier. */
  private final String networkTransactionId;
} 