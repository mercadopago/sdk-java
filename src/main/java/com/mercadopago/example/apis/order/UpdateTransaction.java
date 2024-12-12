package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.client.order.OrderPaymentRequest;
import com.mercadopago.client.order.OrderTransactionRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.order.OrderTransaction;
import com.mercadopago.resources.order.UpdateOrderTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateTransaction {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        String orderId = "{{ORDER_ID}}";
        String transactionId = "{{TRANSACTION_ID}}";

        OrderClient client = new OrderClient();

        OrderPaymentRequest paymentRequest = OrderPaymentRequest.builder()
                .amount("50.00")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sandbox", "true");
        headers.put("X-Idempotency-Key", "{{SOME_UNIQUE_VALUE}}");
        headers.put("X-Caller-SiteID", "MLB");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            UpdateOrderTransaction updatedTransaction = client.updateTransaction(orderId, transactionId, paymentRequest, requestOptions);
            System.out.println("Updated transaction: " + updatedTransaction.getResponse().getContent());
        } catch (Exception e) {
            System.out.println("Error updating order transaction: " + e.getMessage());
            System.out.println("Status: " + e.getCause());
        }
    }
}