package com.mercadopago.resources.chargeback;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing a MercadoPago chargeback dispute.
 *
 * <p>A chargeback is initiated by a cardholder through their issuing bank when they dispute a
 * payment. This resource provides read-only access to the dispute details.
 *
 * @see com.mercadopago.client.chargeback.ChargebackClient
 */
@Getter
public class Chargeback extends MPResource {

  /** Unique identifier of the chargeback. */
  private String id;

  /** Identifier of the payment that originated the dispute. */
  private Long paymentId;

  /** Current status of the chargeback (e.g., new, in_review, won, lost). */
  private String status;

  /** Amount disputed by the cardholder. */
  private BigDecimal amount;

  /** ISO 4217 currency code of the disputed amount. */
  private String currencyId;

  /** Card-network reason code for the dispute. */
  private String reasonId;

  /** Human-readable description of the dispute reason. */
  private String reason;

  /** Timestamp when the chargeback was created. */
  private OffsetDateTime dateCreated;

  /** Timestamp of the last modification. */
  private OffsetDateTime lastModified;
}
