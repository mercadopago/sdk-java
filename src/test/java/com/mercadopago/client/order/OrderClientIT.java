package com.mercadopago.client.order;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.cardtoken.CardTokenTestClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.order.Order;
import com.mercadopago.resources.order.OrderTransaction;
import com.mercadopago.resources.order.UpdateOrderTransaction;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.mercadopago.net.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/** OrderClientIT class. */
public class OrderClientIT extends BaseClientIT {
        private final OrderClient client = new OrderClient();
        private final CardTokenTestClient cardTokenTestClient = new CardTokenTestClient();

        @Test
        public void createOrderSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("automatic")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);

                        assertNotNull(order.getResponse());
                        assertEquals(CREATED, order.getResponse().getStatusCode());
                        assertNotNull(order.getId());
                        assertEquals("100.00", order.getTotalAmount());
                        assertEquals("master", order.getTransactions().getPayments().get(0).getPaymentMethod().getId());
                        assertEquals("credit_card",
                                        order.getTransactions().getPayments().get(0).getPaymentMethod().getType());
                        assertEquals(1, order.getTransactions().getPayments().get(0).getPaymentMethod()
                                        .getInstallments());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }

        @Test
        public void getOrderSuccess() {
                try {
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("pix")
                                                        .type("bank_transfer")
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("automatic")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        Order foundOrder = client.get(order.getId());

                        assertNotNull(foundOrder.getResponse());
                        assertEquals(OK, foundOrder.getResponse().getStatusCode());
                        assertEquals(order.getId(), foundOrder.getId());
                        assertEquals("100.00", foundOrder.getTotalAmount());
                        assertEquals("pix",
                                        foundOrder.getTransactions().getPayments().get(0).getPaymentMethod().getId());
                        assertEquals("bank_transfer",
                                        foundOrder.getTransactions().getPayments().get(0).getPaymentMethod().getType());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }

        @Test
        public void processOrderSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("manual")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        Order processedOrder = client.process(order.getId());

                        assertNotNull(processedOrder.getResponse());
                        assertEquals(OK, processedOrder.getResponse().getStatusCode());
                        assertNotNull(order.getId());
                        assertEquals("100.00", processedOrder.getTotalAmount());
                        assertEquals("processed", processedOrder.getStatus());
                        assertEquals("processed", processedOrder.getTransactions().getPayments().get(0).getStatus());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }

        @Test
        public void captureOrderSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("automatic")
                                        .captureMode("automatic_async")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        Order capturedOrder = client.capture(order.getId());

                        assertNotNull(capturedOrder.getResponse());
                        assertEquals(OK, capturedOrder.getResponse().getStatusCode());
                        assertEquals(order.getId(), capturedOrder.getId());
                        assertEquals("processed", capturedOrder.getStatus());
                        assertEquals("100.00", capturedOrder.getTransactions().getPayments().get(0).getAmount());
                        assertEquals("processed", capturedOrder.getTransactions().getPayments().get(0).getStatus());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }

        @Test
        public void cancelOrderSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("manual")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        Order cancelledOrder = client.cancel(order.getId());

                        assertNotNull(cancelledOrder.getResponse());
                        assertEquals(OK, cancelledOrder.getResponse().getStatusCode());
                        assertEquals(order.getId(), cancelledOrder.getId());
                        assertEquals("canceled", cancelledOrder.getStatus());
                        assertEquals("canceled", cancelledOrder.getTransactions().getPayments().get(0).getStatus());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }

        @Test
        public void totalRefundOrderSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("automatic")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        Thread.sleep(3000);
                        Order refundedOrder = client.refund(order.getId());

                        assertNotNull(refundedOrder.getResponse());
                        assertEquals(CREATED, refundedOrder.getResponse().getStatusCode());
                        assertEquals(order.getId(), refundedOrder.getId());
                        assertEquals("refunded", refundedOrder.getStatus());
                        assertEquals("refunded", refundedOrder.getStatusDetail());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException | InterruptedException e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void partialRefundOrderSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("automatic")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        Thread.sleep(3000);
                        List<OrderRefundPaymentRequest> refundPaymentRequest = new ArrayList<>();
                        refundPaymentRequest.add(OrderRefundPaymentRequest.builder()
                                        .id(order.getTransactions().getPayments().get(0).getId())
                                        .amount("25.00")
                                        .build());
                        OrderRefundRequest orderRefundRequest = OrderRefundRequest.builder()
                                        .transactions(refundPaymentRequest)
                                        .build();
                        Order refundedOrder = client.refund(order.getId(), orderRefundRequest);

                        assertNotNull(refundedOrder.getResponse());
                        assertEquals(CREATED, refundedOrder.getResponse().getStatusCode());
                        assertEquals(order.getId(), refundedOrder.getId());
                        assertEquals("processed", refundedOrder.getStatus());
                        assertEquals("partially_refunded", refundedOrder.getStatusDetail());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException | InterruptedException e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void createOrderTransactionSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("manual")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .build();
                        List<OrderPaymentRequest> orderPaymentRequestList = new ArrayList<>();
                        OrderPaymentRequest orderPaymentRequest = OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build();
                        orderPaymentRequestList.add(orderPaymentRequest);
                        OrderTransactionRequest orderTransactionRequest = OrderTransactionRequest.builder()
                                        .payments(orderPaymentRequestList)
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        OrderTransaction orderTransaction = client.createTransaction(order.getId(),
                                        orderTransactionRequest);

                        assertNotNull(orderTransaction.getResponse());
                        assertEquals(CREATED, orderTransaction.getResponse().getStatusCode());
                        assertNotNull(orderTransaction.getPayments().get(0).getId());
                        assertEquals("100.00", orderTransaction.getPayments().get(0).getAmount());
                        assertEquals("master", orderTransaction.getPayments().get(0).getPaymentMethod().getId());
                        assertEquals("credit_card", orderTransaction.getPayments().get(0).getPaymentMethod().getType());
                        assertEquals(1, orderTransaction.getPayments().get(0).getPaymentMethod().getInstallments());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }

        @Test
        public void updateOrderTransactionSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("manual")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();
                        OrderPaymentRequest updatePaymentRequest = OrderPaymentRequest.builder()
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .installments(3)
                                                        .build())
                                        .build();

                        Order order = client.create(orderCreateRequest);
                        String orderId = order.getId();
                        String transactionId = order.getTransactions().getPayments().get(0).getId();
                        UpdateOrderTransaction updatedTransaction = client.updateTransaction(orderId, transactionId,
                                        updatePaymentRequest);

                        assertNotNull(updatedTransaction.getResponse());
                        assertEquals(OK, updatedTransaction.getResponse().getStatusCode());
                        assertEquals(3, updatedTransaction.getPaymentMethod().getInstallments());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }

        @Test
        public void deleteOrderTransactionSuccess() {
                try {
                        CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
                        List<OrderPaymentRequest> paymentRequest = new ArrayList<>();
                        paymentRequest.add(OrderPaymentRequest.builder()
                                        .amount("100.00")
                                        .paymentMethod(OrderPaymentMethodRequest.builder()
                                                        .id("master")
                                                        .type("credit_card")
                                                        .token(cardToken.getId())
                                                        .installments(1)
                                                        .build())
                                        .build());
                        OrderPayerRequest orderPayerRequest = OrderPayerRequest.builder()
                                        .email("test_1731350184@testuser.com")
                                        .build();
                        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                                        .type("online")
                                        .totalAmount("100.00")
                                        .processingMode("manual")
                                        .externalReference("ext_ref_1234")
                                        .payer(orderPayerRequest)
                                        .transactions(OrderTransactionRequest.builder()
                                                        .payments(paymentRequest)
                                                        .build())
                                        .build();
                        MPRequestOptions requestOptions = MPRequestOptions.builder().build();
                        Map<String, String> headers = new HashMap<>();
                        headers.put("X-Idempotency-Key", UUID.randomUUID().toString());
                        requestOptions.setCustomHeaders(headers);

                        Order order = client.create(orderCreateRequest);
                        String orderId = order.getId();
                        String transactionId = order.getTransactions().getPayments().get(0).getId();
                        OrderTransaction deletedTransaction = client.deleteTransaction(orderId, transactionId,
                                        requestOptions);

                        assertNotNull(deletedTransaction.getResponse());
                        assertEquals(NO_CONTENT, deletedTransaction.getResponse().getStatusCode());
                } catch (MPApiException mpApiException) {
                        fail(mpApiException.getApiResponse().getContent());
                } catch (MPException mpException) {
                        fail(mpException.getMessage());
                }
        }
}
