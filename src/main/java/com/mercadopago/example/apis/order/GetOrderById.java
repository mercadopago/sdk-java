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
 * Mercado Pago Get Order.
 *
 * @see <a href="https://mercadopago.com/developers/en/reference/order/online-payments/get-order/get">Documentation</a>
 */
public class GetOrderById {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        OrderClient client = new OrderClient();

        try {
            Order order = client.get("{{ORDER_ID}}");
            System.out.println("Getting order: " + order.getId());
        } catch (MPException | MPApiException e) {
            System.out.println("Error getting order: " + e.getMessage());
        }
    }
}
