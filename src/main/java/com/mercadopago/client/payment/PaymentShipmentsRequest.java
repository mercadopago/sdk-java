package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentShipmentsRequest class. */
@Getter
@Builder
public class PaymentShipmentsRequest {
  private final PaymentReceiverAddressRequest receiverAddress;

  private final boolean localPickup;

  private final boolean expressShipment;
}
