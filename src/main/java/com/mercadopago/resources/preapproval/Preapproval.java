package com.mercadopago.resources.preapproval;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing a MercadoPago subscription (preapproval).
 *
 * <p>A preapproval authorizes the seller to collect recurring payments from a payer according to
 * the billing configuration defined in {@link PreapprovalAutoRecurring}. The lifecycle of a
 * subscription includes creation, activation via the checkout link ({@code initPoint}), and
 * ongoing automated billing until cancellation or expiration.
 *
 * @see PreapprovalAutoRecurring
 * @see com.mercadopago.client.preapproval.PreapprovalClient
 */
@Getter
public class Preapproval extends MPResource {
  /** Unique identifier of the preapproval (subscription). */
  private String id;

  /** Identifier of the payer who authorized the recurring charges. */
  private Long payerId;

  /** Email address of the payer associated with this subscription. */
  private String payerEmail;

  /** URL the payer is redirected to after completing the subscription flow. */
  private String backUrl;

  /** Identifier of the seller (collector) who receives the recurring payments. */
  private Long collectorId;

  /** Identifier of the application that created this subscription. */
  private Long applicationId;

  /** Current status of the subscription (e.g., pending, authorized, paused, cancelled). */
  private String status;

  /** Title or reason describing the purpose of the subscription. */
  private String reason;

  /** External reference for correlating this subscription with the integrator's system. */
  private String externalReference;

  /** Scheduled date of the next automatic payment debit. */
  private OffsetDateTime nextPaymentDate;

  /** Timestamp when the subscription was created. */
  private OffsetDateTime dateCreated;

  /** Timestamp when the subscription was last modified. */
  private OffsetDateTime lastModified;

  /** Production checkout URL where the payer authorizes the subscription. */
  private String initPoint;

  /** Sandbox checkout URL for testing the subscription flow. */
  private String sandboxInitPoint;

  /** Identifier of the payment method used for recurring charges. */
  private String paymentMethodId;

  /** Recurring billing configuration including frequency, currency, and amount. */
  private PreapprovalAutoRecurring autoRecurring;

  /** Optimistic concurrency control version number. */
  private Long version;
}
