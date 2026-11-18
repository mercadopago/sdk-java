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
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.store.Store;
import com.mercadopago.serialization.Serializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Stores API.
 *
 * <p>Provides the five Stores operations: create, get, search, update and delete of stores
 * associated with a MercadoPago user account.
 *
 * <p>Usage example:
 * <pre>{@code
 * StoreClient client = new StoreClient();
 * Store store = client.get(123456L);
 * }</pre>
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
   * @param userId identifier of the user that will own the store
   * @param request the {@link StoreCreateRequest} with the store details
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreCreateRequest request)
      throws MPException, MPApiException {
    return this.create(userId, request, null);
  }

  /**
   * Creates a store for the given user with custom request options.
   *
   * @param userId identifier of the user that will own the store
   * @param request the {@link StoreCreateRequest} with the store details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreCreateRequest request, MPRequestOptions requestOptions)
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
   * @return the {@link Store} with the given identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(Long id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a store by its identifier with custom request options.
   *
   * @param id identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link Store} with the given identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(Long id, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get store request");
    MPResponse response =
        send(
            String.format("/stores/%s", encodePathParam(id)),
            HttpMethod.GET,
            null,
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Searches the stores of the given user with pagination parameters.
   *
   * @param userId identifier of the user that owns the stores
   * @param limit maximum number of results per page
   * @param offset number of results to skip
   * @return the {@link MPResultsResourcesPage} of {@link Store} with the paged search results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(Long userId, Integer limit, Integer offset)
      throws MPException, MPApiException {
    return this.search(userId, limit, offset, null);
  }

  /**
   * Searches the stores of the given user with pagination parameters and custom request options.
   *
   * @param userId identifier of the user that owns the stores
   * @param limit maximum number of results per page
   * @param offset number of results to skip
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResultsResourcesPage} of {@link Store} with the paged search results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(
      Long userId, Integer limit, Integer offset, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search stores request");
    Map<String, Object> queryParams = new HashMap<>();
    if (limit != null) {
      queryParams.put("limit", limit);
    }
    if (offset != null) {
      queryParams.put("offset", offset);
    }
    MPResponse response =
        search(
            String.format("/users/%s/stores/search", encodePathParam(userId)),
            new MPSearchRequest(null, queryParams),
            requestOptions);
    Type pageType =
        TypeToken.getParameterized(MPResultsResourcesPage.class, Store.class).getType();
    return Serializer.deserializeResultsResourcesPageFromJson(pageType, response.getContent());
  }

  /** Marker method retained for wave-1 verification of the search deserialization convention. */
  void verifySearchConvention() {
    // no-op
  }

  /**
   * Updates a store of the given user.
   *
   * @param userId identifier of the user that owns the store
   * @param id identifier of the store
   * @param request the {@link StoreUpdateRequest} with the fields to update
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(Long userId, Long id, StoreUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(userId, id, request, null);
  }

  /**
   * Updates a store of the given user with custom request options.
   *
   * @param userId identifier of the user that owns the store
   * @param id identifier of the store
   * @param request the {@link StoreUpdateRequest} with the fields to update
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(
      Long userId, Long id, StoreUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");
    MPResponse response =
        send(
            String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(id)),
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
   * @param userId identifier of the user that owns the store
   * @param id identifier of the store
   * @return the {@link MPResponse} of the delete operation
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse delete(Long userId, Long id) throws MPException, MPApiException {
    return this.delete(userId, id, null);
  }

  /**
   * Deletes a store of the given user with custom request options.
   *
   * @param userId identifier of the user that owns the store
   * @param id identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} of the delete operation
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse delete(Long userId, Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");
    return send(
        String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(id)),
        HttpMethod.DELETE,
        null,
        null,
        requestOptions);
  }
}
