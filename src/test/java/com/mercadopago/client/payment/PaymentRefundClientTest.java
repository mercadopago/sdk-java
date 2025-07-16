package com.mercadopago.client.payment;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.payment.PaymentRefund;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class PaymentRefundClientTest extends BaseClientTest {
  private final Long paymentTestId = 17014025134L;

  private final Long refundTestId = 1245678203L;

  private final String refundBaseJson = "refund/refund_base.json";

  private final String refundListJson = "refund/refund_list.json";

  private final String refundPartialJson = "refund/refund_partial.json";

  private final OffsetDateTime date = OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final PaymentRefundClient client = new PaymentRefundClient();

  @Test
  void refundSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.refund(paymentTestId);

    assertRefundFields(result);
  }

  @Test
  public void refundWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.refund(paymentTestId, buildRequestOptions());

    assertRefundFields(result);
  }

  @Test
  void refundPartialSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    BigDecimal amount = new BigDecimal("50");
    PaymentRefund result = client.refund(paymentTestId, amount);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(refundPartialJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertRefundFields(result);
  }

  @Test
  public void refundPartialWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    BigDecimal amount = new BigDecimal("50");
    PaymentRefund result = client.refund(paymentTestId, amount, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(refundPartialJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertRefundFields(result);
  }

  @Test
  public void getRefundSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.get(paymentTestId, refundTestId);
    assertRefundFields(result);
  }

  @Test
  public void getRefundWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.get(paymentTestId, refundTestId, buildRequestOptions());
    assertRefundFields(result);
  }

  @Test
  public void listRefundsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<PaymentRefund> result = client.list(paymentTestId);
    assertEquals(OK, result.getResponse().getStatusCode());
    assertNotNull(result.getResponse());
    assertEquals(2, result.getResults().size());
    assertRefundFields(result.getResults().get(0));
  }

  @Test
  public void listRefundsWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<PaymentRefund> result = client.list(paymentTestId, buildRequestOptions());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertNotNull(result.getResponse());
    assertEquals(2, result.getResults().size());
    assertRefundFields(result.getResults().get(0));
  }

  private void assertRefundFields(PaymentRefund refund) {
    assertEquals(refundTestId, refund.getId());
    assertEquals(paymentTestId, refund.getPaymentId());
    assertEquals(new BigDecimal("50"), refund.getAmount());
    assertEquals("823549964", refund.getSource().getId());
    assertEquals("Mullins Hillary", refund.getSource().getName());
    assertEquals("collector", refund.getSource().getType());
    assertEquals(date, refund.getDateCreated());
    assertNull(refund.getUniqueSequenceNumber());
    assertEquals("standard", refund.getRefundMode());
    assertEquals(BigDecimal.ZERO, refund.getAdjustmentAmount());
    assertEquals("approved", refund.getStatus());
    assertNull(refund.getReason());
  }
}
