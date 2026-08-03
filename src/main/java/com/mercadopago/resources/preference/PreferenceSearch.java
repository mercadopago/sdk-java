package com.mercadopago.resources.preference;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing a lightweight preference summary returned from search results.
 *
 * <p>Contains a subset of preference attributes optimized for listing and filtering, including
 * identifiers, dates, payer information, and operational metadata. Unlike {@link Preference}, this
 * resource does not include the full item details or nested configuration objects.
 *
 * @see Preference
 * @see com.mercadopago.client.preference.PreferenceClient#search(com.mercadopago.client.preference.PreferenceSearchRequest)
 */
@Getter
public class PreferenceSearch extends MPResource {
  /** Unique identifier of the preference. */
  private String id;

  /** Identifier of the OAuth application client that created this preference. */
  private String clientId;

  /** Identifier of the seller (collector) who receives the payment. */
  private Long collectorId;

  /** Timestamp when the preference was created. */
  private OffsetDateTime dateCreated;

  /** Start date from which the preference becomes active. */
  private OffsetDateTime expirationDateFrom;

  /** End date after which the preference is no longer valid. */
  private OffsetDateTime expirationDateTo;

  /** Whether this preference has an expiration window. */
  private Boolean expires;

  /** External reference for correlating this preference with the integrator's system. */
  private String externalReference;

  /** List of item identifiers included in this preference. */
  private List<String> items;

  /** Timestamp when the preference was last updated. */
  private OffsetDateTime lastUpdated;

  /** Whether the preference was created in production (live) mode. */
  private Boolean liveMode;

  /** Marketplace origin identifier. Default value: NONE. */
  private String marketplace;

  /** Type of operation (e.g., regular_payment, money_transfer). */
  private String operationType;

  /** Email address of the payer. */
  private String payerEmail;

  /** Identifier of the payer. */
  private String payerId;

  /** Identifier of the platform that originated this preference. */
  private String platformId;

  /** List of processing modes used (e.g., aggregator, gateway). */
  private List<String> processingModes;

  /** Identifier of the product associated with this preference. */
  private String productId;

  /** Purpose of the preference (e.g., wallet_purchase). */
  private String purpose;

  /** MercadoPago site identifier (e.g., MLA, MLB, MLM). */
  private String siteId;

  /** Identifier of the sponsoring account, if applicable. */
  private Long sponsorId;

  /** Shipping mode configured for this preference. */
  private String shippingMode;
}
