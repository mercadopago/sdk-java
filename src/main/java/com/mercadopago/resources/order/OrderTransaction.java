package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

import java.util.List;

/** OrderTransaction class. */
@Getter
public class OrderTransaction extends MPResource{

    /** Payments information. */
    private List<OrderPayment> payments;

    /** Refunds information. */
    private List<OrderRefund> refunds;
}
