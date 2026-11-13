package com.mercadopago.client.store;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/** StoreClientTest class. */
public class StoreClientTest extends BaseClientTest {
  private final Long userId = 480593326L;

  private final Long storeId = 123456L;

  private final String storeBaseJson = "store/store_base.json";

  private final String storeUpdateJson = "store/store_update.json";

  private final String storeSearchJson = "store/store_search.json";

  private final OffsetDateTime date = OffsetDateTime.of(2021, 8, 24, 10, 10, 10, 0, ZoneOffset.ofHours(-4));

  private final StoreClient client = new StoreClient();

  @Test
  public void createSuccess() throws IOException, MPException, MPApiException {
    StoreCreateRequest createRequest = StoreCreateRequest.builder().name("Store Test").build();

    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.create(userId, createRequest);

    assertRequest("POST", "/users/" + userId + "/stores");
    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(CREATED, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  public void createWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    StoreCreateRequest createRequest = StoreCreateRequest.builder().name("Store Test").build();

    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPRequestOptions requestOptions = buildRequestOptions();
    Store store = client.create(userId, createRequest, requestOptions);

    assertRequest("POST", "/users/" + userId + "/stores");
    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(CREATED, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  public void getStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.get(storeId);

    assertRequest("GET", "/stores/" + storeId);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  public void getStoreWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.get(storeId, buildRequestOptions());

    assertRequest("GET", "/stores/" + storeId);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  public void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(10).offset(0).build();
    MPResultsResourcesPage<Store> result = client.search(userId, searchRequest);

    assertRequest("GET", "/users/" + userId + "/stores/search");
    assertNotNull(result.getResponse().getContent());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(10, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
    assertStoreFields(result.getResults().get(0));
  }

  @Test
  public void searchWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(10).offset(0).build();
    MPResultsResourcesPage<Store> result =
        client.search(userId, searchRequest, buildRequestOptions());

    assertRequest("GET", "/users/" + userId + "/stores/search");
    assertNotNull(result.getResponse().getContent());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(10, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
    assertStoreFields(result.getResults().get(0));
  }

  @Test
  public void updateSuccess() throws IOException, MPException, MPApiException {
    StoreUpdateRequest updateRequest = StoreUpdateRequest.builder().name("Updated Store").build();

    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.update(userId, storeId, updateRequest);

    assertRequest("PUT", "/users/" + userId + "/stores/" + storeId);
    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeUpdateJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  public void updateWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    StoreUpdateRequest updateRequest = StoreUpdateRequest.builder().name("Updated Store").build();

    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.update(userId, storeId, updateRequest, buildRequestOptions());

    assertRequest("PUT", "/users/" + userId + "/stores/" + storeId);
    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(storeUpdateJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
    assertStoreFields(store);
  }

  @Test
  public void deleteSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.delete(userId, storeId);

    assertRequest("DELETE", "/users/" + userId + "/stores/" + storeId);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
  }

  @Test
  public void deleteWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(storeBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = client.delete(userId, storeId, buildRequestOptions());

    assertRequest("DELETE", "/users/" + userId + "/stores/" + storeId);
    assertNotNull(store.getResponse());
    assertEquals(OK, store.getResponse().getStatusCode());
  }

  private void assertRequest(String expectedMethod, String expectedPath) throws IOException {
    ArgumentCaptor<HttpRequestBase> requestCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    verify(HTTP_CLIENT).execute(requestCaptor.capture(), any(HttpContext.class));
    HttpRequestBase capturedRequest = requestCaptor.getValue();
    assertEquals(expectedMethod, capturedRequest.getMethod());
    assertEquals(
        "https://api.mercadopago.com" + expectedPath, capturedRequest.getURI().toString());
  }

  private void assertStoreFields(Store store) {
    assertEquals(storeId, store.getId());
    assertEquals("Store Test", store.getName());
    assertEquals(date, store.getDateCreated());
    assertNotNull(store.getResponse().getContent());
  }
}
