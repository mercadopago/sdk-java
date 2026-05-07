package com.mercadopago.client.order;

import com.mercadopago.net.MPResource;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/**
 * Request object for creating a new order in the MercadoPago Orders API. Contains all the
 * information needed to create an order, including payer details, transaction data, items,
 * shipping, and configuration options.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/online-payments/checkout-api/create-order/post">Orders API</a>
 */
@Builder
@Getter
public class OrderCreateRequest extends MPResource {
    /** Type of the order (e.g. "online", "point"). */
    private String type;

    /** External reference that can be synchronized with your own payment system. */
    private String externalReference;

    /** Transaction information associated with this order, including payments. */
    private OrderTransactionRequest transactions;

    /** Payer information for the order. */
    private OrderPayerRequest payer;

    /** Total amount of the order as a decimal string. */
    private String totalAmount;

    /** Capture mode for the payment (e.g. "automatic", "manual"). */
    private String captureMode;

    /** Processing mode that defines how the payment is processed (e.g. "aggregator", "gateway"). */
    private String processingMode;

    /** Short description of the order. */
    private String description;

    /** Origin of the payment, used in marketplace scenarios. */
    private String marketplace;

    /** Fee collected by a marketplace or MercadoPago application as a decimal string. */
    private String marketplaceFee;

    /** List of items included in the order. */
    private List<OrderItemRequest> items;

    /** Configuration options for the order (payment methods, online/point settings). */
    private OrderConfigRequest config;

    /** ISO 8601 date-time when the checkout becomes available. */
    private String checkoutAvailableAt;

    /** ISO 8601 duration or date-time indicating when the order expires. */
    private String expirationTime;

    /** Additional information about the order such as payer, shipment, platform, and travel data. */
    private AdditionalInfoRequest additionalInfo;

    /** Shipment details for the order, including receiver address and package dimensions. */
    private OrderShipmentRequest shipment;
}
