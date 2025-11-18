package com.mercadopago.client.order;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Order creation with 3DS authentication.
 */
public class OrderClientWith3DSTest extends BaseClientIT {

    private final OrderClient client = new OrderClient();

    @Test
    public void testCreateOrderWith3DS_Success() throws MPException, MPApiException {
        // Configure 3DS transaction security
        OrderTransactionSecurity transactionSecurity = OrderTransactionSecurity.builder()
                .validation("on_fraud_risk")
                .liabilityShift("required")
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
                .id("master")
                .type("credit_card")
                .token("test_card_token")
                .installments(1)
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
                .type("CPF")
                .number("12345678901")
                .build();

        OrderPayerRequest payer = OrderPayerRequest.builder()
                .email("test_user@example.com")
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

        // Verify that the request is properly configured
        assertNotNull(request.getConfig());
        assertNotNull(request.getConfig().getOnline());
        assertNotNull(request.getConfig().getOnline().getTransactionSecurity());
        assertEquals("on_fraud_risk", request.getConfig().getOnline().getTransactionSecurity().getValidation());
        assertEquals("required", request.getConfig().getOnline().getTransactionSecurity().getLiabilityShift());
    }

    @Test
    public void testTransactionSecurityBuilder() {
        // Test building transaction security with all validation options
        OrderTransactionSecurity alwaysValidation = OrderTransactionSecurity.builder()
                .validation("always")
                .liabilityShift("preferred")
                .build();

        assertEquals("always", alwaysValidation.getValidation());
        assertEquals("preferred", alwaysValidation.getLiabilityShift());

        OrderTransactionSecurity neverValidation = OrderTransactionSecurity.builder()
                .validation("never")
                .liabilityShift("required")
                .build();

        assertEquals("never", neverValidation.getValidation());
        assertEquals("required", neverValidation.getLiabilityShift());

        OrderTransactionSecurity fraudRiskValidation = OrderTransactionSecurity.builder()
                .validation("on_fraud_risk")
                .liabilityShift("required")
                .build();

        assertEquals("on_fraud_risk", fraudRiskValidation.getValidation());
        assertEquals("required", fraudRiskValidation.getLiabilityShift());
    }

    @Test
    public void testOrderOnlineConfigWith3DS() {
        OrderTransactionSecurity transactionSecurity = OrderTransactionSecurity.builder()
                .validation("on_fraud_risk")
                .liabilityShift("required")
                .build();

        OrderOnlineConfig onlineConfig = OrderOnlineConfig.builder()
                .callbackUrl("https://example.com/callback")
                .successUrl("https://example.com/success")
                .failureUrl("https://example.com/failure")
                .transactionSecurity(transactionSecurity)
                .build();

        assertNotNull(onlineConfig.getTransactionSecurity());
        assertEquals("on_fraud_risk", onlineConfig.getTransactionSecurity().getValidation());
        assertEquals("required", onlineConfig.getTransactionSecurity().getLiabilityShift());
        assertEquals("https://example.com/callback", onlineConfig.getCallbackUrl());
        assertEquals("https://example.com/success", onlineConfig.getSuccessUrl());
        assertEquals("https://example.com/failure", onlineConfig.getFailureUrl());
    }
}
