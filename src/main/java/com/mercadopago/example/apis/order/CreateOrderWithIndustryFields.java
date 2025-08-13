package com.mercadopago.example.apis.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * Mercado Pago Create Order with industry fields.
 *
 * @see <a href=
 *      "https://mercadopago.com/developers/en/reference/orders/online-payments/create/post">Documentation</a>.
 */
public class CreateOrderWithIndustryFields {
    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        System.out.println("Initializing OrderClient...");
        OrderClient client = new OrderClient();

        System.out.println("Creating OrderPaymentRequest...");
        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("100.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("master")
                        .type("credit_card")
                        .token("{{CARD_TOKEN}}")
                        .installments(1)
                        .statementDescriptor("statement")
                        .build())
                .build();

        System.out.println("Adding payment details:");
        System.out.println("Payment Amount: " + payment.getAmount());
        System.out.println("Payment Method ID: " + payment.getPaymentMethod().getId());

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(payment);

        System.out.println("Creating OrderCreateRequest...");

        AdditionalInfoRequest additionalInfo = AdditionalInfoRequest.builder()
                .payer(PayerInfo.builder()
                        .authenticationType("MOBILE")
                        .registrationDate("2022-01-01T00:00:00Z")
                        .isPrimeUser(true)
                        .isFirstPurchaseOnline(false)
                        .lastPurchase("2024-12-01T12:00:00Z")
                        .build())
                .shipment(ShipmentInfo.builder()
                        .express(true)
                        .localPickup(false)
                        .build())
                .platform(PlatformInfo.builder()
                        .authentication("2FA")
                        .shipment(PlatformShipment.builder()
                                .deliveryPromise("delivery_promise")
                                .dropShipping("drop_shipping")
                                .safety("safety")
                                .tracking(TrackingInfo.builder()
                                        .code("TRACK123")
                                        .status("status")
                                        .build())
                                .withdrawn(false)
                                .build())
                        .seller(SellerInfo.builder()
                                .id("123456")
                                .name("Seller name")
                                .email("seller@example.com")
                                .status("active")
                                .referralUrl("https://example.com")
                                .registrationDate("2020-05-01T00:00:00Z")
                                .hiredPlan("Premium")
                                .businessType("E-commerce")
                                .address(SellerAddress.builder()
                                        .zipCode("01310000")
                                        .streetName("Av. Paulista")
                                        .streetNumber("100")
                                        .city("São Paulo")
                                        .state("SP")
                                        .complement("101")
                                        .country("Brasil")
                                        .build())
                                .identification(SellerIdentification.builder()
                                        .type("CNPJ")
                                        .number("12.345.678/0001-99")
                                        .build())
                                .phone(SellerPhone.builder()
                                        .areaCode("11")
                                        .number("999999999")
                                        .build())
                                .build())
                        .build())
                .travel(TravelInfo.builder()
                        .passengers(Arrays.asList(
                                TravelPassengerRequest.builder()
                                        .firstName("John")
                                        .lastName("Doe")
                                        .identification(PassengerIdentification.builder()
                                                .type("CPF")
                                                .number("12345678900")
                                                .build())
                                        .build()))
                        .routes(Arrays.asList(
                                TravelRouteRequest.builder()
                                        .departure("GRU")
                                        .destination("CWB")
                                        .departureDateTime("2024-12-01T12:00:00Z")
                                        .arrivalDateTime("2024-12-01T14:00:00Z")
                                        .company("LATAM")
                                        .build()))
                        .build())
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .processingMode("automatic")
                .totalAmount("100.00")
                .externalReference("ref_12345")
                .payer(OrderPayerRequest.builder().email("{{PAYER_EMAIL}}").build())
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .additionalInfo(additionalInfo)
                .build();

        System.out.println("Total amount: " + request.getTotalAmount());
        System.out.println("External reference: " + request.getExternalReference());

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();

        try {
            System.out.println("Attempting to create order...");
            Order order = client.create(request, requestOptions);
            System.out.println("Order created: " + order.getId());
            System.out.println("Order status: " + order.getStatus());
        } catch (MPApiException mpApiException) {
            System.out.println("Error creating order: " + mpApiException.getMessage());
            System.out.println("Status Code: " + mpApiException.getStatusCode());
            System.out.println("Error Code: " + mpApiException.getApiResponse());
            System.out.println("Error Details: " + mpApiException.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating order: " + e.getMessage());
        }
    }
}
