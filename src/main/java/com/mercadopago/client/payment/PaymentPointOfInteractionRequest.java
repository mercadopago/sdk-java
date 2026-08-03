package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object describing the point of interaction where the payment was initiated.
 * Provides context about the channel, device, or integration from which the
 * payment originates (e.g. QR code, smart link, subscription).
 */
@Getter
@Builder
public class PaymentPointOfInteractionRequest {
  /** Identifier of the resource this payment is linked to. */
  private final String linkedTo;

  /** Point-of-interaction type (e.g. "PSP_TRANSFER", "CHECKOUT"). */
  private final String type;

  /** Point-of-interaction sub-type for more granular classification. */
  private final String subType;

  /** Transaction data associated with the point of interaction. */
  private final PaymentTransactionDataRequest transactionData;

}
