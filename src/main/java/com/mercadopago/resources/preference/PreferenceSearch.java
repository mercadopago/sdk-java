package com.mercadopago.resources.preference;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/** Preference resource. */
@Getter
public class PreferenceSearch extends MPResource {
  /** Preference ID. */
  private String id;

  /** Client ID. */
  private String clientId;

  /** Collector ID. */
  private Long collectorId;

  /** Created date of cash payment. */
  private OffsetDateTime dateCreated;

  /** Date when the preference will be active. */
  private OffsetDateTime expirationDateFrom;

  /** Date when the preference will be expired. */
  private OffsetDateTime expirationDateTo;

  /** True if a preference expires, false if not. */
  private Boolean expires;

  /** Reference you can synchronize with your payment system. */
  private String externalReference;

  /** List of items to be paid. */
  private List<String> items;

  /** Last updated. */
  private OffsetDateTime lastUpdated;

  /** Live mode. */
  private Boolean liveMode;

  /** Origin of the payment. Default value: NONE. */
  private String marketplace;

  /** Operation type. */
  private String operationType;

  /** Payer email. */
  private String payerEmail;

  /** Payer id. */
  private String payerId;

  /** Platform id. */
  private String platformId;

  /** Configures which processing modes to use. */
  private List<String> processingModes;

  /** Product id. */
  private String productId;

  /** Purpose. */
  private String purpose;

  /** Site id. */
  private String siteId;

  /** Sponsor id. */
  private Long sponsorId;

  /** Shipping mode. */
  private String shippingMode;
}
