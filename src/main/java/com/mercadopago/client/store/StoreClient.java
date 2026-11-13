package com.mercadopago.client.store;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Stores API.
 *
 * <p>Provides CRUD operations for stores (create, get, update, delete) and search with
 * pagination. Stores represent physical or logical locations associated with a seller user.
 *
 * <p>Usage example:
 * <pre>{@code
 * StoreClient client = new StoreClient();
 * Store store = client.create(userId, storeCreateRequest);
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/stores/_users_user_id_stores/post">
 *     Stores API reference</a>
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
   * Creates a new store for the given user.
   *
   * @param userId the unique identifier of the user that owns the store
   * @param request the {@link StoreCreateRequest} with the store details
   * @return the newly created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreCreateRequest request) throws MPException, MPApiException {
    return this.create(userId, request, null);
  }

  /**
   * Creates a new store for the given user with custom request options.
   *
   * @param userId the unique identifier of the user that owns the store
   * @param request the {@link StoreCreateRequest} with the store details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the newly created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create store request");

    JsonObject payload = Serializer.serializeToJson(request);
    MPRequest mpRequest =
        MPRequest.buildRequest(
            String.format("/users/%s/stores", encodePathParam(userId)),
            HttpMethod.POST,
            payload,
            null,
            requestOptions);
    MPResponse response = send(mpRequest);

    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Retrieves a store by its unique identifier.
   *
   * @param storeId the unique identifier of the store
   * @return the requested {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(String storeId) throws MPException, MPApiException {
    return this.get(storeId, null);
  }

  /**
   * Retrieves a store by its unique identifier with custom request options.
   *
   * @param storeId the unique identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(String storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get store request");

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
   * Searches for stores belonging to the given user.
   *
   * @param userId the unique identifier of the user that owns the stores
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPElementsResourcesPage} of {@link Store} with matching results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPElementsResourcesPage<Store> search(Long userId, MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(userId, request, null);
  }

  /**
   * Searches for stores belonging to the given user with custom request options.
   *
   * @param userId the unique identifier of the user that owns the stores
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPElementsResourcesPage} of {@link Store} with matching results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPElementsResourcesPage<Store> search(
      Long userId, MPSearchRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search store request");

    MPResponse response =
        search(
            String.format("/users/%s/stores/search", encodePathParam(userId)),
            request,
            requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<Store>>() {}.getType();
    MPElementsResourcesPage<Store> result =
        Serializer.deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Updates an existing store.
   *
   * @param userId the unique identifier of the user that owns the store
   * @param storeId the unique identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the updated store details
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(Long userId, String storeId, StoreUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(userId, storeId, request, null);
  }

  /**
   * Updates an existing store with custom request options.
   *
   * @param userId the unique identifier of the user that owns the store
   * @param storeId the unique identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the updated store details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(
      Long userId, String storeId, StoreUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");

    JsonObject payload = Serializer.serializeToJson(request);
    MPRequest mpRequest =
        MPRequest.buildRequest(
            String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId)),
            HttpMethod.PUT,
            payload,
            null,
            requestOptions);
    MPResponse response = send(mpRequest);

    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Deletes a store by its unique identifier.
   *
   * @param userId the unique identifier of the user that owns the store
   * @param storeId the unique identifier of the store to delete
   * @return the deleted {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store delete(Long userId, String storeId) throws MPException, MPApiException {
    return this.delete(userId, storeId, null);
  }

  /**
   * Deletes a store by its unique identifier with custom request options.
   *
   * @param userId the unique identifier of the user that owns the store
   * @param storeId the unique identifier of the store to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the deleted {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store delete(Long userId, String storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");

    MPRequest mpRequest =
        MPRequest.buildRequest(
            String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId)),
            HttpMethod.DELETE,
            null,
            null,
            requestOptions);
    MPResponse response = send(mpRequest);

    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }
}
