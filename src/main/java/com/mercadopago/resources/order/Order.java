package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

import java.util.List;

/* API version: 54cea3ac-c258-4a6f-aea9-988e641cff30 */

/** Order class. */
@Getter
public class Order extends MPResource {

    /** Order ID. */
    private String id;

    /** Configures which processing modes to use. */
    private String processingMode;

    /** External reference. */
    private String externalReference;

    /** Description of Order. */
    private String description;

    /** Origin of the order. */
    private String marketplace;

    /** Fee collected by a marketplace or MercadoPago Application. */
    private String marketplaceFee;

    /** Campaign ID. */
    private String campaignId;

    /** Total amount of the order. */
    private String totalAmount;

    /** Currency information. */
    private String currency;

    /** Date of expiration. */
    private String expirationTime;

    /** Site ID. */
    private String siteId;

    /** Unique ID that identifies your integration. You can get it in Your credentials. */
    private String clientId;

    /** ID of the collector associated with the order. */
    private String collectorId;

    /** Date of creation. */
    private String createdDate;

    /** Last modified date. */
    private String lastUpdatedDate;

    /** Type of Order. */
    private String type;

    /** Status of Order. */
    private String status;

    /** Payer information. */
    private OrderPayer payer;

    /** Transactions information. */
    private OrderTransaction transactions;

    /** Shipping information. */
    private OrderShipment shipment;

    /** Items information. */
    private List<OrderItem> items;
}
