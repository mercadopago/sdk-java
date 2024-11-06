package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.client.order.OrderPaymentMethodRequest;
import com.mercadopago.client.order.OrderPaymentRequest;
import com.mercadopago.client.order.OrderTransactionRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.net.MPResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTransaction{

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{Token}}");
        String orderId = "{{OrderId}}";

        OrderClient client = new OrderClient();

        OrderPaymentMethodRequest paymentMethodRequest = OrderPaymentMethodRequest.builder()
                .id("master")
                .type("credit_card")
                .token("{{card_token}}")
                .installments(1)
                .issuerId("123")
                .statementDescriptor("statement")
                .build();

        OrderPaymentRequest paymentRequest = OrderPaymentRequest.builder()
                .amount("100.00")
                .currency("BRL")
                .paymentMethod(paymentMethodRequest)
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(paymentRequest);

        OrderTransactionRequest transactionRequest = OrderTransactionRequest.builder()
                .payments(payments)
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sandbox", "true");
        headers.put("X-Idempotency-Key", "123456");
        headers.put("X-Caller-SiteID", "MLB");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try{
            MPResponse response = client.createTransaction(orderId, transactionRequest, requestOptions).getResponse();
            System.out.println("Order transaction created: " + response.getContent());
        } catch (Exception e) {
            System.out.println("Error creating order transaction: " + e.getMessage());
        }
    }
}