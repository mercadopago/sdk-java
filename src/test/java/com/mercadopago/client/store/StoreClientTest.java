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

class StoreClientTest extends BaseClientTest {

  private static final String STORE_BASE_JSON = "/store/store_base.json";
  private static final String STORE_ALL_JSON = "/store/store_all.json";

  @Test
  void createStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/response" + STORE_BASE_JSON, HttpStatus.CREATED);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreCreateRequest request =
        StoreCreateRequest.builder()
            .name("Official Store")
            .businessHours(
                StoreBusinessHoursRequest.builder()
                    .monday(
                        StoreOpeningHoursRequest.builder()
                            .open("08:00")
                            .close("15:00")
                            .build())
                    .build())
            .location(
                StoreLocationRequest.builder()
                    .addressLine("Av. Casacavel 1221")
                    .reference("Mercado Livre")
                    .city(
                        StoreCityRequest.builder()
                            .id("TUxBQ0NBUm9mZjE1")
                            .name("Carapicuíba")
                            .build())
                    .latitude(-23.588479)
                    .longitude(-46.634852)
                    .build())
            .build();

    Store store = new StoreClient().createStore(539675046L, request);
    assertNotNull(store);
    assertStoreFields(store);
  }

  @Test
  void getStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/response" + STORE_BASE_JSON, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = new StoreClient().getStore(960798L);
    assertNotNull(store);
    assertStoreFields(store);
  }

  @Test
  void searchStoresSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/response" + STORE_ALL_JSON, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Map<String, Object> filters = new HashMap<>();
    ResultsStoresPage page = new StoreClient().searchStores(new MPSearchRequest(30, 0));
    assertNotNull(page);
    assertEquals(2, page.getResults().size());
    assertStoreFields(page.getResults().get(0));
  }

  @Test
  void updateStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/response" + STORE_BASE_JSON, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    StoreUpdateRequest request =
        StoreUpdateRequest.builder()
            .name("Official Store")
            .businessHours(
                StoreBusinessHoursRequest.builder()
                    .monday(
                        StoreOpeningHoursRequest.builder()
                            .open("08:00")
                            .close("15:00")
                            .build())
                    .build())
            .location(
                StoreLocationRequest.builder()
                    .addressLine("Av. Casacavel 1221")
                    .reference("Mercado Livre")
                    .city(
                        StoreCityRequest.builder()
                            .id("TUxBQ0NBUm9mZjE1")
                            .name("Carapicuíba")
                            .build())
                    .latitude(-23.588479)
                    .longitude(-46.634852)
                    .build())
            .build();

    Store store = new StoreClient().updateStore(960798L, request);
    assertNotNull(store);
    assertStoreFields(store);
  }

  @Test
  void deleteStoreSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/response" + STORE_BASE_JSON, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Store store = new StoreClient().deleteStore(960798L);
    assertNotNull(store);
    assertStoreFields(store);
  }

  private void assertStoreFields(Store store) {
    assertEquals(960798, store.getId());
    assertEquals(539675046, store.getUserId());
    assertEquals("Official Store", store.getName());
    assertEquals("2022-01-10T10:10:10.000+00:00", store.getDateCreated());
    assertNotNull(store.getResponse().getContent());
    assertEquals(HttpStatus.OK, store.getResponse().getStatusCode());
  }
}
  "results": [
    {
      "id": 960798,
      "user_id": 539675046,
      "name": "Official Store",
      "date_created": "2022-01-10T10:10:10.000+00:00",
      "business_hours": {
        "monday": {
          "open": "08:00",
          "close": "15:00"
        }
      },
      "location": {
        "address_line": "Av. Casacavel 1221",
        "reference": "Mercado Livre",
        "city": {
          "id": "TUxBQ0NBUm9mZjE1",
          "name": "Carapicuíba"
        },
        "latitude": -23.588479,
        "longitude": -46.634852
      }
    },
    {
      "id": 960799,
      "user_id": 539675046,
      "name": "Second Store",
      "date_created": "2022-01-11T10:10:10.000+00:00",
      "business_hours": {
        "monday": {
          "open": "09:00",
          "close": "18:00"
        }
      },
      "location": {
        "address_line": "Av. das Nações Unidas 3003",
        "reference": "Mercado Pago",
        "city": {
          "id": "TUxBQ0NBUm9mZjE1",
          "name": "Carapicuíba"
        },
        "latitude": -23.588479,
        "longitude": -46.634852
      }
    }
  ]
}
