package com.mercadopago.resources.merchantorder;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/**
 * Represents a merchant order resource in the MercadoPago API.
 *
 * <p>A merchant order groups one or more payments, items, and shipments into a single commercial
 * transaction. It tracks the overall status, amounts, and logistics of an order placed through
 * MercadoPago payment preferences.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders/post">
 *     Merchant Order API Reference</a>
 */
@Getter
public class MerchantOrder extends MPResource {
  /** Unique identifier of the merchant order. */
  private Long id;

  /** Payment preference identifier associated with this merchant order. */
  private String preferenceId;

  /** Application identifier that created the order. */
  private String applicationId;

  /** Current status of the merchant order (e.g., opened, closed). */
  private String status;

  /** Country site identifier where the merchant order was created (e.g., MLA, MLB). */
  private String siteId;

  /** Payer (buyer) information for this order. */
  private MerchantOrderPayer payer;

  /** Collector (seller) information for this order. */
  private MerchantOrderCollector collector;

  /** Identifier of the sponsor associated with the order. */
  private String sponsorId;

  /** List of payments associated with this merchant order. */
  private List<MerchantOrderPayment> payments;

  /** Total amount already paid for this order. */
  private BigDecimal paidAmount;

  /** Total amount refunded for this order. */
  private BigDecimal refundedAmount;

  /** Shipping cost of the order. */
  private BigDecimal shippingCost;

  /** Date and time when the order was created. */
  private OffsetDateTime dateCreated;

  /** Whether the order has been cancelled. */
  private boolean cancelled;

  /** List of items included in this order. */
  private List<MerchantOrderItem> items;

  /** List of shipments associated with this order. */
  private List<MerchantOrderShipment> shipments;

  /** URL to receive payment notification webhooks. */
  private String notificationUrl;

  /** Additional information about the order. */
  private String additionalInfo;

  /** External reference to synchronize with the merchant's own system. */
  private String externalReference;

  /** Marketplace origin of the payment. */
  private String marketplace;

  /** Total amount of the order including all items. */
  private BigDecimal totalAmount;

  /** Computed order status based on the aggregate status of its payments. */
  private String orderStatus;

  /** Date and time when the order was last updated. */
  private OffsetDateTime lastUpdated;
}
