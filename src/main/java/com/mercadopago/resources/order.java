package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Represents a MercadoPago Order resource.
 *
 * <p>Orders group related payment transactions and provide a unified view of transaction
 * details, status, and associated metadata.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">
 *     Orders API reference</a>
 */
@Getter
public class Order extends MPResource {

  /** Unique identifier of the order. */
  private String id;

  /** Current status of the order (e.g., pending, processed, cancelled). */
  private String status;

  /** Human-readable description of the order. */
  private String description;

  /** External reference provided by the integrator. */
  private String externalReference;

  /** Total amount of the order. */
  private java.math.BigDecimal totalAmount;

  /** ISO 4217 currency code (e.g., BRL, ARS, USD). */
  private String currencyId;

  /** Timestamp when the order was created (ISO 8601 format). */
  private java.time.OffsetDateTime dateCreated;

  /** Timestamp of the last order update (ISO 8601 format). */
  private java.time.OffsetDateTime dateLastUpdated;
}