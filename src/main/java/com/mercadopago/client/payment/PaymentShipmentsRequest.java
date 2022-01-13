package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Data;

/** PaymentShipmentsRequest class. */
@Data
@Builder
public class PaymentShipmentsRequest {
  private PaymentReceiverAddressRequest receiverAddress;

  private boolean localPickup;

  private boolean expressShipment;
}
