package com.mercadopago.resources.payment;

import com.mercadopago.client.payment.PaymentNetworkTransactionDataRequest;
import lombok.Getter;

/** PaymentExpanded class. */
@Getter
public class PaymentExpanded {
  /** Gateway information. */
  private Gateway gateway;

  /** Gateway class for expanded information. */
  @Getter
  public static class Gateway {
    /** Reference information. */
    private PaymentNetworkTransactionDataRequest reference;
  }
} 