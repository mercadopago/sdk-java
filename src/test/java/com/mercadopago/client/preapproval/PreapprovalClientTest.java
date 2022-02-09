package com.mercadopago.client.preapproval;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static java.util.Calendar.JANUARY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.preapproval.Preapproval;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class PreapprovalClientTest extends BaseClientTest {

  private static final String PREAPPROVAL_BASE_JSON = "preapproval/preapproval_base.json";

  private static final String PREAPPROVAL_UPDATE_JSON = "preapproval/preapproval_update.json";

  private static final String PREAPPROVAL_LIST_JSON = "preapproval/preapproval_list.json";

  private static final String PREAPPROVAL_ID = "2c9380847e9b451c017ea1bd70ba0219";

  private static final int DEFAULT_TIMEOUT = 1000;

  private static final int START_ADDITIONAL_YEAR = 122;

  private static final int END_ADDITIONAL_YEAR = 123;

  private static final int TEN = 10;

  private static final Date START_DATE =
      new Date(START_ADDITIONAL_YEAR, JANUARY, TEN, TEN, TEN, TEN);

  private static final Date END_DATE = new Date(END_ADDITIONAL_YEAR, JANUARY, TEN, TEN, TEN, TEN);

  PreapprovalClient client = new PreapprovalClient();

  @Test
  void getSuccess() throws IOException, MPException {
    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Preapproval preapproval = client.get(PREAPPROVAL_ID);

    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void getWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions = generateRequestOptions();

    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Preapproval preapproval = client.get(PREAPPROVAL_ID, requestOptions);

    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void createSuccess() throws IOException, MPException {
    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_BASE_JSON, CREATED);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalCreateRequest request = generatePreapprovalRequest();
    Preapproval preapproval = client.create(request);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(PREAPPROVAL_BASE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(CREATED, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void createWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions = generateRequestOptions();

    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_BASE_JSON, CREATED);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalCreateRequest request = generatePreapprovalRequest();
    Preapproval preapproval = client.create(request, requestOptions);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(PREAPPROVAL_BASE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(CREATED, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void updateSuccess() throws MPException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_UPDATE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalUpdateRequest updateRequest =
        PreapprovalUpdateRequest.builder().reason("Updated reason").build();
    Preapproval preapproval = client.update(PREAPPROVAL_ID, updateRequest);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(PREAPPROVAL_UPDATE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, true);
  }

  @Test
  void updateWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions = generateRequestOptions();

    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_UPDATE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalUpdateRequest updateRequest =
        PreapprovalUpdateRequest.builder().reason("Updated reason").build();
    Preapproval preapproval = client.update(PREAPPROVAL_ID, updateRequest, requestOptions);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(PREAPPROVAL_UPDATE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, true);
  }

  @Test
  void searchSuccess() throws IOException, MPException {
    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_LIST_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(2).build();

    MPResultsResourcesPage<Preapproval> preapprovalList = client.search(searchRequest);

    assertNotNull(preapprovalList.getResponse());
    assertEquals(OK, preapprovalList.getResponse().getStatusCode());
    assertEquals(7, preapprovalList.getPaging().getTotal());
    assertEquals(2, preapprovalList.getPaging().getLimit());
    assertEquals(0, preapprovalList.getPaging().getOffset());
    assertEquals(2, preapprovalList.getResults().size());
    assertNotNull(preapprovalList.getResults().get(0));
    assertNotNull(preapprovalList.getResults().get(1));
  }

  @Test
  void searchWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions = generateRequestOptions();

    HttpResponse httpResponse = generateHttpResponseFromFile(PREAPPROVAL_LIST_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(2).build();

    MPResultsResourcesPage<Preapproval> preapprovalList =
        client.search(searchRequest, requestOptions);

    assertNotNull(preapprovalList.getResponse());
    assertEquals(OK, preapprovalList.getResponse().getStatusCode());
    assertEquals(7, preapprovalList.getPaging().getTotal());
    assertEquals(2, preapprovalList.getPaging().getLimit());
    assertEquals(0, preapprovalList.getPaging().getOffset());
    assertEquals(2, preapprovalList.getResults().size());
    assertNotNull(preapprovalList.getResults().get(0));
    assertNotNull(preapprovalList.getResults().get(1));
  }

  private void assertPreapprovalFields(Preapproval preapproval, boolean updated) {
    String reason = updated ? "Updated reason" : "reason";

    assertEquals(PREAPPROVAL_ID, preapproval.getId());
    assertEquals(766790067L, preapproval.getPayerId());
    assertTrue(preapproval.getPayerEmail().isEmpty());
    assertEquals("https://www.mercadopago.com.br", preapproval.getBackUrl());
    assertEquals(823549964L, preapproval.getCollectorId());
    assertEquals(6245132082630004L, preapproval.getApplicationId());
    assertEquals("pending", preapproval.getStatus());
    assertEquals(reason, preapproval.getReason());
    assertEquals("23546246234", preapproval.getExternalReference());
    assertEquals(START_DATE, preapproval.getDateCreated());
    assertEquals(
        "https://www.mercadopago.com.br/subscriptions/checkout?preapproval_id=2c9380847e9b451c017ea1bd70ba0219",
        preapproval.getInitPoint());
    assertNull(preapproval.getPaymentMethodId());
    assertEquals(1, preapproval.getAutoRecurring().getFrequency());
    assertEquals("months", preapproval.getAutoRecurring().getFrequencyType());
    assertEquals(new BigDecimal("10.00"), preapproval.getAutoRecurring().getTransactionAmount());
    assertEquals("BRL", preapproval.getAutoRecurring().getCurrencyId());
    assertEquals(START_DATE, preapproval.getAutoRecurring().getStartDate());
    assertEquals(END_DATE, preapproval.getAutoRecurring().getEndDate());
  }

  private PreapprovalCreateRequest generatePreapprovalRequest() {
    PreApprovalAutoRecurringCreateRequest autoRecurring =
        PreApprovalAutoRecurringCreateRequest.builder()
            .transactionAmount(new BigDecimal("10"))
            .frequency(1)
            .frequencyType("months")
            .currencyId("BRL")
            .startDate(START_DATE)
            .endDate(END_DATE)
            .build();

    return PreapprovalCreateRequest.builder()
        .backUrl("https://www.mercadopago.com.br")
        .externalReference("23546246234")
        .reason("reason")
        .payerEmail("test_user_28355466@testuser.com")
        .autoRecurring(autoRecurring)
        .build();
  }

  private MPRequestOptions generateRequestOptions() {
    return MPRequestOptions.builder()
        .accessToken("abc")
        .connectionTimeout(DEFAULT_TIMEOUT)
        .connectionRequestTimeout(DEFAULT_TIMEOUT)
        .socketTimeout(DEFAULT_TIMEOUT)
        .build();
  }
}
