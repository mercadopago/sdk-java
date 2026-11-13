package com.mercadopago.example.store;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.store.StoreClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.store.Store;

import java.util.HashMap;
import java.util.Map;

/**
 * Example: retrieve an existing store by id using the Stores API.
 *
 * <p>See {@link CreateStore} to obtain a store id first.
 */
public class GetStore {

  public static void main(String[] args) {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    StoreClient client = new StoreClient();

    Long storeId = 123456789L;

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
      Store store = client.get(storeId.toString(), requestOptions);
      System.out.println("Store found: " + store.getName());
    } catch (MPApiException | MPException e) {
      System.out.println("Error getting store: " + e.getMessage());
    }
  }
}
