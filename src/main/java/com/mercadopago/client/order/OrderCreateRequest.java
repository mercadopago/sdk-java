package com.mercadopago.client.order;

import com.mercadopago.net.MPResource;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** Order class. */
@Builder
@Getter
public class OrderCreateRequest extends MPResource {
    /** Order type. */
    private String type;

    /** Reference you can synchronize with your payment system. */
    private String externalReference;

    /** Order transaction information. */
    private OrderTransactionRequest transactions;

    /** Payer's information. */
    private OrderPayerRequest payer;

    /** Total amount of the order. */
    private String totalAmount;

    /** Capture mode. */
    private String captureMode;

    /** Configures which processing modes to use. */
    private String processingMode;

    /** Description of the order. */
    private String description;

    /** Origin of the payment. */
    private String marketplace;

    /** Fee collected by a marketplace or MercadoPago Application. */
    private String marketplaceFee;

    /** Items information. */
    private List<OrderItemRequest> items;

    /** Order config. */
    private OrderConfigRequest config;

    /** Checkout available at. */
    private String checkoutAvailableAt;

    /** Expiration time of the order. */
    private String expirationTime;
}
