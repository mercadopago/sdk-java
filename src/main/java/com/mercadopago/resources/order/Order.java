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

    /** Type of Order. */
    private String type;

    /** External reference. */
    private String externalReference;

    /** Country Code. */
    private String countryCode;

    /** Status of Order. */
    private String status;

    /** Status Detail of Order. */
    private String statusDetail;

    /** Capture Mode of Order. */
    private String captureMode;

    /** Transactions information. */
    private OrderTransaction transactions;

    /** Payer information. */
    private OrderPayer payer;

    /** Total amount of the order. */
    private String totalAmount;

    /** Configures which processing modes to use. */
    private String processingMode;

    /** Description of Order. */
    private String description;

    /** Origin of the order. */
    private String marketplace;

    /** Fee collected by a marketplace or MercadoPago Application. */
    private String marketplaceFee;

    /** Items information. */
    private List<OrderItem> items;

    /** Date of expiration. */
    private String expirationTime;

}
