package com.mercadopago.resources.preference;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/** Preference resource. */
@Getter
public class Preference extends MPResource {
  /** Preference ID. */
  private String id;

  /** List of items to be paid. */
  private List<PreferenceItem> items;

  /** Payer information. */
  private PreferencePayer payer;

  /** Client ID. */
  private String clientId;

  /** Set up payment methods. */
  private PreferencePaymentMethods paymentMethods;

  /** URLs to return to the sellers website. */
  private PreferenceBackUrls backUrls;

  /** Shipments information. */
  private PreferenceShipments shipments;

  /** URL where you'd like to receive a payment notification. */
  private String notificationUrl;

  /** How the payment will be specified in the card bill. */
  private String statementDescriptor;

  /** Reference you can synchronize with your payment system. */
  private String externalReference;

  /** True if a preference expires, false if not. */
  private Boolean expires;

  /** Expiration date of cash payment. */
  private OffsetDateTime dateOfExpiration;

  /** Date when the preference will be active. */
  private OffsetDateTime expirationDateFrom;

  /** Date when the preference will be expired. */
  private OffsetDateTime expirationDateTo;

  /** Collector ID. */
  private Long collectorId;

  /** Origin of the payment. Default value: NONE. */
  private String marketplace;

  /** Marketplace's fee charged by application owner. */
  private BigDecimal marketplaceFee;

  /** Additional info. */
  private String additionalInfo;

  /**
   * If specified, your buyers will be redirected back to your site immediately after completing the
   * purchase.
   */
  private String autoReturn;

  /** Operation type. */
  private String operationType;

  /** Differential pricing configuration for this preference. */
  private PreferenceDifferentialPricing differentialPricing;

  /** Configures which processing modes to use. */
  private List<String> processingModes;

  /**
   * When set to true, the payment can only be approved or rejected. Otherwise in_process status is
   * added.
   */
  private Boolean binaryMode;

  /** Taxes for preferences. */
  private List<PreferenceTax> taxes;

  /** Tracks to be executed during the users interaction in the Checkout flow. */
  private List<PreferenceTrack> tracks;

  /**
   * Data that can be attached to the preference to record additional attributes of the merchant.
   */
  private Map<String, Object> metadata;

  /** Checkout URL from preference. */
  private String initPoint;

  /** Sandbox checkout URL from preference. */
  private String sandboxInitPoint;

  /** Date of creation. */
  private OffsetDateTime dateCreated;
}
