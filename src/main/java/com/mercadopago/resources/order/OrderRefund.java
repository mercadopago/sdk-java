package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/**
 * OrderRefund class.
 */
@Getter
public class OrderRefund extends MPResource {

    /** Refund id. */
    private String id;

    /** Transaction id. */
    private String transactionId;

    /** Reference id. */
    private String referenceId;

    /** Amount Refund. */
    private String amount;

    /** Refund status. */
    private String status;

    /** Refund status detail. */
    private String statusDetail;
    
    /** Refund items. */
    private OrderItem items;
}
