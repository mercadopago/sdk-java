package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.client.order.OrderPaymentMethodRequest;
import com.mercadopago.client.order.OrderPaymentRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.resources.order.UpdateOrderTransaction;

import java.util.HashMap;
import java.util.Map;

/**
 * Mercado Pago Update Order transaction.
 *
 * @see <a href=
 *      "https://mercadopago.com/developers/en/reference/order/online-payments/update-transaction/put">Documentation</a>
 */
public class UpdateTransaction {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        String orderId = "{{order_id}}";
        String transactionId = "{{transaction_id}}";

        OrderClient client = new OrderClient();

        OrderPaymentRequest paymentRequest = OrderPaymentRequest.builder()
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .type("credit_card")
                        .installments(8)
                        .statementDescriptor("statement")
                        .build())
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            UpdateOrderTransaction updatedTransaction = client.updateTransaction(orderId, transactionId, paymentRequest,
                    requestOptions);
            System.out.println("Updated transaction: " + updatedTransaction.getResponse().getContent());
        } catch (Exception e) {
            System.out.println("Error updating order transaction: " + e.getMessage());
            System.out.println("Status: " + e.getCause());
        }
    }
}