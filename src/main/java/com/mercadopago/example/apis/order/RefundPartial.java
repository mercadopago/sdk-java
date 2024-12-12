package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResponse;

import java.util.*;

public class RefundPartial {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");
        String orderId = "{{order_id}}";

        OrderClient client = new OrderClient();

        OrderRefundPaymentRequest refundRequest = OrderRefundPaymentRequest.builder()
                .id("{{payment_id}}")
                .amount("10.00")
                .build();

        List<OrderRefundPaymentRequest>  orderRefundTransactionRequests = new ArrayList<>();
        orderRefundTransactionRequests.add(refundRequest);

        OrderRefundRequest orderRequest = OrderRefundRequest.builder()
                .transactions(orderRefundTransactionRequests)
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sandbox", "true");
        headers.put("X-Idempotency-Key", "{{idempotency_key}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            MPResponse response = client.refund(orderId, orderRequest, requestOptions).getResponse();
            System.out.println("Updated transaction: " + response.getContent());
        } catch (MPException e) {
            System.out.println("Error refund order transaction: " + e.getMessage());
            System.out.println("Status: " + e.getCause());
        } catch (Exception e) {
            System.out.println("Error refund order transaction: " + e.getMessage());
        }
    }
}
