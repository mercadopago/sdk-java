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
 * @see <a href=
 *      "https://mercadopago.com/developers/en/reference/orders/online-payments/create/post">Documentation</a>.
 */
public class CreateOrderWithIndustryFields {
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
                        .token("{{CARD_TOKEN}}")
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

        // Build additionalInfo map
        Map<String, Object> additionalInfo = new HashMap<>();

        // Payer info
        additionalInfo.put("payer.authentication_type", "MOBILE");
        additionalInfo.put("payer.registration_date", "2022-01-01T00:00:00Z");
        additionalInfo.put("payer.is_prime_user", true);
        additionalInfo.put("payer.is_first_purchase_online", false);
        additionalInfo.put("payer.last_purchase", "2024-12-01T12:00:00Z");

        // Shipment info
        additionalInfo.put("shipment.express", true);
        additionalInfo.put("shipment.local_pickup", false);

        // Platform shipment
        additionalInfo.put("platform.shipment.delivery_promise", "delivery_promise");
        additionalInfo.put("platform.shipment.drop_shipping", "drop_shipping");
        additionalInfo.put("platform.shipment.safety", "safety");
        additionalInfo.put("platform.shipment.tracking.code", "TRACK123");
        additionalInfo.put("platform.shipment.tracking.status", "status");
        additionalInfo.put("platform.shipment.withdrawn", false);

        // Platform seller
        additionalInfo.put("platform.seller.id", "123456");
        additionalInfo.put("platform.seller.name", "Seller name");
        additionalInfo.put("platform.seller.email", "seller@example.com");
        additionalInfo.put("platform.seller.status", "active");
        additionalInfo.put("platform.seller.referral_url", "https://example.com");
        additionalInfo.put("platform.seller.registration_date", "2020-05-01T00:00:00Z");
        additionalInfo.put("platform.seller.hired_plan", "Premium");
        additionalInfo.put("platform.seller.business_type", "E-commerce");
        additionalInfo.put("platform.seller.address.zip_code", "01310000");
        additionalInfo.put("platform.seller.address.street_name", "Av. Paulista");
        additionalInfo.put("platform.seller.address.street_number", "100");
        additionalInfo.put("platform.seller.address.city", "São Paulo");
        additionalInfo.put("platform.seller.address.state", "SP");
        additionalInfo.put("platform.seller.address.complement", "101");
        additionalInfo.put("platform.seller.address.country", "Brasil");
        additionalInfo.put("platform.seller.identification.type", "CNPJ");
        additionalInfo.put("platform.seller.identification.number", "12.345.678/0001-99");
        additionalInfo.put("platform.seller.phone.area_code", "11");
        additionalInfo.put("platform.seller.phone.number", "999999999");

        // Platform authentication
        additionalInfo.put("platform.authentication", "2FA");

        // Travel info
        additionalInfo.put("travel.passengers", new Object[] {
                Map.of("first_name", "John", "last_name", "Doe", "identification",
                        Map.of("type", "CPF", "number", "12345678900")),
        });
        additionalInfo.put("travel.routes", new Object[] {
                Map.of("departure", "GRU", "destination", "CWB", "departure_date_time", "2024-12-01T12:00:00Z",
                        "arrival_date_time", "2024-12-01T14:00:00Z", "company", "LATAM"),
        });

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("automatic")
                .totalAmount("100.00")
                .externalReference("ref_12345")
                .payer(OrderPayerRequest.builder().email("{{PAYER_EMAIL}}").build())
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .additionalInfo(additionalInfo)
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
