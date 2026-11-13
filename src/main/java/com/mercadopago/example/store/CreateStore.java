package com.mercadopago.example.store;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.store.StoreClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.store.Store;

/**
 * Create a store for a user.
 */
public class CreateStore {

  public static void main(String[] args) {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    StoreClient client = new StoreClient();

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("{{ACCESS_TOKEN}}")
            .connectionRequestTimeout(2000)
            .connectionTimeout(2000)
            .socketTimeout(2000)
            .build();

    StoreCreateRequest request =
        StoreCreateRequest.builder()
            .name("Original Store")
            .businessHours(
                StoreBusinessHoursRequest.builder()
                    .monday("09:00-18:00")
                    .tuesday("09:00-18:00")
                    .wednesday("09:00-18:00")
                    .thursday("09:00-18:00")
                    .friday("09:00-18:00")
                    .build())
            .location(
                StoreLocationRequest.builder()
                    .addressName("Mercado Pago HQ")
                    .addressLine("Av. Caseros 1234")
                    .reference("Near the main square")
                    .city(
                        StoreCityRequest.builder()
                            .id("TUxBQ0NBUGZlZj1OOQ")
                            .name("Buenos Aires")
                            .build())
                    .state(
                        StoreStateRequest.builder()
                            .id("TUxBUENBUGw3M2E1")
                            .name("Buenos Aires")
                            .build())
                    .zipCode("1424")
                    .build())
            .build();

    try {
      Store store = client.create(1234567890L, request, requestOptions);
      System.out.println("Store created: " + store.getId());
    } catch (MPApiException | MPException e) {
      System.out.println("Error creating store: " + e.getMessage());
    }
  }
}
