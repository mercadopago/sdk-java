package com.mercadopago.resources.preference;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * Resource representing a MercadoPago checkout preference.
 *
 * <p>A preference defines the payment experience for the buyer, including which items are being
 * purchased, accepted payment methods, shipping options, redirect URLs, and expiration rules.
 * After creation, the {@code initPoint} URL is used to redirect buyers to the MercadoPago
 * Checkout flow.
 *
 * @see PreferenceItem
 * @see PreferencePayer
 * @see PreferencePaymentMethods
 * @see PreferenceBackUrls
 * @see PreferenceShipments
 * @see com.mercadopago.client.preference.PreferenceClient
 */
@Getter
public class Preference extends MPResource {
  /** Unique identifier of the preference. */
  private String id;

  /** Collection of items included in this checkout preference. */
  private List<PreferenceItem> items;

  /** Information about the buyer who will pay for the items. */
  private PreferencePayer payer;

  /** Identifier of the OAuth application client that created this preference. */
  private String clientId;

  /** Configuration of accepted and excluded payment methods for this preference. */
  private PreferencePaymentMethods paymentMethods;

  /** Redirect URLs for success, pending, and failure payment outcomes. */
  private PreferenceBackUrls backUrls;

  /** Shipping configuration including mode, cost, and delivery address. */
  private PreferenceShipments shipments;

  /** Webhook URL where payment notifications (IPN) will be sent. */
  private String notificationUrl;

  /** Descriptor that appears on the buyer's card or bank statement. */
  private String statementDescriptor;

  /** External reference for correlating this preference with the integrator's system. */
  private String externalReference;

  /** Whether this preference has an expiration window. */
  private Boolean expires;

  /** Expiration date for cash-based payment methods. */
  private OffsetDateTime dateOfExpiration;

  /** Start date from which the preference becomes active. */
  private OffsetDateTime expirationDateFrom;

  /** End date after which the preference is no longer valid. */
  private OffsetDateTime expirationDateTo;

  /** Identifier of the seller (collector) who receives the payment. */
  private Long collectorId;

  /** Marketplace origin identifier. Default value: NONE. */
  private String marketplace;

  /** Fee amount charged by the marketplace application owner. */
  private BigDecimal marketplaceFee;

  /** Free-form additional information attached to the preference. */
  private String additionalInfo;

  /**
   * Auto-return mode. When specified, buyers are redirected back to the seller's site immediately
   * after completing the purchase (e.g., {@code approved}, {@code all}).
   */
  private String autoReturn;

  /** Type of operation (e.g., regular_payment, money_transfer). */
  private String operationType;

  /** Differential pricing configuration that applies special pricing rules. */
  private PreferenceDifferentialPricing differentialPricing;

  /** List of processing modes to use (e.g., aggregator, gateway). */
  private List<String> processingModes;

  /**
   * When {@code true}, payments can only be approved or rejected immediately; the
   * {@code in_process} intermediate status is not used.
   */
  private Boolean binaryMode;

  /** List of applicable taxes for this preference. */
  private List<PreferenceTax> taxes;

  /** Tracking tags executed during the buyer's interaction in the Checkout flow. */
  private List<PreferenceTrack> tracks;

  /** Arbitrary key-value metadata attached to the preference for merchant use. */
  private Map<String, Object> metadata;

  /** Production checkout URL that initiates the payment flow for this preference. */
  private String initPoint;

  /** Sandbox checkout URL for testing the payment flow. */
  private String sandboxInitPoint;

  /** Timestamp when the preference was created. */
  private OffsetDateTime dateCreated;

  /** Breakdown of amounts for the payer and collector. */
  private PreferenceAmounts amounts;

  /** Counter currency configuration for cross-currency payments. */
  private PreferenceCounterCurrency counterCurrency;
}
