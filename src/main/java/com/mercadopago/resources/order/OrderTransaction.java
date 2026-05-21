package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

import java.util.List;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/**
 * Resource representing the transaction details of a MercadoPago Order.
 * Groups together all financial operations associated with an order, including
 * payments, refunds, and chargebacks.
 */
@Getter
public class OrderTransaction extends MPResource{

    /** List of payments associated with this order transaction. */
    private List<OrderPayment> payments;

    /** List of refunds issued against payments in this order transaction. */
    private List<OrderRefund> refunds;

    /** List of chargebacks raised against payments in this order transaction. */
    private List<OrderChargeback> chargebacks;
}
