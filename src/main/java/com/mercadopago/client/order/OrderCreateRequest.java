package com.mercadopago.client.order;

import com.mercadopago.net.MPResource;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/** Order class. */
@Builder
@Getter
public class OrderCreateRequest extends MPResource {
    /** Order type. */
    private String type;

    /** Total amount of the order. */
    private String totalAmount;

    /** Reference you can synchronize with your payment system. */
    private String externalReference;

    /** Order transaction information. */
    private OrderTransactionRequest transactions;

    /** Currency information. */
    private String currency;

    /** Configures which processing modes to use. */
    private String processingMode;

    /** Capture mode. */
    private String captureMode;

    /** Description of the order. */
    private String description;

    /** Payer's information. */
    private OrderPayerRequest payer;

    /** Origin of the payment. */
    private String marketplace;

    /** Fee collected by a marketplace or MercadoPago Application. */
    private String marketplaceFee;

    /** Discount campaign ID. */
    private String campaignId;

    /** Items information. */
    private List<OrderItemRequest> items;

    /** Coupon information. */
    private OrderCouponRequest coupon;

    /** Information's about split payments. */
    private List<OrderSplitRequest> splits;

    /** Shipping information. */
    private OrderShipmentRequest shipment;

    /** Expiration time of the order. */
    private String expirationTime;
}
