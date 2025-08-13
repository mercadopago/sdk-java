package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.resources.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mercado Pago Capture Order.
 *
 * @see <a href="https://mercadopago.com/developers/en/reference/order/online-payments/capture/post">Documentation</a>
 */
public class CaptureOrder {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        System.out.println("Initializing OrderClient...");
        OrderClient client = new OrderClient();

        System.out.println("Creating OrderPaymentRequest...");
        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("1000.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("master")
                        .type("credit_card")
                        .token("{{CARD_TOKEN}}")
                        .installments(1)
                        .statementDescriptor("statement")
                        .build())
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(payment);

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("automatic")
                .captureMode("automatic_async")
                .totalAmount("1000.00")
                .externalReference("ext_ref")
                .payer(OrderPayerRequest.builder().email("{{PAYER_EMAIL}}").build())
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            Order order = client.create(request, requestOptions);
            System.out.println("Order created: " + order.getId());
            System.out.println("Order status: " + order.getStatus());
            System.out.println("Order status detail: " + order.getStatusDetail());

            // Capture Order
            Order capturedOrder = client.capture(order.getId(), requestOptions);
            System.out.println("Captured order: " + capturedOrder.getId());
            System.out.println("Captured order status: " + capturedOrder.getStatus());
            System.out.println("Captured order status detail: " + capturedOrder.getStatusDetail());
        } catch (Exception e) {
            System.out.println("Error creating order: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
            System.out.println("Stack Trace: " + e.getStackTrace());
            System.out.println("Error cause: " + e.getCause());
        }

    }

}
