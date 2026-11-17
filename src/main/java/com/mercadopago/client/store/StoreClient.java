package com.mercadopago.client.store;

import static com.mercadopago.helper.RequestHelper.serializeRequest;

import com.google.gson.JsonObject;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import com.mercadopago.serialization.Serializer;

/** Client for the MercadoPago Stores API. */
public class StoreClient extends MercadoPagoClient {

  /**
   * Default constructor. Uses the default HTTP client provided by {@link
   * com.mercadopago.MercadoPagoConfig}.
   */
  public StoreClient() {
    this(com.mercadopago.MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code StoreClient} with a custom HTTP client.
   *
   * @param httpClient the {@link com.mercadopago.net.MPHttpClient} implementation used to execute
   *     HTTP requests
   */
  public StoreClient(com.mercadopago.net.MPHttpClient httpClient) {
    super(httpClient);
  }

  /**
   * Creates a store associated with the given user.
   *
   * @param userId the identifier of the user that owns the store
   * @param request the {@link StoreCreateRequest} with the store attributes
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store createStore(Long userId, StoreCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    JsonObject payload = serializeRequest(request);
    MPResponse response =
        send(
            String.format("/v1/users/%s/stores", encodePathParam(userId)),
            HttpMethod.POST,
            payload,
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Retrieves a store by its identifier.
   *
   * @param storeId the identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link Store} matching the given identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store getStore(String storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPResponse response =
        send(
            String.format("/stores/%s", encodePathParam(storeId)),
            HttpMethod.GET,
            null,
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Searches the stores of the given user.
   *
   * @param userId the identifier of the user that owns the stores
   * @param searchRequest the {@link MPSearchRequest} with query parameters; may be {@code null}
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResourceList} of {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResourceList<Store> searchStores(
      Long userId, MPSearchRequest searchRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPResponse response =
        search(
            String.format("/users/%s/stores/search", encodePathParam(userId)),
            searchRequest,
            requestOptions);
    MPResourceList<Store> stores = Serializer.deserializeFromJson(MPResourceList.class, response.getContent());
    stores.setResponse(response);
    return stores;
  }

  /**
   * Updates a store.
   *
   * @param userId the identifier of the user that owns the store
   * @param storeId the identifier of the store
   * @param request the {@link StoreUpdateRequest} with the store attributes to update
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store updateStore(
      Long userId, String storeId, StoreUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    JsonObject payload = serializeRequest(request);
    MPResponse response =
        send(
            String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId)),
            HttpMethod.PUT,
            payload,
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Deletes a store.
   *
   * @param userId the identifier of the user that owns the store
   * @param storeId the identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} of the delete operation
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse deleteStore(Long userId, String storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return send(
        String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId)),
        HttpMethod.DELETE,
        null,
        null,
        requestOptions);
  }
}
