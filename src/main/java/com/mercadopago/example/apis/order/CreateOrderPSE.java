package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mercado Pago — Create Order with PSE (Pagos Seguros en Línea — Colombia).
 *
 * <p>PSE is Colombia's standard online bank-transfer payment method. The integrator initiates
 * the transaction with {@code payment_method.id = "pse"} and {@code type = "bank_transfer"},
 * and must specify the buyer's bank via {@code financial_institution}.
 *
 * <p>Required PSE-specific fields:
 * <ul>
 *   <li>{@code payment_method.id} = "pse" (fixed)</li>
 *   <li>{@code payment_method.type} = "bank_transfer" (fixed)</li>
 *   <li>{@code payment_method.financial_institution} = PSE bank code (see table below)</li>
 *   <li>{@code currency} = "COP" (Colombia only)</li>
 *   <li>{@code payer.entity_type} = "individual" or "association"</li>
 *   <li>{@code payer.identification.type} = "CC", "NIT", etc. (Colombian doc type)</li>
 *   <li>{@code additional_info.payer.ip_address} (required by risk engine)</li>
 *   <li>{@code config.online.callback_url} (URL to redirect the buyer after auth)</li>
 * </ul>
 *
 * <p>Most-used PSE bank codes (full catalog via MP API):
 * <pre>
 *   Bancolombia ........... 1007
 *   Davivienda ............ 1051
 *   Banco de Bogotá ....... 1001
 *   BBVA Colombia ......... 1013
 *   Banco Popular ......... 1002
 *   Scotiabank Colpatria .. 1019
 * </pre>
 *
 * @see <a href="https://www.mercadopago.com.co/developers/es/docs">Documentación oficial PSE</a>
 */
public class CreateOrderPSE {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        OrderClient client = new OrderClient();

        // PSE payment: id="pse", type="bank_transfer", financial_institution = bank code.
        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("50000.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("pse")
                        .type("bank_transfer")
                        .financialInstitution("1007") // Bancolombia
                        .build())
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(payment);

        // Payer: entity_type + Colombian identification (CC/NIT) are required for PSE.
        OrderPayerRequest payer = OrderPayerRequest.builder()
                .email("{{PAYER_EMAIL}}")
                .firstName("{{FIRST_NAME}}")
                .lastName("{{LAST_NAME}}")
                .entityType("individual")
                .identification(IdentificationRequest.builder()
                        .type("CC")
                        .number("{{PAYER_DOC_NUMBER}}")
                        .build())
                .build();

        // additional_info.payer.ip_address — required by MP's risk engine for PSE.
        AdditionalInfoRequest additionalInfo = AdditionalInfoRequest.builder()
                .payer(PayerInfo.builder()
                        .ipAddress("{{CLIENT_IP}}")
                        .build())
                .build();

        // callback_url — where the bank redirects the buyer after authorization.
        OrderConfigRequest config = OrderConfigRequest.builder()
                .online(OrderOnlineConfig.builder()
                        .callbackUrl("{{CALLBACK_URL}}")
                        .build())
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("automatic")
                .totalAmount("50000.00")
                .currency("COP")
                .externalReference("ref_pse_12345")
                .payer(payer)
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .additionalInfo(additionalInfo)
                .config(config)
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
        } catch (MPApiException mpApiException) {
            System.out.println("Error creating order: " + mpApiException.getMessage());
            System.out.println("Status Code: " + mpApiException.getStatusCode());
            System.out.println("Error Code: " + mpApiException.getApiResponse());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
