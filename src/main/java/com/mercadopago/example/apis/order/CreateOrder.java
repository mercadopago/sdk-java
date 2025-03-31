package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mercado Pago Create Order transaction.
 *
 * @see <a href="https://mercadopago.com/developers/en/reference/order/online-payments/create/post">Documentation</a>.
 */
public class CreateOrder {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        System.out.println("Initializing OrderClient...");
        OrderClient client = new OrderClient();

        System.out.println("Creating OrderPaymentRequest...");
        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("100.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("master")
                        .type("credit_card")
                        .token("{{card_token}}")
                        .installments(1)
                        .statementDescriptor("statement")
                        .build())
                .build();

        System.out.println("Adding payment details:");
        System.out.println("Payment Amount: " + payment.getAmount());
        System.out.println("Payment Method ID: " + payment.getPaymentMethod().getId());

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(payment);

        System.out.println("Creating OrderCreateRequest...");
        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("automatic")
                .totalAmount("100.00")
                .externalReference("ref_12345")
                .payer(OrderPayerRequest.builder().email("{{PAYER_EMAIL}}").build())
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .build();

        System.out.println("Total amount: " + request.getTotalAmount());
        System.out.println("External reference: " + request.getExternalReference());

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            System.out.println("Attempting to create order...");
            Order order = client.create(request, requestOptions);
            System.out.println("Order created: " + order.getId());
            System.out.println("Order status: " + order.getStatus());
        } catch (MPApiException mpApiException) {
            System.out.println("Error creating order: " + mpApiException.getMessage());
            System.out.println("Status Code: " + mpApiException.getStatusCode());
            System.out.println("Error Code: " + mpApiException.getApiResponse());
            System.out.println("Error Details: " + mpApiException.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating order: " + e.getMessage());
        }
    }
}