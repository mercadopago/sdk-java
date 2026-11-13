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
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Stores API.
 *
 * <p>Exposes the store management operations used by merchants with physical locations:
 * creation, retrieval, search, update, and deletion of stores attached to a user account.
 *
 * <p>Usage example:
 * <pre>{@code
 * StoreClient client = new StoreClient();
 * Store store = client.get("12345", null);
 * }</pre>
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
   * Creates a store for the given user.
   *
   * <p>Hits {@code POST /users/{user_id}/stores}.
   *
   * @param userId identifier of the user that will own the store
   * @param request the {@link StoreCreateRequest} with the store data
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create store request");
    JsonObject payload = Serializer.serializeToJson(request);
    MPResponse response =
        send(
            String.format("/users/%s/stores", encodePathParam(userId)),
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
   * <p>Hits {@code GET /stores/{id}}.
   *
   * @param id identifier of the store to retrieve
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the retrieved {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(String id, MPRequestOptions requestOptions) throws MPException, MPApiException {
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
   * Searches the stores of the given user.
   *
   * <p>Hits {@code GET /users/{user_id}/stores/search}.
   *
   * @param userId identifier of the user that owns the stores
   * @param searchRequest the {@link MPSearchRequest} with search, filter, and pagination
   *     parameters; may be {@code null} for an unfiltered search
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} with the search results returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResponse search(
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
   * <p>Hits {@code PUT /users/{user_id}/stores/{id}}.
   *
   * @param userId identifier of the user that owns the store
   * @param id identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the fields to update
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(
      Long userId, String id, StoreUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");
    JsonObject payload = Serializer.serializeToJson(request);
    MPResponse response =
        send(
            String.format(
                "/users/%s/stores/%s", encodePathParam(userId), encodePathParam(id)),
            HttpMethod.PUT,
            payload,
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Deletes a store of the given user.
   *
   * <p>Hits {@code DELETE /users/{user_id}/stores/{id}}.
   *
   * @param userId identifier of the user that owns the store
   * @param id identifier of the store to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public void delete(Long userId, String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");
    send(
        String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(id)),
        HttpMethod.DELETE,
        null,
        null,
        requestOptions);
  }
}
