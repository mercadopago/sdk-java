package com.mercadopago.client.point;

import static com.mercadopago.net.HttpStatus.CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.point.PointPaymentIntent;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** PointClientIT class. */
class PointClientIT extends BaseClientIT {
  private final String deviceId = "GERTEC_MP35P__8701012051267123";

  private final PointClient client = new PointClient();

  @Test
  void createPaymentIntentSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();

      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);

      assertNotNull(paymentIntent.getResponse());
      assertEquals(CREATED, paymentIntent.getResponse().getStatusCode());
      assertPaymentIntentFields(paymentIntent);
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void createPaymentIntentWithRequestOptionsSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();

      PointPaymentIntent paymentIntent =
          client.createPaymentIntent(deviceId, request, buildRequestOptions());

      assertNotNull(paymentIntent.getResponse());
      assertEquals(CREATED, paymentIntent.getResponse().getStatusCode());
      assertPaymentIntentFields(paymentIntent);
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  private void assertPaymentIntentFields(PointPaymentIntent paymentIntent) {
    assertNotNull(paymentIntent.getId());
    assertNotNull(paymentIntent.getAdditionalInfo());
    assertNotNull(paymentIntent.getAdditionalInfo().getExternalReference());
    assertTrue(paymentIntent.getAdditionalInfo().getPrintOnTerminal());
    assertEquals(new BigDecimal("1500"), paymentIntent.getAmount());
    assertEquals("your payment intent description", paymentIntent.getDescription());
    assertEquals("GERTEC_MP35P__8701012051267123", paymentIntent.getDeviceId());
    assertNotNull(paymentIntent.getPayment());
    assertEquals(1, paymentIntent.getPayment().getInstallments());
    assertEquals("seller", paymentIntent.getPayment().getInstallmentsCost());
    assertEquals("credit_card", paymentIntent.getPayment().getType());
  }

  private PointPaymentIntentRequest buildPaymentIntentRequest() {

    PointPaymentIntentPaymentRequest payment =
        PointPaymentIntentPaymentRequest.builder()
            .installments(1)
            .type("credit_card")
            .installmentsCost("seller")
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
