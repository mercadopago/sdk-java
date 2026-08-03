package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Source;
import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource that represents a refund applied to a MercadoPago payment.
 *
 * <p>A refund can be total or partial, allowing the merchant to return funds to the payer.
 * Each refund is linked to the original payment and tracks its own status, amount, and
 * creation date.
 *
 * @see Payment
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">Refunds API reference</a>
 */
@Getter
public class PaymentRefund extends MPResource {
  /** Unique identifier of this refund. */
  private Long id;

  /** Identifier of the original payment that was refunded. */
  private Long paymentId;

  /** Amount that was refunded to the payer. */
  private BigDecimal amount;

  /** Adjustment amount applied to the refund (e.g. rounding differences). */
  private BigDecimal adjustmentAmount;

  /** Current status of the refund (e.g. approved, in_process). */
  private String status;

  /** Mode of the refund indicating how it was processed. */
  private String refundMode;

  /** Date and time when the refund was created. */
  private OffsetDateTime dateCreated;

  /** Reason provided by the merchant for issuing this refund. */
  private String reason;

  /** Unique sequence number that identifies this refund transaction. */
  private String uniqueSequenceNumber;

  /** Source that originated the refund (e.g. collector, admin). */
  private Source source;
}
