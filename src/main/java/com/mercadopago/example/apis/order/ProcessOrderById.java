package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;

/**
 * Mercado Pago Process Order.
 *
 * @see <a href="https://mercadopago.com/developers/en/reference/order/online/process-order/post">Documentation</a>
 */
public class ProcessOrderById {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        OrderClient client = new OrderClient();

        try {
            Order order = client.process("{{order_id}}");
            System.out.println("Process order: " + order.getId());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Status Detail: " + order.getStatusDetail());
        } catch (MPException | MPApiException e) {
            System.out.println("Error getting order: " + e.getMessage());
        }
    }
}