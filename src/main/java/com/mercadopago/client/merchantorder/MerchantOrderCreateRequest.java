package com.mercadopago.client.merchantorder;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create a new Merchant Order. A merchant order groups one or more payments
 * for a set of items, enabling the merchant to track fulfillment, shipments, and payment
 * collection for a complete purchase operation.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderCreateRequest {
  /** Checkout preference ID that links this order to a payment preference. */
  private final String preferenceId;

  /** Application ID that originated this merchant order. */
  private final String applicationId;

  /** Mercado Libre site ID indicating the country of operation (e.g., "MLA", "MLB"). */
  private final String siteId;

  /** Payer information associated with this order. */
  private final MerchantOrderPayerRequest payer;

  /** Sponsor ID for marketplace or platform integrations. */
  private final String sponsorId;

  /** List of items included in this merchant order. */
  private final List<MerchantOrderItemRequest> items;

  /** Webhook URL to receive payment and order status notifications. */
  private final String notificationUrl;

  /** Free-text field for additional information about the order. */
  private final String additionalInfo;

  /** External reference ID to correlate this order with the merchant's own system. */
  private final String externalReference;

  /** Marketplace identifier indicating the origin of the payment. */
  private final String marketplace;
}
