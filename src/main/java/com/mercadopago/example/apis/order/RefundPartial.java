package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResponse;

import java.util.*;

/**
 * Mercado Pago Partial Refund Order.
 *
 * @see <a href="https://mercadopago.com/developers/en/reference/order/online-payments/refund/post">Documentation</a>
 */
public class RefundPartial {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");
        String orderId = "{{order_id}}";

        OrderClient client = new OrderClient();

        OrderRefundPaymentRequest refundRequest = OrderRefundPaymentRequest.builder()
                .id("{{transaction_id}}")
                .amount("10.00")
                .build();

        List<OrderRefundPaymentRequest>  orderRefundTransactionRequests = new ArrayList<>();
        orderRefundTransactionRequests.add(refundRequest);

        OrderRefundRequest orderRequest = OrderRefundRequest.builder()
                .transactions(orderRefundTransactionRequests)
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "{{idempotency_key}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            MPResponse response = client.refund(orderId, orderRequest, requestOptions).getResponse();
            System.out.println("Updated transaction: " + response.getContent());
        } catch (MPException e) {
            System.out.println("Error refund order transaction: " + e.getMessage());
            System.out.println("Status: " + e.getCause());
        } catch (Exception e) {
            System.out.println("Error refund order transaction: " + e.getMessage());
        }
    }
}
