package com.mercadopago.resources.merchantorder;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/** Merchant Order resource. */
@Getter
public class MerchantOrder extends MPResource {
  /** Order ID. */
  private Long id;

  /** Payment preference identifier associated to the merchant order. */
  private String preferenceId;

  /** Application ID. */
  private String applicationId;

  /** Show the current merchant order state. */
  private String status;

  /** Country identifier that merchant order belongs to. */
  private String siteId;

  /** Payer information. */
  private MerchantOrderPayer payer;

  /** Seller information. */
  private MerchantOrderCollector collector;

  /** Sponsor ID. */
  private String sponsorId;

  /** Payments information. */
  private List<MerchantOrderPayment> payments;

  /** Amount paid in this order. */
  private BigDecimal paidAmount;

  /** Amount refunded in this Order. */
  private BigDecimal refundedAmount;

  /** Shipping fee. */
  private BigDecimal shippingCost;

  /** Date of creation. */
  private OffsetDateTime dateCreated;

  /** If the Order is expired (true) or not (false). */
  private boolean cancelled;

  /** Items information. */
  private List<MerchantOrderItem> items;

  /** Shipments information. */
  private List<MerchantOrderShipment> shipments;

  /** URL where you'd like to receive a payment notification. */
  private String notificationUrl;

  /** Additional information. */
  private String additionalInfo;

  /** Reference you can synchronize with your payment system. */
  private String externalReference;

  /** Origin of the payment. */
  private String marketplace;

  /** Total amount of the order. */
  private BigDecimal totalAmount;

  /** Current merchant order status given the payments status. */
  private String orderStatus;

  /** Last modified date. */
  private OffsetDateTime lastUpdated;
}
