package com.mercadopago.resources;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing the response from the refund endpoint.
 *
 * <p>This class encapsulates the 201 response returned by the POST refund endpoint,
 * containing information about the refund status, details, and associated transactions.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">Mercado Pago API Reference</a>
 */
@Getter
public class OrderRefundResponse extends MPResource {

  /** Unique identifier of the refund. */
  private String id;

  /** Current status of the refund (e.g., "approved", "pending", "rejected"). */
  private String status;

  /** Additional details about the refund status. */
  private String statusDetail;

  /** Object containing transaction details associated with the refund. */
  private Object transactions;
}