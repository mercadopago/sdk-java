package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/* API version: acd67b14-97c4-4a4a-840d-0a018c09654f */

/** Order class. */
@Getter
public class Order extends MPResource {

    /** Order ID. */
    private String id;

    /** Type of Order. */
    private String type;

    /** External reference. */
    private String externalReference;

    /** Total amount of the order. */
    private String totalAmount;

    /** Total Paid amount of the order. */
    private String totalPaidAmount;

    /**
     * Identifier of the site (country) to which the Mercado Pago application that
     * created the Order belongs.
     */
    private String countryCode;

    /**
     * Identifier of the user to which the Mercado Pago application that created the
     * Order belongs. It is the person that will receive the payment.
     */
    private String userId;

    /** Status of Order. */
    private String status;

    /** Status Detail of Order. */
    private String statusDetail;

    /** Order capture mode. */
    private String captureMode;

    /** Date of creation. */
    private String createdDate;

    /** Last modified date. */
    private String lastUpdatedDate;

    /**
     * Additional information that can be used to integrate with other systems, such
     * as the identifier of the Order in the integrator's system.
     */
    private OrderIntegrationData integrationData;

    /** Configures which processing modes to use. */
    private String processingMode;

    /** Description of Order. */
    private String description;

    /** Origin of the order. */
    private String marketplace;

    /** Fee collected by a marketplace or MercadoPago Application. */
    private String marketplaceFee;

    /** Transactions information. */
    private OrderTransaction transactions;

    /** Checkout available at */
    private String checkoutAvailableAt;

    /** Date of expiration. */
    private String expirationTime;

    /**
     * Unique token that identifies your integration. You can get it in Your
     * credentials.
     */
    private String clientToken;

    /** Items information. */
    private List<OrderItem> items;

    /** Order config. */
    private OrderConfig config;

    /** Payer information. */
    private OrderPayer payer;

    /** Additional info for the order. */
    private Map<String, Object> additionalInfo;
}
