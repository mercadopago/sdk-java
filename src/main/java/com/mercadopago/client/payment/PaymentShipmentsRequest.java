package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing shipment information for a payment.
 * Provides delivery details such as the receiver's address and shipping
 * mode, which are used for fraud analysis and logistics processing.
 */
@Getter
@Builder
public class PaymentShipmentsRequest {
  /** Destination address where the shipment will be delivered. */
  private final PaymentReceiverAddressRequest receiverAddress;

  /** Whether the product is picked up at the store instead of being shipped. */
  private final boolean localPickup;

  /** Whether the shipment uses express (expedited) delivery. */
  private final boolean expressShipment;
}
