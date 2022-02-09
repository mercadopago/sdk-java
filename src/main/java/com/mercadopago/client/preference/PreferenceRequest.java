package com.mercadopago.client.preference;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** Parameters to create/update a preference. */
@Getter
@Builder
public class PreferenceRequest {
  /** Additional info. */
  private final String additionalInfo;

  /**
   * If specified, your buyers will be redirected back to your site immediately after completing the
   * purchase.
   */
  private final String autoReturn;

  /** URLs to return to the sellers website. */
  private final PreferenceBackUrlsRequest backUrls;

  /**
   * When set to true, the payment can only be approved or rejected. Otherwise in_process status is
   * added.
   */
  private final Boolean binaryMode;

  /** Expiration date of cash payment. */
  private final OffsetDateTime dateOfExpiration;

  /** Differential pricing configuration for this preference. */
  private final PreferenceDifferentialPricingRequest differentialPricing;

  /** Date since the preference will be active. */
  private final OffsetDateTime expirationDateFrom;

  /** Date when the preference will be expired. */
  private final OffsetDateTime expirationDateTo;

  /** True if a preference expires, false if not. */
  private final Boolean expires;

  /** Reference you can synchronize with your payment system. */
  private final String externalReference;

  /** List of items to be paid. */
  private final List<PreferenceItemRequest> items;

  /** Origin of the payment. Default value: NONE. */
  private final String marketplace;

  /** Marketplace's fee charged by application owner. */
  private final BigDecimal marketplaceFee;

  /**
   * Data that can be attached to the preference to record additional attributes of the merchant.
   */
  private final Map<String, Object> metadata;

  /** URL where you'd like to receive a payment notification. */
  private final String notificationUrl;

  /** Operation type. */
  private final String operationType;

  /** Payer information. */
  private final PreferencePayerRequest payer;

  /** Set up payment methods. */
  private final PreferencePaymentMethodsRequest paymentMethods;

  /** Configures which processing modes to use. */
  private final List<String> processingModes;

  /** Purpose of the Preference. */
  private final String purpose;

  /** Shipments information. */
  private final PreferenceShipmentsRequest shipments;

  /** How will look the payment in the card bill. */
  private final String statementDescriptor;

  /** Taxes for preferences. */
  private final List<PreferenceTaxRequest> taxes;

  /** Tracks to be executed during the users interaction in the Checkout flow. */
  private final List<PreferenceTrackRequest> tracks;
}
