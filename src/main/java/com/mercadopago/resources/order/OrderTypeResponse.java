package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing type-specific response data for a MercadoPago Order.
 * Contains output generated based on the order type, such as QR code data
 * for in-store (Point) payments.
 */
@Getter
public class OrderTypeResponse {

    /** QR code payload data for rendering the payment QR code in Point integrations. */
    private String qrData;
}
