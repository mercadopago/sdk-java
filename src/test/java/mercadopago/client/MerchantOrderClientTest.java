package mercadopago.client;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static java.util.Calendar.JANUARY;
import static mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static mercadopago.helper.MockHelper.generateJsonElement;
import static mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.merchantorder.MerchantOrderCreateRequest;
import com.mercadopago.client.merchantorder.MerchantOrderPayerRequest;
import com.mercadopago.client.merchantorder.MerchantOrderUpdateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import mercadopago.BaseClientTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class MerchantOrderClientTest extends BaseClientTest {

  private static final int YEAR = 2021;

  private static final int TEN = 10;

  private static final int DEFAULT_TIMEOUT = 1000;

  private static final String ORDER_BASE_JSON = "merchant/order_base.json";

  private static final String ORDER_UPDATED_JSON = "merchant/order_updated.json";

  private static final String ORDER_SEARCH_JSON = "merchant/order_search.json";

  private static final String PREFERENCE_ID = "798798399-13769cb5-b898-448f-8d5a-c939a8cee479";

  private static final long MERCHANT_ORDER_ID = 4018801790L;

  private static final Date DATE = new Date(YEAR, JANUARY, TEN, TEN, TEN, TEN);

  MerchantOrderClient client = new MerchantOrderClient();

  @Test
  void createSuccess() throws MPException, IOException {

    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_BASE_JSON, CREATED);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderCreateRequest request =
        MerchantOrderCreateRequest.builder().preferenceId(PREFERENCE_ID).build();

    MerchantOrder merchantOrder = client.create(request);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(ORDER_BASE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(CREATED, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void createWithRequestOptionsSuccess() throws MPException, IOException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_BASE_JSON, CREATED);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderCreateRequest request =
        MerchantOrderCreateRequest.builder().preferenceId(PREFERENCE_ID).build();

    MerchantOrder merchantOrder = client.create(request, requestOptions);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(ORDER_BASE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(CREATED, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void getSuccess() throws MPException, IOException {

    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrder merchantOrder = client.get(MERCHANT_ORDER_ID);

    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void getWithRequestOptionsSuccess() throws MPException, IOException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrder merchantOrder = client.get(MERCHANT_ORDER_ID, requestOptions);

    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertMerchantOrderFields(merchantOrder);
  }

  @Test
  void updateSuccess() throws MPException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_UPDATED_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderPayerRequest payerRequest =
        MerchantOrderPayerRequest.builder().nickname("Test").build();

    MerchantOrderUpdateRequest updateRequest =
        MerchantOrderUpdateRequest.builder().payer(payerRequest).build();

    MerchantOrder merchantOrder = client.update(MERCHANT_ORDER_ID, updateRequest);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(ORDER_UPDATED_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertEquals("Test", merchantOrder.getPayer().getNickname());
  }

  @Test
  void updateWithRequestOptionsSuccess() throws MPException, IOException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_UPDATED_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MerchantOrderPayerRequest payerRequest =
        MerchantOrderPayerRequest.builder().nickname("Test").build();

    MerchantOrderUpdateRequest updateRequest =
        MerchantOrderUpdateRequest.builder().payer(payerRequest).build();

    MerchantOrder merchantOrder = client.update(MERCHANT_ORDER_ID, updateRequest, requestOptions);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(ORDER_UPDATED_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(merchantOrder.getResponse());
    assertEquals(OK, merchantOrder.getResponse().getStatusCode());
    assertEquals("Test", merchantOrder.getPayer().getNickname());
  }

  @Test
  void searchSuccess() throws MPException, IOException {

    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_SEARCH_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Map<String, Object> filters = new HashMap<>();
    filters.put("preference_id", PREFERENCE_ID);

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
  void searchWithRequestOptionsSuccess() throws MPException, IOException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(ORDER_SEARCH_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Map<String, Object> filters = new HashMap<>();
    filters.put("preference_id", PREFERENCE_ID);

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
    MPElementsResourcesPage<MerchantOrder> results = client.search(searchRequest, requestOptions);

    assertEquals(OK, results.getResponse().getStatusCode());
    assertNotNull(results.getResponse());
    assertEquals(2, results.getTotal());
    assertEquals(2, results.getElements().size());
    assertEquals(2, results.getNextOffset());
    assertMerchantOrderFields(results.getElements().get(0));
  }

  private void assertMerchantOrderFields(MerchantOrder merchantOrder) {
    assertEquals(MERCHANT_ORDER_ID, merchantOrder.getId());
    assertEquals("opened", merchantOrder.getStatus());
    assertNull(merchantOrder.getExternalReference());
    assertEquals(PREFERENCE_ID, merchantOrder.getPreferenceId());
    assertTrue(merchantOrder.getPayments().isEmpty());
    assertTrue(merchantOrder.getShipments().isEmpty());
    assertTrue(merchantOrder.getPayments().isEmpty());
    assertEquals(798798399L, merchantOrder.getCollector().getId());
    assertEquals("TETE8419469", merchantOrder.getCollector().getNickname());
    assertTrue(merchantOrder.getMarketplace().isEmpty());
    assertNull(merchantOrder.getNotificationUrl());
    assertEquals(DATE, merchantOrder.getDateCreated());
    assertEquals(DATE, merchantOrder.getLastUpdated());
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
