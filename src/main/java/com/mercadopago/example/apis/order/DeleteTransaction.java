package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import java.util.HashMap;
import java.util.Map;

public class DeleteTransaction {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");
        String orderId = "{{order_id}}";
        String transactionId = "{{transaction_id}}";

        OrderClient client = new OrderClient();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sandbox", "true");
        headers.put("X-Idempotency-Key", "1234569999");
        headers.put("X-Caller-SiteID", "MLB");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            client.deleteTransaction(orderId, transactionId, requestOptions);
            System.out.println("Transaction successfully deleted.");
        } catch (MPApiException e) {
            System.out.println("API error while deleting transaction: " + e.getMessage());
            System.out.println("Status: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            System.out.println("Error while deleting transaction: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}