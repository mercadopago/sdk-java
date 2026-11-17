package com.mercadopago.client.store;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeElementsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.serializeToJson;

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
import com.mercadopago.net.UrlFormatter;
import com.mercadopago.resources.store.Store;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Stores API.
 *
 * <p>Provides operations to create, retrieve, search, update and delete stores associated with a
 * MercadoPago user account.
 *
 * @see <a
 *     href="https://www.mercadopago.com/developers/en/reference/stores/_users_user_id_stores/post">
 *     Stores API reference</a>
 */
public class StoreClient extends MercadoPagoClient {

  /** Class-level logger for store operations. */
  private static final Logger LOGGER = Logger.getLogger(StoreClient.class.getName());

  /** URL template for the store creation and search endpoints. */
  private static final String URL_WITH_USER_ID = "/users/%s/stores";

  /** URL template for the single-store endpoints. */
  private static final String URL_WITH_ID = "/stores/%s";

  /** URL template for the update and delete endpoints. */
  private static final String URL_WITH_USER_AND_ID = "/users/%s/stores/%s";

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
   * Creates a new store for the given user.
   *
   * @param userId the unique identifier of the store owner user
   * @param request the {@link StoreCreateRequest} with the store details
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreCreateRequest request) throws MPException, MPApiException {
    return this.create(userId, request, null);
  }

  /**
   * Creates a new store for the given user with custom request options.
   *
   * @param userId the unique identifier of the store owner user
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

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_USER_ID, encodePathParam(userId)))
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Store result = deserializeFromJson(Store.class, response.getContent());
    result.setResponse(response);

    return result;
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
  public Store get(String id, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get store request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_ID, encodePathParam(id)))
            .method(HttpMethod.GET)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Store result = deserializeFromJson(Store.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Searches for stores of the given user matching the specified criteria.
   *
   * @param userId the unique identifier of the store owner user
   * @param searchRequest the {@link MPSearchRequest} containing search filters and pagination
   *     parameters
   * @return an {@link MPElementsResourcesPage} of {@link Store} with matching results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPElementsResourcesPage<Store> search(Long userId, MPSearchRequest searchRequest)
      throws MPException, MPApiException {
    return this.search(userId, searchRequest, null);
  }

  /**
   * Searches for stores of the given user with custom request options.
   *
   * @param userId the unique identifier of the store owner user
   * @param searchRequest the {@link MPSearchRequest} containing search filters and pagination
   *     parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPElementsResourcesPage} of {@link Store} with matching results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPElementsResourcesPage<Store> search(
      Long userId, MPSearchRequest searchRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search store request");

    String uri =
        UrlFormatter.formatSearchUrl(
            String.format(URL_WITH_USER_ID, encodePathParam(userId)) + "/search", searchRequest);
    MPRequest mpRequest = MPRequest.builder().uri(uri).method(HttpMethod.GET).build();

    MPResponse response = send(mpRequest, requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<Store>>() {}.getType();
    MPElementsResourcesPage<Store> result =
        deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Updates an existing store.
   *
   * @param userId the unique identifier of the store owner user
   * @param id the unique identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the updated attributes
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(Long userId, String id, StoreUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(userId, id, request, null);
  }

  /**
   * Updates an existing store with custom request options.
   *
   * @param userId the unique identifier of the store owner user
   * @param id the unique identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the updated attributes
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

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_USER_AND_ID, encodePathParam(userId), encodePathParam(id)))
            .method(HttpMethod.PUT)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Store result = deserializeFromJson(Store.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Deletes a store.
   *
   * @param userId the unique identifier of the store owner user
   * @param id the unique identifier of the store to delete
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public void delete(Long userId, String id) throws MPException, MPApiException {
    this.delete(userId, id, null);
  }

  /**
   * Deletes a store with custom request options.
   *
   * @param userId the unique identifier of the store owner user
   * @param id the unique identifier of the store to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public void delete(Long userId, String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_USER_AND_ID, encodePathParam(userId), encodePathParam(id)))
            .method(HttpMethod.DELETE)
            .build();

    send(mpRequest, requestOptions);
  }
}
