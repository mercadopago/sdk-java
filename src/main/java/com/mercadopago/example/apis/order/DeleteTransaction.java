package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

/**
 * Mercado Pago Delete Order transaction.
 *
 * @see <a href=
 *      "https://mercadopago.com/developers/en/reference/order/online-payments/delete-transaction/delete">Documentation</a>
 */
public class DeleteTransaction {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");
        String orderId = "{{order_id}}";
        String transactionId = "{{transaction_id}}";

        OrderClient client = new OrderClient();

        try {
            client.deleteTransaction(orderId, transactionId);
            System.out.println("Transaction successfully deleted.");
        } catch (MPApiException e) {
            System.out.println("API error while deleting transaction: " + e.getMessage());
            System.out.println("Status: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            System.out.println("Error while deleting transaction: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}