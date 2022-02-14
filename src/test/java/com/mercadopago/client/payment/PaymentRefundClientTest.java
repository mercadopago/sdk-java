package com.mercadopago.client.payment;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
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

  private static final Long PAYMENT_TEST_ID = 17014025134L;

  private static final Long REFUND_TEST_ID = 1245678203L;

  private static final String REFUND_BASE_JSON = "refund/refund_base.json";

  private static final String REFUND_LIST_JSON = "refund/refund_list.json";

  private static final String REFUND_PARTIAL_JSON = "refund/refund_partial.json";

  private static final OffsetDateTime DATE =
      OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final PaymentRefundClient client = new PaymentRefundClient();

  @Test
  void refundSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(REFUND_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    BigDecimal amount = new BigDecimal("50");
    PaymentRefund result = client.refund(PAYMENT_TEST_ID, amount);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(REFUND_PARTIAL_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertRefundFields(result);
  }

  @Test
  public void refundPartialWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(REFUND_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    BigDecimal amount = new BigDecimal("50");
    PaymentRefund result = client.refund(PAYMENT_TEST_ID, amount, requestOptions);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(REFUND_PARTIAL_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertRefundFields(result);
  }

  @Test
  public void getRefundSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(REFUND_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.get(PAYMENT_TEST_ID, REFUND_TEST_ID);
    assertRefundFields(result);
  }

  @Test
  public void getRefundWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(REFUND_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.get(PAYMENT_TEST_ID, REFUND_TEST_ID, requestOptions);
    assertRefundFields(result);
  }

  @Test
  public void listRefundsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(REFUND_LIST_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<PaymentRefund> result = client.list(PAYMENT_TEST_ID);
    assertEquals(OK, result.getResponse().getStatusCode());
    assertNotNull(result.getResponse());
    assertEquals(2, result.getResults().size());
    assertRefundFields(result.getResults().get(0));
  }

  @Test
  public void listRefundsWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(REFUND_LIST_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<PaymentRefund> result = client.list(PAYMENT_TEST_ID, requestOptions);
    assertEquals(OK, result.getResponse().getStatusCode());
    assertNotNull(result.getResponse());
    assertEquals(2, result.getResults().size());
    assertRefundFields(result.getResults().get(0));
  }

  private void assertRefundFields(PaymentRefund refund) {
    assertEquals(REFUND_TEST_ID, refund.getId());
    assertEquals(PAYMENT_TEST_ID, refund.getPaymentId());
    assertEquals(new BigDecimal("50"), refund.getAmount());
    assertEquals("823549964", refund.getSource().getId());
    assertEquals("Mullins Hillary", refund.getSource().getName());
    assertEquals("collector", refund.getSource().getType());
    assertEquals(DATE, refund.getDateCreated());
    assertNull(refund.getUniqueSequenceNumber());
    assertEquals("standard", refund.getRefundMode());
    assertEquals(BigDecimal.ZERO, refund.getAdjustmentAmount());
    assertEquals("approved", refund.getStatus());
    assertNull(refund.getReason());
  }
}
