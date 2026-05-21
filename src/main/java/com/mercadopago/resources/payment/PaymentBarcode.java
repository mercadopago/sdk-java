package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that represents barcode data for a ticket or boleto-based MercadoPago payment.
 *
 * <p>Contains the raw barcode content that can be used to generate a scannable barcode
 * image for offline payment methods.
 *
 * @see PaymentTransactionDetails#getBarcode()
 */
@Getter
public class PaymentBarcode {
    /** Raw barcode string content used to generate the scannable barcode. */
    private String content;
}
