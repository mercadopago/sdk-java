package com.mercadopago.example.store;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.store.StoreClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;

/** Example: Search stores. */
public class SearchStores {

  public static void main(String[] args) {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    StoreClient client = new StoreClient();

    try {
      Long userId = 123456789L;
      MPSearchRequest searchRequest = MPSearchRequest.builder().limit(10).offset(0).build();
      MPResultsResourcesPage<Store> results = client.search(userId, searchRequest, null);
      System.out.println("Total stores: " + results.getPaging().getTotal());
      for (Store store : results.getResults()) {
        System.out.println("Store: " + store.getName());
      }
    } catch (MPApiException e) {
      System.out.println("API error while searching stores: " + e.getMessage());
      System.out.println("Status: " + e.getApiResponse().getContent());
    } catch (MPException e) {
      System.out.println("Error while searching stores: " + e.getMessage());
    }
  }
}
