package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.order.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of creating an Order with 3DS (3D Secure) authentication.
 * This example demonstrates how to configure transaction security for enhanced payment security.
 */
public class CreateOrderWith3DS {

    /**
     * Creates an order with 3DS authentication enabled.
     * 
     * @param accessToken Your MercadoPago access token
     * @param cardToken   Token representing the credit card
     * @param payerEmail  Payer's email address
     * @param identificationType Payer's identification type (e.g., "CPF", "DNI")
     * @param identificationNumber Payer's identification number
     * @return Created Order with 3DS configuration
     * @throws MPException if there's an error in the request
     * @throws MPApiException if the API returns an error
     */
    public static Order createOrderWith3DS(
            String accessToken,
            String cardToken,
            String payerEmail,
            String identificationType,
            String identificationNumber) throws MPException, MPApiException {

        // Set your access token
        MercadoPagoConfig.setAccessToken(accessToken);

        // Create the order client
        OrderClient client = new OrderClient();

        // Configure 3DS transaction security
        OrderTransactionSecurity transactionSecurity = OrderTransactionSecurity.builder()
                .validation("on_fraud_risk") // Recommended: validate based on fraud risk
                .liabilityShift("required")   // Required for liability shift to card brand
                .build();

        // Configure online settings with 3DS
        OrderOnlineConfig onlineConfig = OrderOnlineConfig.builder()
                .transactionSecurity(transactionSecurity)
                .build();

        // Configure order settings
        OrderConfigRequest config = OrderConfigRequest.builder()
                .online(onlineConfig)
                .build();

        // Configure payment method
        OrderPaymentMethodRequest paymentMethod = OrderPaymentMethodRequest.builder()
                .id("master")                    // Card brand (master, visa, etc.)
                .type("credit_card")             // Payment type
                .token(cardToken)                // Card token from MercadoPago SDK JS
                .installments(1)                 // Number of installments
                .build();

        // Configure payment
        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(OrderPaymentRequest.builder()
                .amount("150.00")
                .paymentMethod(paymentMethod)
                .build());

        // Configure transactions
        OrderTransactionRequest transactions = OrderTransactionRequest.builder()
                .payments(payments)
                .build();

        // Configure payer information
        IdentificationRequest identification = IdentificationRequest.builder()
                .type(identificationType)
                .number(identificationNumber)
                .build();

        OrderPayerRequest payer = OrderPayerRequest.builder()
                .email(payerEmail)
                .identification(identification)
                .build();

        // Create the order request
        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .externalReference("3ds_test_" + System.currentTimeMillis())
                .processingMode("automatic")
                .totalAmount("150.00")
                .config(config)
                .payer(payer)
                .transactions(transactions)
                .build();

        // Create the order
        Order order = client.create(request);

        return order;
    }

    /**
     * Main method to demonstrate the usage.
     */
    public static void main(String[] args) {
        try {
            // Replace with your actual values
            String accessToken = "YOUR_ACCESS_TOKEN";
            String cardToken = "YOUR_CARD_TOKEN";  // Generated using MercadoPago SDK JS
            String payerEmail = "test_user@example.com";
            String identificationType = "CPF";
            String identificationNumber = "12345678901";

            Order order = createOrderWith3DS(
                    accessToken,
                    cardToken,
                    payerEmail,
                    identificationType,
                    identificationNumber
            );

            System.out.println("Order created successfully!");
            System.out.println("Order ID: " + order.getId());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Status Detail: " + order.getStatusDetail());

            // Check if 3DS challenge is required
            if ("action_required".equals(order.getStatus()) && 
                "pending_challenge".equals(order.getStatusDetail())) {
                
                // Get the challenge URL from the payment method
                if (order.getTransactions() != null && 
                    order.getTransactions().getPayments() != null && 
                    !order.getTransactions().getPayments().isEmpty()) {
                    
                    var payment = order.getTransactions().getPayments().get(0);
                    if (payment.getPaymentMethod() != null && 
                        payment.getPaymentMethod().getTransactionSecurity() != null) {
                        
                        String challengeUrl = payment.getPaymentMethod()
                                .getTransactionSecurity().getUrl();
                        
                        System.out.println("3DS Challenge required!");
                        System.out.println("Challenge URL: " + challengeUrl);
                        System.out.println("Display this URL in an iframe for the user to complete the challenge.");
                    }
                }
            } else if ("processed".equals(order.getStatus())) {
                System.out.println("Payment processed successfully without 3DS challenge.");
            }

        } catch (MPException | MPApiException e) {
            System.err.println("Error creating order: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
