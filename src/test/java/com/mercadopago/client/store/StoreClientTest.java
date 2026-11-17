package com.mercadopago.client.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.store.Store;
import java.io.IOException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class StoreClientTest extends BaseClientTest {

  @Test
  void createStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.CREATED);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store =
        new StoreClient().createStore(477763906L, StoreCreateRequest.builder().build(), null);
    assertNotNull(store);
    assertStoreFields(store, HttpStatus.CREATED);
  }

  @Test
  void getStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = new StoreClient().getStore("1234567", null);
    assertNotNull(store);
    assertStoreFields(store, HttpStatus.OK);
  }

  @Test
  void searchStoresSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_list.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<Store> stores =
        new StoreClient().searchStores(477763906L, null, null);
    assertNotNull(stores);
    assertEquals(1, stores.getResults().size());
    assertStoreFields(stores.getResults().get(0), HttpStatus.OK);
  }

  @Test
  void updateStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_update.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store =
        new StoreClient()
            .updateStore(477763906L, "1234567", StoreUpdateRequest.builder().build(), null);
    assertNotNull(store);
    assertStoreFields(store, HttpStatus.OK);
  }

  @Test
  void deleteStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/store/store_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResponse response = new StoreClient().deleteStore(477763906L, "1234567", null);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  private void assertStoreFields(Store store, HttpStatus expectedStatus) {
    assertEquals("1234567", store.getId());
    assertEquals("Official Store", store.getName());
    assertNotNull(store.getDateCreated());
    assertNotNull(store.getResponse().getContent());
    assertEquals(expectedStatus, store.getResponse().getStatusCode());
  }
}
