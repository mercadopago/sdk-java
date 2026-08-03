package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.order.Order;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Mercado Pago Create Order for Checkout PRO (online type).
 *
 * <p>Creates an Order with type "online" and processing_mode "manual". The API returns a
 * checkout_url to redirect the buyer to the Checkout PRO payment flow.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/online-payments/checkout-api/create-order/post">Orders API</a>
 */
public class CreateOrderCheckoutPro {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        OrderClient client = new OrderClient();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("manual")
                .captureMode("automatic")
                .totalAmount("500.00")
                .externalReference("ext_ref_checkout_pro_001")
                .description("Travel package SAO-RIO with insurance")
                .expirationTime("P1D")
                .marketplaceFee("5.00")
                .payer(OrderPayerRequest.builder()
                        .email("buyer@testuser.com")
                        .firstName("John")
                        .lastName("Smith")
                        .identification(IdentificationRequest.builder()
                                .type("CPF")
                                .number("12345678909")
                                .build())
                        .address(OrderPayerAddressRequest.builder()
                                .zipCode("01310-100")
                                .streetName("Av. Paulista")
                                .streetNumber("1000")
                                .neighborhood("Bela Vista")
                                .city("São Paulo")
                                .build())
                        .build())
                .items(Arrays.asList(
                        OrderItemRequest.builder()
                                .externalCode("ITEM-001")
                                .title("Flight SAO-RIO")
                                .description("Round trip, economy class")
                                .categoryId("travels")
                                .pictureUrl("https://example.com/img.jpg")
                                .quantity(1)
                                .unitPrice("450.00")
                                .type("travel")
                                .eventDate("2027-01-15T00:00:00.000-03:00")
                                .build(),
                        OrderItemRequest.builder()
                                .externalCode("ITEM-002")
                                .title("Travel insurance")
                                .description("Basic coverage during trip")
                                .categoryId("travels")
                                .pictureUrl("https://example.com/insurance.jpg")
                                .quantity(1)
                                .unitPrice("50.00")
                                .type("travel")
                                .eventDate("2027-01-15T00:00:00.000-03:00")
                                .build()))
                .config(OrderConfigRequest.builder()
                        .online(OrderOnlineConfig.builder()
                                .successUrl("https://example.com/success")
                                .failureUrl("https://example.com/failure")
                                .pendingUrl("https://example.com/pending")
                                .autoReturn("approved")
                                .availableFrom("2026-01-01T00:00:00Z")
                                .tracks(Arrays.asList(
                                        buildGoogleAdTrack("21312312312123", "TEST"),
                                        buildFacebookAdTrack("21312312312123")))
                                .build())
                        .paymentMethod(OrderPaymentMethodConfig.builder()
                                .maxInstallments(12)
                                .notAllowedIds(Collections.singletonList("amex"))
                                .notAllowedTypes(Collections.singletonList("ticket"))
                                .installments(OrderInstallmentsRequest.builder()
                                        .interestFree(OrderInstallmentsInterestFreeRequest.builder()
                                                .type("range")
                                                .values(Arrays.asList(2, 6))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            Order order = client.create(request, requestOptions);
            System.out.println("Order created: " + order.getId());
            System.out.println("Checkout URL: " + order.getCheckoutUrl());
            System.out.println("Status: " + order.getStatus());
        } catch (MPApiException mpApiException) {
            System.out.println("API error: " + mpApiException.getMessage());
            System.out.println("Status code: " + mpApiException.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static OrderTrackRequest buildGoogleAdTrack(String conversionId, String conversionLabel) {
        Map<String, String> values = new HashMap<>();
        values.put("conversion_id", conversionId);
        values.put("conversion_label", conversionLabel);
        return OrderTrackRequest.builder().type("google_ad").values(values).build();
    }

    private static OrderTrackRequest buildFacebookAdTrack(String pixelId) {
        Map<String, String> values = new HashMap<>();
        values.put("pixel_id", pixelId);
        return OrderTrackRequest.builder().type("facebook_ad").values(values).build();
    }
}
