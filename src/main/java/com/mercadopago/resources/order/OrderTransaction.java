package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

import java.util.List;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

/** OrderTransaction class. */
@Getter
public class OrderTransaction extends MPResource{

    /** Payments information. */
    private List<OrderPayment> payments;

    /** Refunds information. */
    private List<OrderRefund> refunds;
}
