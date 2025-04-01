package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/** UpdateOrderTransaction class. */
@Getter
public class UpdateOrderTransaction extends MPResource {
    /** Payment method information. */
    private OrderPaymentMethod paymentMethod;
}
