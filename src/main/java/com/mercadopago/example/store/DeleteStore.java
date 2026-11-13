package com.mercadopago.example.store;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.store.StoreClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import java.util.HashMap;
import java.util.Map;

/** Example showing how to delete a store using the Stores API. */
public class DeleteStore {

  /** Deletes a store by its identifier. */
  public static void main(String[] args) {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    StoreClient client = new StoreClient();

    Long userId = 123456789L;
    String storeId = "123456789";

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
      client.delete(userId, storeId, requestOptions);
      System.out.println("Store deleted: " + storeId);
    } catch (MPApiException | MPException e) {
      System.out.println("Error deleting store: " + e.getMessage());
    }
  }
}
