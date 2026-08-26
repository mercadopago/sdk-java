package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Network references returned by an expanded payment gateway response.
 */
@Getter
public class PaymentGatewayReference {
  /** Card-network transaction identifier. */
  private String networkTransactionId;

  /** Card-network transaction and link identifiers. */
  private PaymentNetworkData networkData;
}
