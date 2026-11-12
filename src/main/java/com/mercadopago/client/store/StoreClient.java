package com.mercadopago.client.store;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.deserializeResultsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.serializeToJson;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Stores API.
 *
 * <p>Provides CRUD operations for physical stores (create, get, update, delete) and search with
 * pagination. Stores represent physical locations where users can conduct business.
 *
 * <p>Usage example:
 * <pre>{@code
 * StoreClient client = new StoreClient();
 * Store store = client.create(userId, storeRequest);
 * Store retrieved = client.get(store.getId());
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">
 *     Stores API reference</a>
 */
public class StoreClient extends MercadoPagoClient {

  /** Class-level logger for store operations. */
  private static final Logger LOGGER = Logger.getLogger(StoreClient.class.getName());

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
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
   * Creates a new store for a user.
   *
   * @param userId the unique identifier of the user who owns the store
   * @param request the {@link StoreRequest} with store details (name, location, etc.)
   * @return the newly created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(String userId, StoreRequest request) throws MPException, MPApiException {
    return this.create(userId, request, null);
  }

  /**
   * Creates a new store for a user with custom request options.
   *
   * @param userId the unique identifier of the user who owns the store
   * @param request the {@link StoreRequest} with store details (name, location, etc.)
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the newly created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(String userId, StoreRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create store request");

    MPResponse response =
        send(
            String.format("/users/%s/stores", encodePathParam(userId)),
            HttpMethod.POST,
            serializeToJson(request),
            null,
            requestOptions);

    Store store = deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Retrieves a store by its unique identifier.
   *
   * @param id the unique identifier of the store
   * @return the requested {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a store by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get store request");

    MPResponse response =
        send(
            String.format("/stores/%s", encodePathParam(id)),
            HttpMethod.GET,
            null,
            null,
            requestOptions);

    Store store = deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Searches for stores matching the specified criteria.
   *
   * @param userId the unique identifier of the user who owns the stores
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link Store} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(String userId, MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(userId, request, null);
  }

  /**
   * Searches for stores matching the specified criteria with custom request options.
   *
   * @param userId the unique identifier of the user who owns the stores
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link Store} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(
      String userId, MPSearchRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search store request");

    MPResponse response =
        search(
            String.format("/users/%s/stores/search", encodePathParam(userId)),
            request,
            requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Store>>() {}.getType();
    MPResultsResourcesPage<Store> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Updates an existing store.
   *
   * @param userId the unique identifier of the user who owns the store
   * @param id the unique identifier of the store to update
   * @param request the {@link StoreRequest} with the updated store details
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(String userId, String id, StoreRequest request)
      throws MPException, MPApiException {
    return this.update(userId, id, request, null);
  }

  /**
   * Updates an existing store with custom request options.
   *
   * @param userId the unique identifier of the user who owns the store
   * @param id the unique identifier of the store to update
   * @param request the {@link StoreRequest} with the updated store details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(
      String userId, String id, StoreRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");

    MPResponse response =
        send(
            String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(id)),
            HttpMethod.PUT,
            serializeToJson(request),
            null,
            requestOptions);

    Store store = deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Deletes a store by its unique identifier.
   *
   * @param userId the unique identifier of the user who owns the store
   * @param id the unique identifier of the store to delete
   * @return the deleted {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store delete(String userId, String id) throws MPException, MPApiException {
    return this.delete(userId, id, null);
  }

  /**
   * Deletes a store by its unique identifier with custom request options.
   *
   * @param userId the unique identifier of the user who owns the store
   * @param id the unique identifier of the store to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the deleted {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store delete(String userId, String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");

    MPResponse response =
        send(
            String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(id)),
            HttpMethod.DELETE,
            null,
            null,
            requestOptions);

    Store store = deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }
}
