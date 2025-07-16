package com.mercadopago.client.merchantorder;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class MerchantOrderClientTest extends BaseClientTest {
  private final String orderBaseJson = "merchant/order_base.json";

  private final String orderUpdatedJson = "merchant/order_updated.json";

  private final String orderSearchJson = "merchant/order_search.json";

  private final String preferenceId = "798798399-13769cb5-b898-448f-8d5a-c939a8cee479";

  private final long merchantOrderId = 4018801790L;

  private final OffsetDateTime date = OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final MerchantOrderClient client = new MerchantOrderClient();

  @Test
  void createSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderCreateRequest request =
        MerchantOrderCreateRequest.builder().preferenceId(preferenceId).build();

    MerchantOrder merchantOrder = client.create(request);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(orderBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(CREATED, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void createWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderCreateRequest request =
        MerchantOrderCreateRequest.builder().preferenceId(preferenceId).build();

    MerchantOrder merchantOrder = client.create(request, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(orderBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(CREATED, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void getSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrder merchantOrder = client.get(merchantOrderId);

    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void getWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrder merchantOrder = client.get(merchantOrderId, buildRequestOptions());

    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void updateSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderUpdatedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderPayerRequest payerRequest =
        MerchantOrderPayerRequest.builder().id(0L).nickname("Test").build();

    MerchantOrderUpdateRequest updateRequest =
        MerchantOrderUpdateRequest.builder().payer(payerRequest).build();

    MerchantOrder merchantOrder = client.update(merchantOrderId, updateRequest);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(orderUpdatedJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertEquals("Test", merchantOrder.getPayer().getNickname());
  }

  @Test
  void updateWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderUpdatedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderPayerRequest payerRequest =
        MerchantOrderPayerRequest.builder().id(0L).nickname("Test").build();

    MerchantOrderUpdateRequest updateRequest =
        MerchantOrderUpdateRequest.builder().payer(payerRequest).build();

    MerchantOrder merchantOrder =
        client.update(merchantOrderId, updateRequest, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(orderUpdatedJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertEquals("Test", merchantOrder.getPayer().getNickname());
  }

  @Test
  void searchSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Map<String, Object> filters = new HashMap<>();
    filters.put("preference_id", preferenceId);

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
    MPElementsResourcesPage<MerchantOrder> results = client.search(searchRequest);

    assertEquals(OK, results.getResponse().getStatusCode());
    assertNotNull(results.getResponse());
    assertEquals(2, results.getTotal());
    assertEquals(2, results.getElements().size());
    assertEquals(2, results.getNextOffset());
    assertMerchantOrderFields(results.getElements().get(0));
  }

  @Test
  void searchWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(orderSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Map<String, Object> filters = new HashMap<>();
    filters.put("preference_id", preferenceId);

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
    MPElementsResourcesPage<MerchantOrder> results =
        client.search(searchRequest, buildRequestOptions());

    assertEquals(OK, results.getResponse().getStatusCode());
    assertNotNull(results.getResponse());
    assertEquals(2, results.getTotal());
    assertEquals(2, results.getElements().size());
    assertEquals(2, results.getNextOffset());
    assertMerchantOrderFields(results.getElements().get(0));
  }

  private void assertMerchantOrderFields(MerchantOrder merchantOrder) {
    assertEquals(merchantOrderId, merchantOrder.getId());
    assertEquals("opened", merchantOrder.getStatus());
    assertNull(merchantOrder.getExternalReference());
    assertEquals(preferenceId, merchantOrder.getPreferenceId());
    assertTrue(merchantOrder.getPayments().isEmpty());
    assertTrue(merchantOrder.getShipments().isEmpty());
    assertTrue(merchantOrder.getPayments().isEmpty());
    assertEquals(798798399L, merchantOrder.getCollector().getId());
    assertEquals("TETE8419469", merchantOrder.getCollector().getNickname());
    assertTrue(merchantOrder.getMarketplace().isEmpty());
    assertNull(merchantOrder.getNotificationUrl());
    assertEquals(date, merchantOrder.getDateCreated());
    assertEquals(date, merchantOrder.getLastUpdated());
    assertNull(merchantOrder.getSponsorId());
    assertEquals(BigDecimal.ZERO, merchantOrder.getShippingCost());
    assertEquals(new BigDecimal("206.02"), merchantOrder.getTotalAmount());
    assertEquals("MLM", merchantOrder.getSiteId());
    assertEquals(BigDecimal.ZERO, merchantOrder.getPaidAmount());
    assertEquals(BigDecimal.ZERO, merchantOrder.getRefundedAmount());
    assertNull(merchantOrder.getPayer());
    assertTrue(merchantOrder.getItems().isEmpty());
    assertFalse(merchantOrder.isCancelled());
    assertNull(merchantOrder.getAdditionalInfo());
    assertNull(merchantOrder.getApplicationId());
    assertEquals("payment_required", merchantOrder.getOrderStatus());
  }
}
