package com.mercadopago.client.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** StoreClientTest class. */
public class StoreClientTest extends BaseClientTest {
  private final StoreClient storeClient = new StoreClient();

  private final Long userId = 471763245L;

  private final String storeId = "1234567";

  @Test
  public void createSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = storeClient.create(userId, buildStoreCreateRequest());

    assertStoreFields(store);
  }

  @Test
  public void createWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = storeClient.create(userId, buildStoreCreateRequest(), buildRequestOptions());

    assertStoreFields(store);
  }

  @Test
  public void getSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = storeClient.get(storeId);

    assertStoreFields(store);
  }

  @Test
  public void getWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = storeClient.get(storeId, buildRequestOptions());

    assertStoreFields(store);
  }

  @Test
  public void searchSuccess() throws MPException, MPApiException, IOException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("external_reference", "store-123");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(10).offset(0).filters(filters).build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_list.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPElementsResourcesPage<Store> result = storeClient.search(userId, searchRequest);

    assertNotNull(result);
    assertEquals(2, result.getTotal());
    assertEquals(2, result.getElements().size());
    assertStoreFields(result.getElements().get(0));
  }

  @Test
  public void searchWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("external_reference", "store-123");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(10).offset(0).filters(filters).build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_list.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPElementsResourcesPage<Store> result =
        storeClient.search(userId, searchRequest, buildRequestOptions());

    assertNotNull(result);
    assertEquals(2, result.getTotal());
    assertEquals(2, result.getElements().size());
    assertStoreFields(result.getElements().get(0));
  }

  @Test
  public void updateSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = storeClient.update(userId, storeId, buildStoreUpdateRequest());

    assertStoreFields(store);
  }

  @Test
  public void updateWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store =
        storeClient.update(userId, storeId, buildStoreUpdateRequest(), buildRequestOptions());

    assertStoreFields(store);
  }

  @Test
  public void deleteSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = storeClient.delete(userId, storeId);

    assertStoreFields(store);
  }

  @Test
  public void deleteWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = storeClient.delete(userId, storeId, buildRequestOptions());

    assertStoreFields(store);
  }

  private StoreCreateRequest buildStoreCreateRequest() {
    return StoreCreateRequest.builder()
        .name("Official Store")
        .externalId("store-123")
        .location(
            StoreLocationRequest.builder()
                .addressLine("Av. das Nações Unidas, 3003")
                .reference("Floor 3")
                .city(StoreCityRequest.builder().id("BR-SP-44").name("São Paulo").build())
                .latitude(-23.5966)
                .longitude(-46.6835)
                .build())
        .build();
  }

  private StoreUpdateRequest buildStoreUpdateRequest() {
    return StoreUpdateRequest.builder()
        .name("Updated Store")
        .externalId("store-123")
        .location(
            StoreLocationRequest.builder()
                .addressLine("Av. das Nações Unidas, 3003")
                .reference("Floor 3")
                .city(StoreCityRequest.builder().id("BR-SP-44").name("São Paulo").build())
                .latitude(-23.5966)
                .longitude(-46.6835)
                .build())
        .build();
  }

  private void assertStoreFields(Store store) {
    assertNotNull(store);
    assertEquals("1234567", store.getId());
    assertEquals(471763245L, store.getUserId());
    assertEquals("Official Store", store.getName());
    assertEquals("A12S3D4F5G", store.getExternalId());
    assertNotNull(store.getResponse().getContent());
    assertEquals(HttpStatus.OK, store.getResponse().getStatusCode());
  }
}
