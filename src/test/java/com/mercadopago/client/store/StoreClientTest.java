package com.mercadopago.client.store;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class StoreClientTest extends BaseClientTest {
  private final String storeBaseJson = "store/store_base.json";

  private final String storeUpdatedJson = "store/store_update.json";

  private final String storeListJson = "store/store_list.json";

  private final long userId = 471763365L;

  private final String storeId = "784821936";

  private final StoreClient client = new StoreClient();

  @Test
  void createSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreCreateRequest request =
        StoreCreateRequest.builder()
            .name("Official Store")
            .businessHours(
                StoreBusinessHoursRequest.builder()
                    .monday("08:00-18:00")
                    .tuesday("08:00-18:00")
                    .build())
            .location(
                StoreLocationRequest.builder()
                    .addressLine("Av. das Nações Unidas 3003")
                    .zipCode("06233200")
                    .build())
            .build();

    Store store = client.createStore(userId, request);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(CREATED, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  void createWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreCreateRequest request =
        StoreCreateRequest.builder().name("Official store").build();

    Store store = client.createStore(userId, request, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(CREATED, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  void getSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.get(storeId);

    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  void getWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.get(storeId, buildRequestOptions());

    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  void searchSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Map<String, Object> filters = new HashMap<>();
    filters.put("external_id", "1234");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
    MPElementsResourcesPage<Store> results = client.search(userId, searchRequest);

    assertEquals(OK, results.getResponse().getStatusCode());
    assertNotNull(results.getResponse());
    assertEquals(2, results.getTotal());
    assertEquals(2, results.getElements().size());
    assertEquals(2, results.getNextOffset());
    assertStoreFields(results.getElements().get(0));
  }

  @Test
  void searchWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Map<String, Object> filters = new HashMap<>();
    filters.put("external_id", "1234");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
    MPElementsResourcesPage<Store> results =
        client.search(userId, searchRequest, buildRequestOptions());

    assertEquals(OK, results.getResponse().getStatusCode());
    assertNotNull(results.getResponse());
    assertEquals(2, results.getTotal());
    assertEquals(2, results.getElements().size());
    assertEquals(2, results.getNextOffset());
    assertStoreFields(results.getElements().get(0));
  }

  @Test
  void updateSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeUpdatedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreUpdateRequest updateRequest =
        StoreUpdateRequest.builder().name("Updated store").build();

    Store store = client.update(userId, storeId, updateRequest);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeUpdatedJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertEquals("Updated store", store.getName());
  }

  @Test
  void updateWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeUpdatedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreUpdateRequest updateRequest =
        StoreUpdateRequest.builder().name("Updated store").build();

    Store store = client.update(userId, storeId, updateRequest, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeUpdatedJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertEquals("Updated store", store.getName());
  }

  @Test
  void deleteSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.delete(userId, storeId);

    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  void deleteWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.delete(userId, storeId, buildRequestOptions());

    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  private void assertStoreFields(Store store) {
    assertEquals(Long.valueOf(storeId), store.getId());
    assertEquals("Official store", store.getName());
    assertEquals("1234", store.getExternalId());
    assertEquals(userId, store.getUserId());
    assertNotNull(store.getLocation());
    assertEquals("Av. das Nações Unidas 3003", store.getLocation().getAddressLine());
    assertEquals("06233200", store.getLocation().getZipCode());
    assertTrue(store.getBusinessHours().getMonday().isEmpty());
  }
}
