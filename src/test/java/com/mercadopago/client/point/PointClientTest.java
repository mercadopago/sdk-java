package com.mercadopago.client.point;

import static com.mercadopago.client.point.OperatingMode.PDV;
import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** PointClientTest class. */
class PointClientTest extends BaseClientTest {
  private final String paymentIntentJson = "point/payment_intent.json";

  private final String paymentIntentListJson = "point/payment_intent_list.json";

  private final String paymentIntentDeleteJson = "point/payment_intent_delete.json";

  private final String paymentIntentSearchJson = "point/payment_intent_search.json";

  private final String paymentIntentStatusJson = "point/payment_intent_status.json";

  private final String devicesListJson = "point/devices_list.json";

  private final String devicesOperatingModeJson = "point/devices_operating_mode.json";

  private final String deviceId = "GERTEC_MP35P__8701012051267123";

  private final String paymentIntentId = "afa5ffb4-9094-43de-8192-fb951e96ee95";

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

  @Test
  void getPaymentIntentListSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointPaymentIntentList paymentIntentList =
        client.getPaymentIntentList(newPaymentIntentListRequest());

    assertNotNull(paymentIntentList.getResponse());
    assertEquals(OK, paymentIntentList.getResponse().getStatusCode());
    assertPaymentIntentListFields(paymentIntentList);
  }

  @Test
  void getPaymentIntentListWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointPaymentIntentList paymentIntentList =
        client.getPaymentIntentList(newPaymentIntentListRequest(), buildRequestOptions());

    assertNotNull(paymentIntentList.getResponse());
    assertEquals(OK, paymentIntentList.getResponse().getStatusCode());
    assertPaymentIntentListFields(paymentIntentList);
  }

  @Test
  void cancelPaymentIntentSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentDeleteJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointCancelPaymentIntent cancelPaymentIntent =
        client.cancelPaymentIntent(deviceId, paymentIntentId);

    assertNotNull(cancelPaymentIntent.getResponse());
    assertEquals(OK, cancelPaymentIntent.getResponse().getStatusCode());
    assertEquals(paymentIntentId, cancelPaymentIntent.getId());
  }

  @Test
  void cancelPaymentIntentWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentDeleteJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointCancelPaymentIntent cancelPaymentIntent =
        client.cancelPaymentIntent(deviceId, paymentIntentId, buildRequestOptions());

    assertNotNull(cancelPaymentIntent.getResponse());
    assertEquals(OK, cancelPaymentIntent.getResponse().getStatusCode());
    assertEquals(paymentIntentId, cancelPaymentIntent.getId());
  }

  @Test
  void searchPaymentIntentSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointSearchPaymentIntent searchPaymentIntent = client.searchPaymentIntent(paymentIntentId);

    assertNotNull(searchPaymentIntent.getResponse());
    assertEquals(OK, searchPaymentIntent.getResponse().getStatusCode());
    assertSearchPaymentIntentFields(searchPaymentIntent);
  }

  @Test
  void searchPaymentIntentWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointSearchPaymentIntent searchPaymentIntent =
        client.searchPaymentIntent(paymentIntentId, buildRequestOptions());

    assertNotNull(searchPaymentIntent.getResponse());
    assertEquals(OK, searchPaymentIntent.getResponse().getStatusCode());
    assertSearchPaymentIntentFields(searchPaymentIntent);
  }

  @Test
  void getPaymentIntentStatusSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentStatusJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointStatusPaymentIntent paymentIntentStatus = client.getPaymentIntentStatus(paymentIntentId);

    assertNotNull(paymentIntentStatus.getResponse());
    assertEquals(OK, paymentIntentStatus.getResponse().getStatusCode());
    assertEquals("CANCELED", paymentIntentStatus.getStatus());
    assertEquals(
        OffsetDateTime.of(2022, 6, 27, 10, 10, 10, 0, ZoneOffset.UTC),
        paymentIntentStatus.getCreatedOn());
  }

  @Test
  void getPaymentIntentStatusWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentIntentStatusJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointStatusPaymentIntent paymentIntentStatus =
        client.getPaymentIntentStatus(paymentIntentId, buildRequestOptions());

    assertNotNull(paymentIntentStatus.getResponse());
    assertEquals(OK, paymentIntentStatus.getResponse().getStatusCode());
    assertEquals("CANCELED", paymentIntentStatus.getStatus());
    assertEquals(
        OffsetDateTime.of(2022, 6, 27, 10, 10, 10, 0, ZoneOffset.UTC),
        paymentIntentStatus.getCreatedOn());
  }

  @Test
  void getDevicesSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(devicesListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointDevices pointDevices = client.getDevices(newSearchDevicesRequest());

    assertNotNull(pointDevices.getResponse());
    assertEquals(OK, pointDevices.getResponse().getStatusCode());
    assertSearchDevicesFields(pointDevices);
  }

  @Test
  void getDevicesWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(devicesListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointDevices pointDevices = client.getDevices(newSearchDevicesRequest(), buildRequestOptions());

    assertNotNull(pointDevices.getResponse());
    assertEquals(OK, pointDevices.getResponse().getStatusCode());
    assertSearchDevicesFields(pointDevices);
  }

  @Test
  void changeDeviceOperatingModeSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(devicesOperatingModeJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointDeviceOperatingModeRequest request =
        PointDeviceOperatingModeRequest.builder().operatingMode(PDV).build();

    PointDeviceOperatingMode deviceOperatingMode =
        client.changeDeviceOperatingMode(deviceId, request);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(devicesOperatingModeJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(deviceOperatingMode.getResponse());
    assertEquals(OK, deviceOperatingMode.getResponse().getStatusCode());
    assertEquals(PDV, deviceOperatingMode.getOperatingMode());
  }

  @Test
  void changeDeviceOperatingModeWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(devicesOperatingModeJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PointDeviceOperatingModeRequest request =
        PointDeviceOperatingModeRequest.builder().operatingMode(PDV).build();

    PointDeviceOperatingMode deviceOperatingMode =
        client.changeDeviceOperatingMode(deviceId, request, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(devicesOperatingModeJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(deviceOperatingMode.getResponse());
    assertEquals(OK, deviceOperatingMode.getResponse().getStatusCode());
    assertEquals(PDV, deviceOperatingMode.getOperatingMode());
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

  private void assertPaymentIntentListFields(PointPaymentIntentList paymentIntentList) {
    assertFalse(paymentIntentList.getEvents().isEmpty());
    assertEquals(3, paymentIntentList.getEvents().size());

    assertEquals(
        "d2ee3a75-ca25-4bf3-9eca-f5f2d8f23bf4",
        paymentIntentList.getEvents().get(0).getPaymentIntentId());
    assertEquals("ABANDONED", paymentIntentList.getEvents().get(0).getStatus());
    assertEquals(
        OffsetDateTime.of(2022, 1, 24, 10, 10, 10, 0, ZoneOffset.UTC),
        paymentIntentList.getEvents().get(0).getCreatedOn());

    assertEquals(
        "a16aba35-d43c-409d-b6c8-c3020797b061",
        paymentIntentList.getEvents().get(1).getPaymentIntentId());
    assertEquals("CANCELED", paymentIntentList.getEvents().get(1).getStatus());
    assertEquals(
        OffsetDateTime.of(2022, 1, 25, 10, 10, 10, 0, ZoneOffset.UTC),
        paymentIntentList.getEvents().get(1).getCreatedOn());

    assertEquals(
        "68bf6839-ddb3-4825-8f4c-7eb26e68a5c3",
        paymentIntentList.getEvents().get(2).getPaymentIntentId());
    assertEquals("ABANDONED", paymentIntentList.getEvents().get(2).getStatus());
    assertEquals(
        OffsetDateTime.of(2022, 1, 26, 10, 10, 10, 0, ZoneOffset.UTC),
        paymentIntentList.getEvents().get(2).getCreatedOn());
  }

  private void assertSearchPaymentIntentFields(PointSearchPaymentIntent searchPaymentIntent) {
    assertNotNull(searchPaymentIntent.getAdditionalInfo());
    assertEquals(
        "4561ads-das4das4-das4754-das456",
        searchPaymentIntent.getAdditionalInfo().getExternalReference());
    assertTrue(searchPaymentIntent.getAdditionalInfo().getPrintOnTerminal());
    assertEquals(new BigDecimal("1500"), searchPaymentIntent.getAmount());
    assertEquals("your payment intent description", searchPaymentIntent.getDescription());
    assertEquals("GERTEC_MP35P__8701012051267123", searchPaymentIntent.getDeviceId());
    assertEquals("afa5ffb4-9094-43de-8192-fb951e96ee95", searchPaymentIntent.getId());
    assertNotNull(searchPaymentIntent.getPayment());
    assertEquals(1, searchPaymentIntent.getPayment().getInstallments());
    assertEquals("seller", searchPaymentIntent.getPayment().getInstallmentsCost());
    assertEquals("credit_card", searchPaymentIntent.getPayment().getType());
    assertEquals("OPEN", searchPaymentIntent.getState());
  }

  private void assertSearchDevicesFields(PointDevices pointDevices) {
    assertEquals(1, pointDevices.getDevices().size());
    assertEquals("GERTEC_MP35P__8701012051267123", pointDevices.getDevices().get(0).getId());
    assertEquals("PDV", pointDevices.getDevices().get(0).getOperatingMode());
    assertNotNull(pointDevices.getPaging());
    assertEquals(1, pointDevices.getPaging().getTotal());
    assertEquals(50, pointDevices.getPaging().getLimit());
    assertEquals(0, pointDevices.getPaging().getOffset());
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

  private PointPaymentIntentListRequest newPaymentIntentListRequest() {
    LocalDate startDate = LocalDate.of(2022, 1, 1);
    LocalDate endDate = LocalDate.of(2022, 1, 30);
    return PointPaymentIntentListRequest.builder().startDate(startDate).endDate(endDate).build();
  }

  private MPSearchRequest newSearchDevicesRequest() {
    Map<String, Object> filters = new HashMap<>();
    filters.put("store_id", "9999999");
    filters.put("posId", 8888888);
    return MPSearchRequest.builder().filters(filters).limit(50).offset(0).build();
  }
}
