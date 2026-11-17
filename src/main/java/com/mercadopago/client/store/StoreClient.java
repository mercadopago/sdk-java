package com.mercadopago.client.store;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.store.Store;
import com.mercadopago.serialization.Serializer;
import com.google.gson.reflect.TypeToken;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Stores API.
 *
 * <p>Provides operations to create, retrieve, search, update and delete stores associated with a
 * MercadoPago user account.
 *
 * <p>Usage example:
 * <pre>{@code
 * StoreClient client = new StoreClient();
 * Store store = client.get("123456");
 * }</pre>
 */
public class StoreClient extends MercadoPagoClient {

  /** Class-level logger for store operations. */
  private static final Logger LOGGER = Logger.getLogger(StoreClient.class.getName());

  /** Endpoint format string for creating a store of a user. */
  private static final String USER_STORES_URL = "/users/%s/stores";

  /** Endpoint format string for searching stores of a user. */
  private static final String USER_STORES_SEARCH_URL = "/users/%s/stores/search";

  /** Endpoint format string for retrieving a store by identifier. */
  private static final String STORE_URL = "/stores/%s";

  /** Endpoint format string for updating and deleting a store of a user. */
  private static final String USER_STORE_URL = "/users/%s/stores/%s";

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
   * @param userId identifier of the user that owns the store
   * @param request the {@link StoreRequest} with the store details
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreRequest request) throws MPException, MPApiException {
    return this.create(userId, request, null);
  }

  /**
   * Creates a store for the given user with custom request options.
   *
   * @param userId identifier of the user that owns the store
   * @param request the {@link StoreRequest} with the store details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create store request");
    MPResponse response =
        send(
            String.format(USER_STORES_URL, encodePathParam(userId)),
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
   * @param storeId identifier of the store
   * @return the {@link Store} with the given identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(String storeId) throws MPException, MPApiException {
    return this.get(storeId, null);
  }

  /**
   * Retrieves a store by its identifier with custom request options.
   *
   * @param storeId identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link Store} with the given identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(String storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get store request");
    MPResponse response =
        send(String.format(STORE_URL, encodePathParam(storeId)), HttpMethod.GET, null, null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Searches stores of the given user.
   *
   * @param userId identifier of the user that owns the stores
   * @param searchRequest the {@link MPSearchRequest} with search, filter and pagination
   *     parameters, or {@code null} for an unfiltered search
   * @return a page of {@link Store} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(Long userId, MPSearchRequest searchRequest)
      throws MPException, MPApiException {
    return this.search(userId, searchRequest, null);
  }

  /**
   * Searches stores of the given user with custom request options.
   *
   * @param userId identifier of the user that owns the stores
   * @param searchRequest the {@link MPSearchRequest} with search, filter and pagination
   *     parameters, or {@code null} for an unfiltered search
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return a page of {@link Store} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(
      Long userId, MPSearchRequest searchRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search stores request");
    MPResponse response =
        search(
            String.format(USER_STORES_SEARCH_URL, encodePathParam(userId)),
            searchRequest,
            requestOptions);
    MPResultsResourcesPage<Store> page =
        Serializer.deserializeFromJson(MPResultsResourcesPage.class, response.getContent());
    page.setResponse(response);
    return page;
  }

  /**
   * Updates a store.
   *
   * @param userId identifier of the user that owns the store
   * @param storeId identifier of the store to update
   * @param request the {@link StoreRequest} with the fields to update
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(Long userId, String storeId, StoreRequest request)
      throws MPException, MPApiException {
    return this.update(userId, storeId, request, null);
  }

  /**
   * Updates a store with custom request options.
   *
   * @param userId identifier of the user that owns the store
   * @param storeId identifier of the store to update
   * @param request the {@link StoreRequest} with the fields to update
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(
      Long userId, String storeId, StoreRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");
    MPResponse response =
        send(
            String.format(USER_STORE_URL, encodePathParam(userId), encodePathParam(storeId)),
            HttpMethod.PUT,
            Serializer.serializeToJson(request),
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Deletes a store.
   *
   * @param userId identifier of the user that owns the store
   * @param storeId identifier of the store to delete
   * @return the deleted {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store delete(Long userId, String storeId) throws MPException, MPApiException {
    return this.delete(userId, storeId, null);
  }

  /**
   * Deletes a store with custom request options.
   *
   * @param userId identifier of the user that owns the store
   * @param storeId identifier of the store to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the deleted {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store delete(Long userId, String storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");
    MPResponse response =
        send(
            String.format(USER_STORE_URL, encodePathParam(userId), encodePathParam(storeId)),
            HttpMethod.DELETE,
            null,
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }
}
