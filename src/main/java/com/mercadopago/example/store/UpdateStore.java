package com.mercadopago.example.store;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.store.StoreClient;
import com.mercadopago.client.store.StoreUpdateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.store.Store;

import java.util.HashMap;
import java.util.Map;

/** Example showing how to update an existing store with {@link StoreClient}. */
public class UpdateStore {

  /** Runs the update flow. */
  public static void main(String[] args) {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    StoreClient client = new StoreClient();

    Long storeId = 123456789L;

    StoreUpdateRequest updateRequest =
        StoreUpdateRequest.builder()
            .name("Updated Store Name")
            .businessHours(
                StoreBusinessHoursRequest.builder()
                    .monday("08:00-18:00")
                    .tuesday("08:00-18:00")
                    .build())
            .build();

    Map<String, String> customHeaders = new HashMap<>();
    customHeaders.put("x-idempotency-key", "{{IDEMPOTENCY_KEY}}");

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .connectionRequestTimeout(2000)
            .connectionTimeout(2000)
            .socketTimeout(2000)
            .customHeaders(customHeaders)
            .build();

    try {
      Store store = client.update(123456789L, storeId.toString(), updateRequest, requestOptions);
      System.out.println("Store updated: " + store.getId());
    } catch (MPApiException | MPException e) {
      System.out.println("Error updating store: " + e.getMessage());
    }
  }
}
