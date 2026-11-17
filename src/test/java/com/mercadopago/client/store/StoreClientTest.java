package com.mercadopago.client.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.google.gson.JsonElement;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import java.io.IOException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** Tests for {@link StoreClient}. */
class StoreClientTest extends BaseClientTest {

  private static final String STORE_BASE_RESPONSE =
      "/mocks/response/store/store_base.json";
  private static final String STORE_UPDATED_RESPONSE =
      "/mocks/response/store/store_updated.json";
  private static final String STORE_SEARCH_RESPONSE =
      "/mocks/response/store/store_search.json";
  private static final String STORE_CREATE_REQUEST =
      "/mocks/request/store/store_base.json";
  private static final String STORE_UPDATE_REQUEST =
      "/mocks/request/store/store_update.json";

  @Test
  void createSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_BASE_RESPONSE, HttpStatus.CREATED);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    StoreCreateRequest request =
        StoreCreateRequest.builder().name("Store Name").build();
    Store store = client.create(123L, request);

    assertNotNull(store);
    assertEquals("Store Name", store.getName());

    JsonElement requestPayload =
        MockHelper.generateJsonElementFromUriRequest(httpRequestCaptor.getValue());
    assertEquals(
        MockHelper.generateJsonElement(STORE_CREATE_REQUEST), requestPayload);
  }

  @Test
  void createWithOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_BASE_RESPONSE, HttpStatus.CREATED);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    StoreCreateRequest request =
        StoreCreateRequest.builder().name("Store Name").build();
    Store store = client.create(123L, request, buildRequestOptions());

    assertNotNull(store);
    assertEquals("Store Name", store.getName());
  }

  @Test
  void getSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_BASE_RESPONSE, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    Store store = client.get("123");

    assertNotNull(store);
    assertEquals("Store Name", store.getName());
  }

  @Test
  void getWithOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_BASE_RESPONSE, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    Store store = client.get("123", buildRequestOptions());

    assertNotNull(store);
    assertEquals("Store Name", store.getName());
  }

  @Test
  void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_SEARCH_RESPONSE, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(10).offset(0).build();
    MPElementsResourcesPage<Store> page = client.search(123L, searchRequest);

    assertNotNull(page);
    assertNotNull(page.getElements());
    assertEquals(1, page.getElements().size());
    assertEquals("Store Name", page.getElements().get(0).getName());
  }

  @Test
  void searchWithOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_SEARCH_RESPONSE, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    MPSearchRequest builder = MPSearchRequest.builder().limit(10).offset(0).build();
    MPElementsResourcesPage<Store> page =
        client.search(123L, builder, buildRequestOptions());

    assertNotNull(page);
    assertNotNull(page.getElements());
    assertEquals(1, page.getElements().size());
    assertEquals("Store Name", page.getElements().get(0).getName());
  }

  @Test
  void updateSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_UPDATED_RESPONSE, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    StoreUpdateRequest request =
        StoreUpdateRequest.builder().name("Updated Store Name").build();
    Store store = client.update(123L, "123", request);

    assertNotNull(store);
    assertEquals("Updated Store Name", store.getName());

    JsonElement requestPayload =
        MockHelper.generateJsonElementFromUriRequest(httpRequestCaptor.getValue());
    assertEquals(
        MockHelper.generateJsonElement(STORE_UPDATE_REQUEST), requestPayload);
  }

  @Test
  void updateWithOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_UPDATED_RESPONSE, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    StoreUpdateRequest request =
        StoreUpdateRequest.builder().name("Updated Store Name").build();
    Store store = client.update(123L, "123", request, buildRequestOptions());

    assertNotNull(store);
    assertEquals("Updated Store Name", store.getName());
  }

  @Test
  void deleteSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(STORE_BASE_RESPONSE, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(httpRequestCaptor.capture(), any(HttpContext.class));

    StoreClient client = new StoreClient(HTTP_CLIENT);
    client.delete(123L, "123");

    HttpRequestBase sentRequest = httpRequestCaptor.getValue();
    assertEquals(HttpMethod.DELETE, sentRequest.getMethod());
    assertEquals("/users/123/stores/123", sentRequest.getURI().getPath());
  }
}
