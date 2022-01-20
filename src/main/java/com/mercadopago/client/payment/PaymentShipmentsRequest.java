package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentShipmentsRequest class. */
@Getter
@Builder
public class PaymentShipmentsRequest {
  /** Receiver Address. */
  private final PaymentReceiverAddressRequest receiverAddress;

  /** True if the product is picked up at the store, false if not. */
  private final boolean localPickup;

  /** True if the shipment is express, false if not. */
  private final boolean expressShipment;
}
