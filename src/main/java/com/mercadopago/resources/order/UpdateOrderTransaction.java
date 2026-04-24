package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing the response returned when updating a transaction
 * on an existing MercadoPago Order. Contains the updated payment method details.
 */
@Getter
public class UpdateOrderTransaction extends MPResource {
    /** Updated payment method details for the order transaction. */
    private OrderPaymentMethod paymentMethod;
}
