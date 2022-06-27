package com.mercadopago.client.point;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.point.PointPaymentIntent;
import com.mercadopago.resources.point.PointPaymentIntentDelete;
import com.mercadopago.resources.point.PointPaymentIntentList;
import java.math.BigDecimal;
import java.time.LocalDate;
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

  @Test
  void getPaymentIntentListSuccess() {
    try {
      PointPaymentIntentList paymentIntentList =
          client.getPaymentIntentList(newPaymentIntentListRequest());

      assertNotNull(paymentIntentList.getResponse());
      assertEquals(OK, paymentIntentList.getResponse().getStatusCode());
      assertNotNull(paymentIntentList.getEvents());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getPaymentIntentListWithRequestOptionsSuccess() {
    try {
      PointPaymentIntentList paymentIntentList =
          client.getPaymentIntentList(newPaymentIntentListRequest(), buildRequestOptions());

      assertNotNull(paymentIntentList.getResponse());
      assertEquals(OK, paymentIntentList.getResponse().getStatusCode());
      assertNotNull(paymentIntentList.getEvents());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void deletePaymentIntentSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointPaymentIntentDelete paymentIntentDelete =
          client.deletePaymentIntent(deviceId, paymentIntent.getId());

      assertNotNull(paymentIntentDelete.getResponse());
      assertEquals(OK, paymentIntentDelete.getResponse().getStatusCode());
      assertEquals(paymentIntent.getId(), paymentIntentDelete.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void deletePaymentIntentWithRequestOptionsSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointPaymentIntentDelete paymentIntentDelete =
          client.deletePaymentIntent(deviceId, paymentIntent.getId(), buildRequestOptions());

      assertNotNull(paymentIntentDelete.getResponse());
      assertEquals(OK, paymentIntentDelete.getResponse().getStatusCode());
      assertEquals(paymentIntent.getId(), paymentIntentDelete.getId());
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

  private PointPaymentIntentListRequest newPaymentIntentListRequest() {
    LocalDate startDate = LocalDate.of(2022, 1, 1);
    LocalDate endDate = LocalDate.of(2022, 1, 30);
    return PointPaymentIntentListRequest.builder().startDate(startDate).endDate(endDate).build();
  }
}
