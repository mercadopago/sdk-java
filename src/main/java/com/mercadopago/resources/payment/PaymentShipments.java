package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds shipping information for a MercadoPago payment.
 *
 * <p>Contains the delivery address where the purchased items should be shipped.
 * This information is part of the additional info sent with the payment.
 *
 * @see PaymentAdditionalInfo#getShipments()
 */
@Getter
public class PaymentShipments {
  /** Full address of the shipment receiver, including state, city, floor, and apartment. */
  private PaymentReceiverAddress receiverAddress;
}
