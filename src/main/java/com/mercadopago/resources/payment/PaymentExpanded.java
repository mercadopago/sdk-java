package com.mercadopago.resources.payment;

import com.mercadopago.client.payment.PaymentNetworkTransactionDataRequest;
import lombok.Getter;

/**
 * Resource that holds expanded response data for a MercadoPago payment.
 *
 * <p>Returned only when the {@code X-Expand-Response-Nodes} header is used in the API request.
 * Contains gateway-level details such as network transaction references, which are useful
 * for reconciliation and advanced payment processing scenarios.
 *
 * @see Payment#getExpanded()
 */
@Getter
public class PaymentExpanded {
  /** Gateway-level expanded information including network transaction references. */
  private Gateway gateway;

  /**
   * Inner resource representing gateway-specific expanded data.
   *
   * <p>Contains the network transaction reference returned by the payment gateway.
   */
  @Getter
  public static class Gateway {
    /** Network transaction data reference from the payment gateway. */
    private PaymentNetworkTransactionDataRequest reference;
  }
} 