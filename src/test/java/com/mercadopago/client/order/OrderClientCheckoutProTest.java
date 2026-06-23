package com.mercadopago.client.order;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static org.mockito.ArgumentMatchers.any;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mercadopago.BaseClientTest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.order.Order;
import com.mercadopago.serialization.Serializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderClientCheckoutProTest extends BaseClientTest {

    private static final String CHECKOUT_PRO_RESPONSE_FILE =
            "order/create_order_checkout_pro_response.json";

    private final OrderClient client = new OrderClient();

    @Test
    void createCheckoutProOrderSuccess() throws MPException, MPApiException, IOException {
        HttpResponse response = generateHttpResponseFromFile(CHECKOUT_PRO_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        OrderCreateRequest request = buildCheckoutProRequest();
        Order order = client.create(request);

        Assertions.assertNotNull(order);
        Assertions.assertEquals("ORDTST01KS5AJ6HTK2HRQ3XJ3C2JCKP9", order.getId());
        Assertions.assertEquals("online", order.getType());
        Assertions.assertEquals("manual", order.getProcessingMode());
        Assertions.assertEquals("500.00", order.getTotalAmount());
        Assertions.assertEquals("created", order.getStatus());
    }

    @Test
    void checkoutProResponseHasCheckoutUrl() throws MPException, MPApiException, IOException {
        HttpResponse response = generateHttpResponseFromFile(CHECKOUT_PRO_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(buildCheckoutProRequest());

        Assertions.assertNotNull(order.getCheckoutUrl());
        Assertions.assertTrue(order.getCheckoutUrl().contains("order_id=ORDTST01KS5AJ6HTK2HRQ3XJ3C2JCKP9"));
    }

    @Test
    void checkoutProResponseHasClientToken() throws MPException, MPApiException, IOException {
        HttpResponse response = generateHttpResponseFromFile(CHECKOUT_PRO_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(buildCheckoutProRequest());

        Assertions.assertNotNull(order.getClientToken());
    }

    @Test
    void checkoutProResponseHasOnlineConfigWithAutoReturn() throws MPException, MPApiException, IOException {
        HttpResponse response = generateHttpResponseFromFile(CHECKOUT_PRO_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(buildCheckoutProRequest());

        Assertions.assertNotNull(order.getConfig());
        Assertions.assertNotNull(order.getConfig().getOnline());
        Assertions.assertEquals("approved", order.getConfig().getOnline().getAutoReturn());
        Assertions.assertEquals("https://example.com/success", order.getConfig().getOnline().getSuccessUrl());
        Assertions.assertEquals("https://example.com/failure", order.getConfig().getOnline().getFailureUrl());
        Assertions.assertEquals("https://example.com/pending", order.getConfig().getOnline().getPendingUrl());
    }

    @Test
    void checkoutProResponseHasRetriesConfig() throws MPException, MPApiException, IOException {
        HttpResponse response = generateHttpResponseFromFile(CHECKOUT_PRO_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(buildCheckoutProRequest());

        OrderOnlineConfig onlineConfig = order.getConfig().getOnline();
        Assertions.assertNotNull(onlineConfig.getRetries());
        Assertions.assertFalse(onlineConfig.getRetries().getAllowed());
    }

    @Test
    void checkoutProResponseHasItems() throws MPException, MPApiException, IOException {
        HttpResponse response = generateHttpResponseFromFile(CHECKOUT_PRO_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(buildCheckoutProRequest());

        Assertions.assertNotNull(order.getItems());
        Assertions.assertEquals(2, order.getItems().size());
        Assertions.assertEquals("Flight SAO-RIO", order.getItems().get(0).getTitle());
        Assertions.assertEquals("450.00", order.getItems().get(0).getUnitPrice());
        Assertions.assertEquals("Travel insurance", order.getItems().get(1).getTitle());
    }

    @Test
    void checkoutProRequestSerializesOnlineConfig() {
        OrderCreateRequest request = buildCheckoutProRequest();
        JsonObject payload = Serializer.serializeToJson(request);

        JsonObject config = payload.getAsJsonObject("config");
        Assertions.assertNotNull(config);

        JsonObject online = config.getAsJsonObject("online");
        Assertions.assertNotNull(online);
        Assertions.assertEquals("approved", online.get("auto_return").getAsString());
        Assertions.assertEquals("https://example.com/success", online.get("success_url").getAsString());
        Assertions.assertEquals("2026-01-01T00:00:00Z", online.get("available_from").getAsString());
    }

    @Test
    void checkoutProRequestSerializesTracks() {
        OrderCreateRequest request = buildCheckoutProRequest();
        JsonObject payload = Serializer.serializeToJson(request);

        JsonArray tracks = payload
                .getAsJsonObject("config")
                .getAsJsonObject("online")
                .getAsJsonArray("tracks");

        Assertions.assertNotNull(tracks);
        Assertions.assertEquals(2, tracks.size());

        JsonObject googleTrack = tracks.get(0).getAsJsonObject();
        Assertions.assertEquals("google_ad", googleTrack.get("type").getAsString());
        Assertions.assertEquals("21312312312123",
                googleTrack.getAsJsonObject("values").get("conversion_id").getAsString());

        JsonObject fbTrack = tracks.get(1).getAsJsonObject();
        Assertions.assertEquals("facebook_ad", fbTrack.get("type").getAsString());
        Assertions.assertEquals("21312312312123",
                fbTrack.getAsJsonObject("values").get("pixel_id").getAsString());
    }

    @Test
    void checkoutProRequestSerializesPaymentMethodConfig() {
        OrderCreateRequest request = buildCheckoutProRequest();
        JsonObject payload = Serializer.serializeToJson(request);

        JsonObject paymentMethod = payload
                .getAsJsonObject("config")
                .getAsJsonObject("payment_method");

        Assertions.assertNotNull(paymentMethod);
        Assertions.assertEquals(12, paymentMethod.get("max_installments").getAsInt());
        Assertions.assertEquals("amex",
                paymentMethod.getAsJsonArray("not_allowed_ids").get(0).getAsString());
        Assertions.assertEquals("ticket",
                paymentMethod.getAsJsonArray("not_allowed_types").get(0).getAsString());

        JsonObject interestFree = paymentMethod
                .getAsJsonObject("installments")
                .getAsJsonObject("interest_free");
        Assertions.assertEquals("range", interestFree.get("type").getAsString());
        Assertions.assertEquals(2, interestFree.getAsJsonArray("values").get(0).getAsInt());
        Assertions.assertEquals(6, interestFree.getAsJsonArray("values").get(1).getAsInt());
    }

    @Test
    void checkoutProRequestSerializesItems() {
        OrderCreateRequest request = buildCheckoutProRequest();
        JsonObject payload = Serializer.serializeToJson(request);

        JsonArray items = payload.getAsJsonArray("items");
        Assertions.assertNotNull(items);
        Assertions.assertEquals(2, items.size());

        JsonObject firstItem = items.get(0).getAsJsonObject();
        Assertions.assertEquals("Flight SAO-RIO", firstItem.get("title").getAsString());
        Assertions.assertEquals("450.00", firstItem.get("unit_price").getAsString());
        Assertions.assertEquals("ITEM-001", firstItem.get("external_code").getAsString());
        Assertions.assertEquals(1, firstItem.get("quantity").getAsInt());
    }

    @Test
    void checkoutProRequestSerializesShipment() {
        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .totalAmount("500.00")
                .externalReference("ext_ref")
                .payer(OrderPayerRequest.builder().email("buyer@testuser.com").build())
                .shipment(OrderShipmentRequest.builder()
                        .mode("custom")
                        .localPickup(false)
                        .cost("15.00")
                        .freeShipping(false)
                        .address(OrderReceiverAddressRequest.builder()
                                .zipCode("01310-100")
                                .streetName("Av. Paulista")
                                .streetNumber("1000")
                                .build())
                        .build())
                .build();

        JsonObject payload = Serializer.serializeToJson(request);
        JsonObject shipment = payload.getAsJsonObject("shipment");

        Assertions.assertNotNull(shipment);
        Assertions.assertEquals("custom", shipment.get("mode").getAsString());
        Assertions.assertFalse(shipment.get("local_pickup").getAsBoolean());
        Assertions.assertEquals("15.00", shipment.get("cost").getAsString());
    }

    private static OrderCreateRequest buildCheckoutProRequest() {
        Map<String, String> googleValues = new HashMap<>();
        googleValues.put("conversion_id", "21312312312123");
        googleValues.put("conversion_label", "TEST");

        Map<String, String> fbValues = new HashMap<>();
        fbValues.put("pixel_id", "21312312312123");

        return OrderCreateRequest.builder()
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
                                .quantity(1)
                                .unitPrice("50.00")
                                .type("travel")
                                .build()))
                .config(OrderConfigRequest.builder()
                        .online(OrderOnlineConfig.builder()
                                .successUrl("https://example.com/success")
                                .failureUrl("https://example.com/failure")
                                .pendingUrl("https://example.com/pending")
                                .autoReturn("approved")
                                .availableFrom("2026-01-01T00:00:00Z")
                                .tracks(Arrays.asList(
                                        OrderTrackRequest.builder()
                                                .type("google_ad")
                                                .values(googleValues)
                                                .build(),
                                        OrderTrackRequest.builder()
                                                .type("facebook_ad")
                                                .values(fbValues)
                                                .build()))
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
    }
}
