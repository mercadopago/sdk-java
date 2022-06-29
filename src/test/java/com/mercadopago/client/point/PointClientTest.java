package com.mercadopago.client.point;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.point.PointPaymentIntent;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** PointClientTest class. */
class PointClientTest extends BaseClientTest {
  private final String paymentIntentJson = "point/payment_intent.json";

  private final String deviceId = "GERTEC_MP35P__8701012051267123";

  private final PointClient client = new PointClient();

  @Test
  void createPaymentIntentSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointPaymentIntent paymentIntent =
        client.createPaymentIntent(deviceId, newPaymentIntentRequest());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(paymentIntentJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(paymentIntent.getResponse());
    assertEquals(CREATED, paymentIntent.getResponse().getStatusCode());
    assertPaymentIntentFields(paymentIntent);
  }

  @Test
  void createPaymentIntentWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointPaymentIntent paymentIntent =
        client.createPaymentIntent(deviceId, newPaymentIntentRequest(), buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(paymentIntentJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(paymentIntent.getResponse());
    assertEquals(CREATED, paymentIntent.getResponse().getStatusCode());
    assertPaymentIntentFields(paymentIntent);
  }

  private void assertPaymentIntentFields(PointPaymentIntent paymentIntent) {
    assertNotNull(paymentIntent.getAdditionalInfo());
    assertEquals(
        "4561ads-das4das4-das4754-das456",
        paymentIntent.getAdditionalInfo().getExternalReference());
    assertTrue(paymentIntent.getAdditionalInfo().getPrintOnTerminal());
    assertEquals(new BigDecimal("1500"), paymentIntent.getAmount());
    assertEquals("your payment intent description", paymentIntent.getDescription());
    assertEquals("GERTEC_MP35P__8701012051267123", paymentIntent.getDeviceId());
    assertEquals("68bf6839-ddb3-4825-8f4c-7eb26e68a5c3", paymentIntent.getId());
    assertNotNull(paymentIntent.getPayment());
    assertEquals(1, paymentIntent.getPayment().getInstallments());
    assertEquals("seller", paymentIntent.getPayment().getInstallmentsCost());
    assertEquals("credit_card", paymentIntent.getPayment().getType());
  }

  private PointPaymentIntentRequest newPaymentIntentRequest() {
    PointPaymentIntentPaymentRequest payment =
        PointPaymentIntentPaymentRequest.builder()
            .installments(1)
            .type("credit_card")
            .installmentsCost("seller")
            .voucherType(null)
            .build();

    PointPaymentIntentAdditionalInfoRequest additionalInfo =
        PointPaymentIntentAdditionalInfoRequest.builder()
            .externalReference("4561ads-das4das4-das4754-das456")
            .printOnTerminal(true)
            .build();

    return PointPaymentIntentRequest.builder()
        .amount(new BigDecimal(1500))
        .description("your payment intent description")
        .payment(payment)
        .additionalInfo(additionalInfo)
        .build();
  }
}
