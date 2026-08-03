package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/* API version: acd67b14-97c4-4a4a-840d-0a018c09654f */

/**
 * Resource representing a MercadoPago Order.
 * An Order is the top-level entity that groups one or more payment transactions, line items,
 * payer information, and configuration options for online or point-of-sale checkout flows.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/online-payments/checkout-api/create-order/post">Order API reference</a>
 */
@Getter
public class Order extends MPResource {

    /** Unique identifier of the order. */
    private String id;

    /** Type of the order (e.g., "online", "point"). */
    private String type;

    /** External reference used to correlate this order with an identifier in the integrator's system. */
    private String externalReference;

    /** Total amount of the order expressed as a decimal string. */
    private String totalAmount;

    /** Total amount already paid on this order expressed as a decimal string. */
    private String totalPaidAmount;

    /** Country code of the site to which the MercadoPago application that created the order belongs. */
    private String countryCode;

    /** Identifier of the user (seller) who will receive the payment for this order. */
    private String userId;

    /** Current status of the order (e.g., "created", "processed", "expired"). */
    private String status;

    /** Detailed sub-status providing additional context about the current order status. */
    private String statusDetail;

    /** Capture mode for the order's payments (e.g., "automatic", "manual"). */
    private String captureMode;

    /** ISO 8601 date-time when the order was created. */
    private String createdDate;

    /** ISO 8601 date-time when the order was last updated. */
    private String lastUpdatedDate;

    /** Integration data containing identifiers for correlating the order with external systems. */
    private OrderIntegrationData integrationData;

    /** Processing mode that controls how payments are executed (e.g., "aggregator", "gateway"). */
    private String processingMode;

    /** Free-text description of the order visible to the payer. */
    private String description;

    /** Marketplace identifier indicating the origin of the order. */
    private String marketplace;

    /** Fee collected by the marketplace or MercadoPago application, expressed as a decimal string. */
    private String marketplaceFee;

    /** Transaction details including payments, refunds, and chargebacks associated with this order. */
    private OrderTransaction transactions;

    /** ISO 8601 date-time from which the checkout becomes available for the payer. */
    private String checkoutAvailableAt;

    /** ISO 8601 date-time when the order expires if not completed. */
    private String expirationTime;

    /** Unique client token that identifies the integrator's credentials for this order. */
    private String clientToken;

    /** URL to redirect the buyer to the checkout flow. Generated at creation. */
    private String checkoutUrl;

    /** List of line items included in this order. */
    private List<OrderItem> items;

    /** Configuration settings for payment methods, online checkout, and point-of-sale behavior. */
    private OrderConfig config;

    /** Information about the payer associated with this order. */
    private OrderPayer payer;

    /** Additional key-value metadata attached to the order for integration purposes. */
    private Map<String, Object> additionalInfo;

    /** ISO 4217 currency code for the order (e.g., "BRL", "ARS", "MXN"). */
    private String currency;

    /** List of taxes applied to the order. */
    private List<OrderTax> taxes;

    /** Discount information applied to the order by payment method. */
    private OrderDiscounts discounts;

    /** Type-specific response data such as QR codes for in-store payments. */
    private OrderTypeResponse typeResponse;
}
