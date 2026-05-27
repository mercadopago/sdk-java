package com.mercadopago.resources.preapprovalplan;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing a MercadoPago subscription plan template.
 *
 * <p>A plan defines the billing frequency, currency, and amount shared by all subscriptions
 * attached to it. Create one plan and link multiple subscribers to it.
 *
 * @see com.mercadopago.client.preapprovalplan.PreApprovalPlanClient
 */
@Getter
public class PreApprovalPlan extends MPResource {

  /** Unique identifier of this subscription plan. */
  private String id;

  /** Short descriptive title of the plan. */
  private String reason;

  /** Integrator-provided external reference. */
  private String externalReference;

  /** Current status of the plan (active, cancelled). */
  private String status;

  /** URL to redirect the subscriber after completing the checkout flow. */
  private String backUrl;

  /** MercadoPago user identifier of the seller who receives the charges. */
  private Long collectorId;

  /** Identifier of the application that created this plan. */
  private String applicationId;

  /** Timestamp when the plan was created. */
  private OffsetDateTime dateCreated;

  /** Timestamp of the last modification. */
  private OffsetDateTime lastModified;

  /** Production checkout URL for subscribing to this plan. */
  private String initPoint;

  /** Sandbox checkout URL for testing. */
  private String sandboxInitPoint;

  /** Recurring billing configuration. */
  private PreApprovalPlanAutoRecurring autoRecurring;
}
