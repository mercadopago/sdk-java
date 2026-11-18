package com.mercadopago.client.store;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import com.mercadopago.serialization.Serializer;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Stores API.
 *
 * <p>Supports creating, retrieving, searching, updating and deleting stores associated with a
 * seller user:
 * <ul>
 *   <li>POST /users/{user_id}/stores</li>
 *   <li>GET /stores/{id}</li>
 *   <li>GET /users/{user_id}/stores/search</li>
 *   <li>PUT /users/{user_id}/stores/{id}</li>
 *   <li>DELETE /users/{user_id}/stores/{id}</li>
 * </ul>
 */
public class StoreClient extends MercadoPagoClient {

  /** Class-level logger for store operations. */
  private static final Logger LOGGER = Logger.getLogger(StoreClient.class.getName());

  /** Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}. */
  public StoreClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code StoreClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public StoreClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Creates a store for the given user.
   *
   * @param userId identifier of the user (seller) that will own the store
   * @param request the {@link StoreCreateRequest} with the store details
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store createStore(Long userId, StoreCreateRequest request)
      throws MPException, MPApiException {
    return this.createStore(userId, request, null);
  }

  /**
   * Creates a store for the given user with custom request options.
   *
   * @param userId identifier of the user (seller) that will own the store
   * @param request the {@link StoreCreateRequest} with the store details
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store createStore(Long userId, StoreCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create store request");
    MPResponse response =
        send(
            String.format("/users/%s/stores", encodePathParam(userId)),
            HttpMethod.POST,
            Serializer.serializeToJson(request),
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Retrieves a store by its identifier.
   *
   * @param id identifier of the store
   * @return the {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store getStore(Long id) throws MPException, MPApiException {
    return this.getStore(id, null);
  }

  /**
   * Retrieves a store by its identifier with custom request options.
   *
   * @param id identifier of the store
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store getStore(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get store request");
    MPResponse response =
        send(String.format("/stores/%s", encodePathParam(id)), HttpMethod.GET, null, null, requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Searches stores of the given user.
   *
   * @param userId identifier of the user (seller)
   * @param searchRequest the {@link MPSearchRequest} with filters and pagination, or {@code null}
   * @return the {@link MPResponse} with the search results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse searchStores(Long userId, MPSearchRequest searchRequest)
      throws MPException, MPApiException {
    return this.searchStores(userId, searchRequest, null);
  }

  /**
   * Searches stores of the given user with custom request options.
   *
   * @param userId identifier of the user (seller)
   * @param searchRequest the {@link MPSearchRequest} with filters and pagination, or {@code null}
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the {@link MPResponse} with the search results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse searchStores(
      Long userId, MPSearchRequest searchRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search stores request");
    return search(
        String.format("/users/%s/stores/search", encodePathParam(userId)),
        searchRequest,
        requestOptions);
  }

  /**
   * Updates a store of the given user.
   *
   * @param userId identifier of the user (seller) that owns the store
   * @param storeId identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the fields to update
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store updateStore(Long userId, Long storeId, StoreUpdateRequest request)
      throws MPException, MPApiException {
    return this.updateStore(userId, storeId, request, null);
  }

  /**
   * Updates a store of the given user with custom request options.
   *
   * @param userId identifier of the user (seller) that owns the store
   * @param storeId identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the fields to update
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store updateStore(
      Long userId, Long storeId, StoreUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");
    MPResponse response =
        send(
            String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId)),
            HttpMethod.PUT,
            Serializer.serializeToJson(request),
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Deletes a store of the given user.
   *
   * @param userId identifier of the user (seller) that owns the store
   * @param storeId identifier of the store to delete
   * @return the {@link MPResponse} returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse deleteStore(Long userId, Long storeId) throws MPException, MPApiException {
    return this.deleteStore(userId, storeId, null);
  }

  /**
   * Deletes a store of the given user with custom request options.
   *
   * @param userId identifier of the user (seller) that owns the store
   * @param storeId identifier of the store to delete
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the {@link MPResponse} returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse deleteStore(Long userId, Long storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");
    return send(
        String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId)),
        HttpMethod.DELETE,
        (JsonObject) null,
        (Map<String, Object>) null,
        requestOptions);
  }
}
