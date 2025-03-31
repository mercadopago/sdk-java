package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Mercado Pago Cancel Order.
 *
 * @see <a href="https://mercadopago.com/developers/en/reference/order/online-payments/cancel-order/post">Documentation</a>.
 */
public class CancelOrder {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN");

        OrderClient client = new OrderClient();

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            Order order = client.cancel("{{ORDER_ID}}", requestOptions);
            System.out.println("Canceled order: " + order.getId());
            System.out.println("Status: " + order.getStatus());
        } catch (MPException | MPApiException e) {
            System.out.println("Error canceling order: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
        }

    }

}
