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
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import java.io.IOException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class StoreClientTest extends BaseClientTest {

  private static final String STORE_BASE_URL = "/users/123/stores";

  @Test
  void createStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.CREATED);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = new StoreClient().create(123L, buildStoreRequest());
    assertNotNull(store);
    assertStoreFields(store);
  }

  @Test
  void getStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = new StoreClient().get("456");
    assertNotNull(store);
    assertStoreFields(store);
  }

  @Test
  void searchStoresSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_list.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResultsResourcesPage<Store> stores =
        new StoreClient().search(123L, MPSearchRequest.builder().build());
    assertNotNull(stores);
    assertEquals(1, stores.getResults().size());
    assertStoreFields(stores.getResults().get(0));
  }

  @Test
  void updateStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = new StoreClient().update(123L, "456", buildStoreRequest());
    assertNotNull(store);
    assertStoreFields(store);
  }

  @Test
  void deleteStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = new StoreClient().delete(123L, 456L);
    assertNotNull(store);
  }

  private com.mercadopago.client.store.StoreCreateRequest buildStoreRequest() {
    return com.mercadopago.client.store.StoreCreateRequest.builder()
        .name("Official Store")
        .location(
            com.mercadopago.client.store.StoreLocationRequest.builder()
                .addressLine("Av. das Nações Unidas, 3003")
                .streetNumber("3003")
                .streetName("Av. das Nações Unidas")
                .zipCode("06233-200")
                .cityName("São Paulo")
                .stateName("SP")
                .latitude(-23.61f)
                .longitude(-46.69f)
                .reference("Near the mall")
                .build())
        .build();
  }

  private void assertStoreFields(Store store) {
    assertEquals(456L, store.getId());
    assertEquals("Test Store", store.getName());
    assertEquals("2022-01-10T10:10:10.000-04:00", store.getDateCreated());
    assertNotNull(store.getResponse().getContent());
    assertEquals(HttpStatus.OK, store.getResponse().getStatusCode());
    assertEquals(1, store.getResponse().getHeaders().size());
  }
}
