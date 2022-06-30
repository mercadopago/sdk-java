package com.mercadopago.client.point;

import static com.mercadopago.client.point.OperatingMode.PDV;
import static com.mercadopago.client.point.OperatingMode.STANDALONE;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.point.PointCancelPaymentIntent;
import com.mercadopago.resources.point.PointDeviceOperatingMode;
import com.mercadopago.resources.point.PointDevices;
import com.mercadopago.resources.point.PointPaymentIntent;
import com.mercadopago.resources.point.PointPaymentIntentList;
import com.mercadopago.resources.point.PointSearchPaymentIntent;
import com.mercadopago.resources.point.PointStatusPaymentIntent;
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
  void cancelPaymentIntentSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointCancelPaymentIntent cancelPaymentIntent =
          client.cancelPaymentIntent(deviceId, paymentIntent.getId());

      assertNotNull(cancelPaymentIntent.getResponse());
      assertEquals(OK, cancelPaymentIntent.getResponse().getStatusCode());
      assertEquals(paymentIntent.getId(), cancelPaymentIntent.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void cancelPaymentIntentWithRequestOptionsSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointCancelPaymentIntent cancelPaymentIntent =
          client.cancelPaymentIntent(deviceId, paymentIntent.getId(), buildRequestOptions());

      assertNotNull(cancelPaymentIntent.getResponse());
      assertEquals(OK, cancelPaymentIntent.getResponse().getStatusCode());
      assertEquals(paymentIntent.getId(), cancelPaymentIntent.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchPaymentIntentSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointSearchPaymentIntent searchPaymentIntent =
          client.searchPaymentIntent(paymentIntent.getId());

      assertNotNull(searchPaymentIntent.getResponse());
      assertEquals(OK, searchPaymentIntent.getResponse().getStatusCode());
      assertEquals(paymentIntent.getId(), searchPaymentIntent.getId());
      assertNotNull(searchPaymentIntent.getState());
      client.cancelPaymentIntent(deviceId, paymentIntent.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchPaymentIntentWithRequestOptionsSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointSearchPaymentIntent searchPaymentIntent =
          client.searchPaymentIntent(paymentIntent.getId(), buildRequestOptions());

      assertNotNull(searchPaymentIntent.getResponse());
      assertEquals(OK, searchPaymentIntent.getResponse().getStatusCode());
      assertEquals(paymentIntent.getId(), searchPaymentIntent.getId());
      assertNotNull(searchPaymentIntent.getState());
      client.cancelPaymentIntent(deviceId, paymentIntent.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getPaymentIntentStatusSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointStatusPaymentIntent paymentIntentStatus =
          client.getPaymentIntentStatus(paymentIntent.getId());

      assertNotNull(paymentIntentStatus.getResponse());
      assertEquals(OK, paymentIntentStatus.getResponse().getStatusCode());
      assertEquals("OPEN", paymentIntentStatus.getStatus());
      assertNotNull(paymentIntentStatus.getCreatedOn());
      client.cancelPaymentIntent(deviceId, paymentIntent.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getPaymentIntentStatusWithRequestOptionsSuccess() {
    try {
      PointPaymentIntentRequest request = buildPaymentIntentRequest();
      PointPaymentIntent paymentIntent = client.createPaymentIntent(deviceId, request);
      PointStatusPaymentIntent paymentIntentStatus =
          client.getPaymentIntentStatus(paymentIntent.getId(), buildRequestOptions());

      assertNotNull(paymentIntentStatus.getResponse());
      assertEquals(OK, paymentIntentStatus.getResponse().getStatusCode());
      assertEquals("OPEN", paymentIntentStatus.getStatus());
      assertNotNull(paymentIntentStatus.getCreatedOn());
      client.cancelPaymentIntent(deviceId, paymentIntent.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getDevicesSuccess() {
    try {
      PointDevices pointDevices = client.getDevices(newSearchDevicesRequest());

      assertNotNull(pointDevices.getResponse());
      assertEquals(OK, pointDevices.getResponse().getStatusCode());
      assertNotNull(pointDevices.getDevices());
      assertNotNull(pointDevices.getPaging());
      assertEquals(50, pointDevices.getPaging().getLimit());
      assertEquals(0, pointDevices.getPaging().getOffset());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getDevicesWithRequestOptionsSuccess() {
    try {
      PointDevices pointDevices =
          client.getDevices(newSearchDevicesRequest(), buildRequestOptions());

      assertNotNull(pointDevices.getResponse());
      assertEquals(OK, pointDevices.getResponse().getStatusCode());
      assertNotNull(pointDevices.getDevices());
      assertNotNull(pointDevices.getPaging());
      assertEquals(50, pointDevices.getPaging().getLimit());
      assertEquals(0, pointDevices.getPaging().getOffset());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void changeDeviceOperatingModeSuccess() {
    try {
      PointDeviceOperatingModeRequest request =
          PointDeviceOperatingModeRequest.builder().operatingMode(STANDALONE).build();

      PointDeviceOperatingMode deviceOperatingMode =
          client.changeDeviceOperatingMode(deviceId, request);

      assertNotNull(deviceOperatingMode.getResponse());
      assertEquals(OK, deviceOperatingMode.getResponse().getStatusCode());
      assertEquals(STANDALONE, deviceOperatingMode.getOperatingMode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void changeDeviceOperatingModeWithRequestOptionsSuccess() {
    try {
      PointDeviceOperatingModeRequest request =
          PointDeviceOperatingModeRequest.builder().operatingMode(PDV).build();

      PointDeviceOperatingMode deviceOperatingMode =
          client.changeDeviceOperatingMode(deviceId, request, buildRequestOptions());

      assertNotNull(deviceOperatingMode.getResponse());
      assertEquals(OK, deviceOperatingMode.getResponse().getStatusCode());
      assertEquals(PDV, deviceOperatingMode.getOperatingMode());
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

  private MPSearchRequest newSearchDevicesRequest() {
    return MPSearchRequest.builder().limit(50).offset(0).build();
  }
}
