package com.mercadopago.client.preference;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * Main request object to create or update a MercadoPago checkout preference. Aggregates all
 * checkout configuration including items, payer details, payment methods, shipping, tracking,
 * and expiration settings.
 *
 * @see com.mercadopago.client.preference.PreferenceClient
 * @see com.mercadopago.client.preference.PreferenceItemRequest
 * @see com.mercadopago.client.preference.PreferencePayerRequest
 */
@Getter
@Builder
public class PreferenceRequest {
  /** Free-text additional information attached to the preference. */
  private final String additionalInfo;

  /** Auto-return mode that redirects buyers back to your site after completing the purchase. */
  private final String autoReturn;

  /** Callback URLs for success, pending, and failure payment outcomes. */
  private final PreferenceBackUrlsRequest backUrls;

  /** When true, payments can only be approved or rejected (no in_process status). */
  private final Boolean binaryMode;

  /** Expiration date for cash-based payment methods. */
  private final OffsetDateTime dateOfExpiration;

  /** Differential pricing rule applied to this preference. */
  private final PreferenceDifferentialPricingRequest differentialPricing;

  /** Date from which the preference becomes active. */
  private final OffsetDateTime expirationDateFrom;

  /** Date when the preference expires and is no longer usable. */
  private final OffsetDateTime expirationDateTo;

  /** Whether this preference has an expiration date. */
  private final Boolean expires;

  /** External reference to synchronize with your payment system. */
  private final String externalReference;

  /** List of items included in the checkout. */
  private final List<PreferenceItemRequest> items;

  /** Origin of the payment. Defaults to NONE. */
  private final String marketplace;

  /** Fee charged by the marketplace application owner. */
  private final BigDecimal marketplaceFee;

  /** Custom key-value metadata attached to the preference for merchant use. */
  private final Map<String, Object> metadata;

  /** URL to receive payment status webhook notifications. */
  private final String notificationUrl;

  /** Type of operation for this preference. */
  private final String operationType;

  /** Buyer (payer) information for the checkout. */
  private final PreferencePayerRequest payer;

  /** Payment method configuration including exclusions and installment settings. */
  private final PreferencePaymentMethodsRequest paymentMethods;

  /** Processing modes to use (e.g., aggregator, gateway). */
  private final List<String> processingModes;

  /** Purpose of the preference (e.g., wallet_purchase). */
  private final String purpose;

  /** Shipping configuration for the checkout. */
  private final PreferenceShipmentsRequest shipments;

  /** Text that appears on the buyer's credit card statement. */
  private final String statementDescriptor;

  /** Tax items applied to this preference. */
  private final List<PreferenceTaxRequest> taxes;

  /** Tracking tags executed during the buyer's checkout interaction. */
  private final List<PreferenceTrackRequest> tracks;

  /** Amount breakdown with payer and collector details. */
  private final PreferenceAmountsRequest amounts;

  /** Counter-currency configuration for cross-currency transactions. */
  private final PreferenceCounterCurrencyRequest counterCurrency;
}
