package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;

import java.util.HashMap;
import java.util.Map;

public class CancelOrder {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{TOKEN}}}}");

        OrderClient client = new OrderClient();

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-Sandbox", "true");
        headers.put("X-Idempotency-Key", "123456");
        headers.put("X-Caller-SiteID", "MLB");


        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            Order order = client.cancel("{{ORDERID}}", requestOptions);
            System.out.println("Canceled order: " + order.getId());
            System.out.println("Status: " + order.getStatus());
        } catch (MPException | MPApiException e) {
            System.out.println("Error canceling order: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
        }

    }

}
