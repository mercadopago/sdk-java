package com.mercadopago.client.merchantorder;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to update an existing Merchant Order. Allows modifying order attributes such
 * as items, shipments, notification settings, and external references after the order has been
 * created.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderUpdateRequest {
  /** Mercado Libre site ID indicating the country of operation (e.g., "MLA", "MLB"). */
  private final String siteId;

  /** Updated payer information associated with this order. */
  private final MerchantOrderPayerRequest payer;

  /** Sponsor ID for marketplace or platform integrations. */
  private final String sponsorId;

  /** Updated list of items included in this merchant order. */
  private final List<MerchantOrderItemRequest> items;

  /** List of shipments associated with this merchant order. */
  private final List<MerchantOrderShipmentRequest> shipments;

  /** Webhook URL to receive payment and order status notifications. */
  private final String notificationUrl;

  /** Free-text field for additional information about the order. */
  private final String additionalInfo;

  /** External reference ID to correlate this order with the merchant's own system. */
  private final String externalReference;

  /** Marketplace identifier indicating the origin of the payment. */
  private final String marketplace;
}
