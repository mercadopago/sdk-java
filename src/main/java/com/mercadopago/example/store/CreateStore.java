package com.mercadopago.example.store;

import com.mercadopago.client.store.StoreClient;
import com.mercadopago.client.store.StoreCreateRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.store.Store;

/**
 * Creates a store associated to a Mercado Pago account.
 *
 * <p>Set your access token in the <code>MERCADO_PAGO_ACCESS_TOKEN</code> environment variable
 * before running this example.
 */
public class CreateStore {

  /** Private constructor to prevent instantiation. */
  private CreateStore() {}

  /**
   * Runs the store creation example.
   *
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {
    StoreClient client = new StoreClient();

    StoreCreateRequest request =
        StoreCreateRequest.builder()
            .name("Official Store")
            .externalId("store-123")
            .build();

    try {
      Long userId = 123456789L;
      Store store = client.create(userId, request);
      System.out.println("Store created successfully:");
      System.out.println("  Id: " + store.getId());
      System.out.println("  Name: " + store.getName());
      System.out.println("  External reference: " + store.getExternalReference());
    } catch (MPApiException apiException) {
      System.err.println(
          "API error - status: "
              + apiException.getStatusCode()
              + ", content: "
              + apiException.getApiResponse().getContent());
    } catch (MPException exception) {
      System.err.println("MP error: " + exception.getMessage());
    }
  }
}
