package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Example: Create Order with Point Configuration */
public class CreateOrderWithPointConfig {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        OrderClient client = new OrderClient();

        System.out.println("Creating OrderCreateRequest with Point configuration...");

        // Configure Point settings
        OrderPointConfig pointConfig = OrderPointConfig.builder()
                .terminalId("GERTEC_MP35P__12345678")
                .printOnTerminal("seller_ticket")
                .ticketNumber("ticket_01")
                .screenTime("PT30S")
                .congratsTime("PT30S")
                .build();

        // Configure Order config with Point
        OrderConfigRequest config = OrderConfigRequest.builder()
                .point(pointConfig)
                .build();

        // Create payment request
        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("100.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("debit_card")
                        .type("debit_card")
                        .installments(1)
                        .build())
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(payment);

        // Create order request
        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("point")
                .processingMode("automatic")
                .totalAmount("100.00")
                .externalReference("ext_ref_point_1234")
                .description("Example Point - Payment")
                .config(config)
                .payer(OrderPayerRequest.builder().email("{{PAYER_EMAIL}}").build())
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .build();

        System.out.println("Point Terminal ID: " + pointConfig.getTerminalId());
        System.out.println("Print on Terminal: " + pointConfig.getPrintOnTerminal());
        System.out.println("Ticket Number: " + pointConfig.getTicketNumber());

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            System.out.println("Attempting to create Point order...");
            Order order = client.create(request, requestOptions);
            System.out.println("Point Order created: " + order.getId());
            System.out.println("Order type: " + order.getType());
            System.out.println("Order status: " + order.getStatus());
            
            if (order.getConfig() != null && order.getConfig().getPoint() != null) {
                System.out.println("Terminal ID configured: " + order.getConfig().getPoint().getTerminalId());
            }
        } catch (MPApiException mpApiException) {
            System.out.println("Error creating Point order: " + mpApiException.getMessage());
            System.out.println("Status Code: " + mpApiException.getStatusCode());
            System.out.println("Error Details: " + mpApiException.getApiResponse());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating Point order: " + e.getMessage());
        }
    }
}
