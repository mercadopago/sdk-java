package com.mercadopago.client.preapproval;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.preapproval.Preapproval;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class PreapprovalClientTest extends BaseClientTest {
  private final String preapprovalBaseJson = "preapproval/preapproval_base.json";

  private final String preapprovalUpdateJson = "preapproval/preapproval_update.json";

  private final String preapprovalListJson = "preapproval/preapproval_list.json";

  private final String preapprovalId = "2c9380847e9b451c017ea1bd70ba0219";

  private final OffsetDateTime startDate =
      OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final OffsetDateTime endDate =
      OffsetDateTime.of(2023, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final OffsetDateTime nextPaymentDate =
      OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final PreapprovalClient client = new PreapprovalClient();

  @Test
  void getSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Preapproval preapproval = client.get(preapprovalId);

    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void getWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Preapproval preapproval = client.get(preapprovalId, buildRequestOptions());

    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void createSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalCreateRequest request = buildPreapprovalCreateRequest();
    Preapproval preapproval = client.create(request);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preapprovalBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(CREATED, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void createWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalCreateRequest request = buildPreapprovalCreateRequest();
    Preapproval preapproval = client.create(request, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preapprovalBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(CREATED, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, false);
  }

  @Test
  void updateSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalUpdateJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalUpdateRequest updateRequest =
        PreapprovalUpdateRequest.builder().reason("Updated reason").build();
    Preapproval preapproval = client.update(preapprovalId, updateRequest);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preapprovalUpdateJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, true);
  }

  @Test
  void updateWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalUpdateJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreapprovalUpdateRequest updateRequest =
        PreapprovalUpdateRequest.builder().reason("Updated reason").build();
    Preapproval preapproval = client.update(preapprovalId, updateRequest, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preapprovalUpdateJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preapproval.getResponse());
    assertEquals(OK, preapproval.getResponse().getStatusCode());
    assertPreapprovalFields(preapproval, true);
  }

  @Test
  void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
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
  void searchWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preapprovalListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(2).build();

    MPResultsResourcesPage<Preapproval> preapprovalList =
        client.search(searchRequest, buildRequestOptions());

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

    assertEquals(preapprovalId, preapproval.getId());
    assertEquals(766790067L, preapproval.getPayerId());
    assertTrue(preapproval.getPayerEmail().isEmpty());
    assertEquals("https://www.mercadopago.com.br", preapproval.getBackUrl());
    assertEquals(823549964L, preapproval.getCollectorId());
    assertEquals(6245132082630004L, preapproval.getApplicationId());
    assertEquals("pending", preapproval.getStatus());
    assertEquals(reason, preapproval.getReason());
    assertEquals("23546246234", preapproval.getExternalReference());
    assertEquals(startDate, preapproval.getDateCreated());
    assertEquals(nextPaymentDate, preapproval.getNextPaymentDate());
    assertEquals(
        "https://www.mercadopago.com.br/subscriptions/checkout?preapproval_id=2c9380847e9b451c017ea1bd70ba0219",
        preapproval.getInitPoint());
    assertNull(preapproval.getPaymentMethodId());
    assertEquals(1, preapproval.getAutoRecurring().getFrequency());
    assertEquals("months", preapproval.getAutoRecurring().getFrequencyType());
    assertEquals(new BigDecimal("10.00"), preapproval.getAutoRecurring().getTransactionAmount());
    assertEquals("BRL", preapproval.getAutoRecurring().getCurrencyId());
    assertEquals(startDate, preapproval.getAutoRecurring().getStartDate());
    assertEquals(endDate, preapproval.getAutoRecurring().getEndDate());
  }

  private PreapprovalCreateRequest buildPreapprovalCreateRequest() {
    PreApprovalAutoRecurringCreateRequest autoRecurring =
        PreApprovalAutoRecurringCreateRequest.builder()
            .transactionAmount(BigDecimal.TEN)
            .frequency(1)
            .frequencyType("months")
            .currencyId("BRL")
            .startDate(startDate)
            .endDate(endDate)
            .build();

    return PreapprovalCreateRequest.builder()
        .backUrl("https://www.mercadopago.com.br")
        .externalReference("23546246234")
        .reason("reason")
        .payerEmail("test_user_28355466@testuser.com")
        .autoRecurring(autoRecurring)
        .build();
  }
}
