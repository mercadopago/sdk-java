package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.order.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;
import com.mercadopago.resources.order.OrderPayment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mercado Pago Create Order — Automatic Payments (recurring charges).
 *
 * <p>Demonstrates the two-step Automatic Payments flow:
 * <ol>
 *   <li>First payment — CVV-validated charge that registers the card credential.</li>
 *   <li>Recurring charge — subsequent MIT charge without CVV, referencing step 1.</li>
 * </ol>
 *
 * <p>Prerequisites:
 * <ul>
 *   <li>A customer created via {@code POST /v1/customers} → {@code CUSTOMER_ID}</li>
 *   <li>A payment profile created via {@code POST /v1/customers/{id}/payment-profiles}
 *       → {@code PAYMENT_PROFILE_ID}</li>
 * </ul>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/docs/automatic-payments-orders/overview">
 *     Automatic Payments Documentation</a>
 */
public class CreateOrderWithAutomaticPayments {

    /**
     * Creates the first payment in an Automatic Payments chain.
     * Registers the card credential — {@code first_payment: true}, no previous reference needed.
     */
    public static Order createFirstPayment(
            String cardToken,
            String customerId,
            String paymentProfileId,
            String payerEmail) throws MPException, MPApiException {

        OrderClient client = new OrderClient();

        OrderPaymentMethodRequest paymentMethod = OrderPaymentMethodRequest.builder()
                .id("master")
                .type("credit_card")
                .token(cardToken)
                .installments(1)
                .build();

        OrderAutomaticPaymentsRequest automaticPayments = OrderAutomaticPaymentsRequest.builder()
                .paymentProfileId(paymentProfileId)
                .build();

        OrderStoredCredentialRequest storedCredential = OrderStoredCredentialRequest.builder()
                .paymentInitiator("customer")
                .reason("recurring")
                .firstPayment(true)
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(OrderPaymentRequest.builder()
                .amount("100.00")
                .paymentMethod(paymentMethod)
                .automaticPayments(automaticPayments)
                .storedCredential(storedCredential)
                .build());

        OrderPayerRequest payer = OrderPayerRequest.builder()
                .email(payerEmail)
                .customerId(customerId)
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("automatic")
                .totalAmount("100.00")
                .externalReference("subscription-001-payment-1")
                .payer(payer)
                .transactions(OrderTransactionRequest.builder().payments(payments).build())
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "<IDEMPOTENCY_KEY_FIRST>");

        return client.create(request,
                com.mercadopago.core.MPRequestOptions.builder()
                        .customHeaders(headers).build());
    }

    /**
     * Creates a recurring charge using the payment profile — no card token needed.
     * {@code firstPaymentId} links this charge to the original card-network authorization.
     */
    public static Order createRecurringCharge(
            String customerId,
            String paymentProfileId,
            String payerEmail,
            String firstPaymentId,
            int sequenceNumber) throws MPException, MPApiException {

        OrderClient client = new OrderClient();

        OrderAutomaticPaymentsRequest automaticPayments = OrderAutomaticPaymentsRequest.builder()
                .paymentProfileId(paymentProfileId)
                .retries(3)
                .scheduleDate("2026-09-01T00:00:00.000-04:00")
                .dueDate("2026-09-05T00:00:00.000-04:00")
                .build();

        OrderStoredCredentialRequest storedCredential = OrderStoredCredentialRequest.builder()
                .paymentInitiator("merchant")
                .reason("recurring")
                .firstPayment(false)
                .prevTransactionRef(firstPaymentId)
                .build();

        OrderSubscriptionDataRequest subscriptionData = OrderSubscriptionDataRequest.builder()
                .invoiceId("INV-00" + sequenceNumber)
                .billingDate("2026-08-01")
                .subscriptionSequence(OrderSubscriptionSequenceRequest.builder()
                        .number(sequenceNumber)
                        .total(12)
                        .build())
                .invoicePeriod(OrderInvoicePeriodRequest.builder()
                        .type("monthly")
                        .period(1)
                        .build())
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(OrderPaymentRequest.builder()
                .amount("100.00")
                .automaticPayments(automaticPayments)
                .storedCredential(storedCredential)
                .subscriptionData(subscriptionData)
                .build());

        OrderPayerRequest payer = OrderPayerRequest.builder()
                .email(payerEmail)
                .customerId(customerId)
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("automatic_async")
                .totalAmount("100.00")
                .externalReference("subscription-001-payment-" + sequenceNumber)
                .payer(payer)
                .transactions(OrderTransactionRequest.builder().payments(payments).build())
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "<IDEMPOTENCY_KEY_RECURRING_" + sequenceNumber + ">");

        return client.create(request,
                com.mercadopago.core.MPRequestOptions.builder()
                        .customHeaders(headers).build());
    }

    public static void main(String[] args) {
        try {
            MercadoPagoConfig.setAccessToken("<ACCESS_TOKEN>");

            String customerId      = "<CUSTOMER_ID>";
            String paymentProfileId = "<PAYMENT_PROFILE_ID>";
            String payerEmail      = "<PAYER_EMAIL>";
            String cardToken       = "<CARD_TOKEN>";

            // ── Step 1: First payment ─────────────────────────────────────────
            Order firstOrder = createFirstPayment(cardToken, customerId, paymentProfileId, payerEmail);
            System.out.println("First payment order ID: " + firstOrder.getId());
            System.out.println("Status: " + firstOrder.getStatus());

            // Save the payment ID — required as prevTransactionRef in subsequent charges
            String firstPaymentId = firstOrder.getTransactions()
                    .getPayments().get(0).getId();
            System.out.println("First payment ID (save for next charge): " + firstPaymentId);

            // ── Step 2: Recurring charge ──────────────────────────────────────
            Order recurringOrder = createRecurringCharge(
                    customerId, paymentProfileId, payerEmail, firstPaymentId, 2);
            System.out.println("\nRecurring charge order ID: " + recurringOrder.getId());
            System.out.println("Status: " + recurringOrder.getStatus());
            System.out.println("Status detail: " + recurringOrder.getStatusDetail());

        } catch (MPApiException e) {
            System.err.println("API error: " + e.getMessage());
            System.err.println("Status code: " + e.getStatusCode());
            System.err.println("Response: " + e.getApiResponse());
        } catch (MPException e) {
            System.err.println("SDK error: " + e.getMessage());
        }
    }
}
