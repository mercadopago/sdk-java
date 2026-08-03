package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that describes the point of interaction where a MercadoPago payment was initiated.
 *
 * <p>Identifies how and where the payment originated, such as through a QR code, deep link,
 * IVR system, or checkout flow. Includes the application that created the payment and
 * the transaction data associated with the interaction channel.
 *
 * @see Payment#getPointOfInteraction()
 */
@Getter
public class PaymentPointOfInteraction {
  /** Type of the point of interaction (e.g. QR, DEEP_LINK, IVR). */
  private String type;

  /** Sub-type further specifying the interaction channel. */
  private String subType;

  /** Identifier indicating the entity or flow this interaction is linked to. */
  private String linkedTo;

  /** Information about the application that originated the payment. */
  private PaymentApplicationData applicationData;

  /** Transaction-level data from the interaction channel (e.g. QR code, ticket URL). */
  private PaymentTransactionData transactionData;
}
