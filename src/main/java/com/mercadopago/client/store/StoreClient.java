package com.mercadopago.client.store;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import static com.mercadopago.serialization.Serializer.deserializeElementsResourcesPageFromJson;

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
 * <p>Provides operations to create, retrieve, search, update, and delete stores associated with a
 * MercadoPago user. Stores represent physical locations where payments can be collected in person.
 *
 * <p>Usage example:
 * <pre>{@code
 * StoreClient client = new StoreClient();
 * Store store = client.createStore(userId, storeCreateRequest);
 * Store retrieved = client.getStore(store.getId());
 * }</pre>
 *
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores/post">
 *     Stores API reference</a>
 */
public class StoreClient extends MercadoPagoClient {

  /** Class-level logger for store operations. */
  private static final Logger LOGGER = Logger.getLogger(StoreClient.class.getName());

  /** URL template for store endpoints scoped to a user (e.g. {@code /users/{user_id}/stores}). */
  private static final String URL_WITH_USER_ID = "/users/%s/stores";

  /** URL template for store endpoints scoped to a user and a store id. */
  private static final String URL_WITH_USER_ID_AND_ID = "/users/%s/stores/%s";

  /** URL template for the single-store endpoint (e.g. {@code /stores/{id}}). */
  private static final String URL_WITH_ID = "/stores/%s";

  /** URL template for the store search endpoint scoped to a user. */
  private static final String URL_SEARCH_WITH_USER_ID = "/users/%s/stores/search";

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
   * @param userId the identifier of the user that will own the store
   * @param request the {@link StoreCreateRequest} with the store details (name, business hours,
   *     location, etc.)
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores/post">api
   *     docs</a>
   */
  public Store createStore(Long userId, StoreCreateRequest request)
      throws MPException, MPApiException {
    return this.createStore(userId, request, null);
  }

  /**
   * Creates a new store for the given user with custom request options.
   *
   * @param userId the identifier of the user that will own the store
   * @param request the {@link StoreCreateRequest} with the store details (name, business hours,
   *     location, etc.)
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores/post">api
   *     docs</a>
   */
  public Store createStore(Long userId, StoreCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create store request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_USER_ID, userId.toString()))
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
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_stores_id/get">api
   *     docs</a>
   */
  public Store getStore(Long id) throws MPException, MPApiException {
    return this.getStore(id, null);
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
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_stores_id/get">api
   *     docs</a>
   */
  public Store getStore(Long id, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get store request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_ID, id.toString()))
            .method(HttpMethod.GET)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Store result = deserializeFromJson(Store.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Searches for stores belonging to the given user matching the specified criteria.
   *
   * @param userId the identifier of the user that owns the stores
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link Store} with matching results and paging
   *     metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<Store> searchStores(Long userId, MPSearchRequest request)
      throws MPException, MPApiException {
    return this.searchStores(userId, request, null);
  }

  /**
   * Searches for stores belonging to the given user matching the specified criteria with custom
   * request options.
   *
   * @param userId the identifier of the user that owns the stores
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link Store} with matching results and paging
   *     metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores_search/get">api
   *     docs</a>
   */
  public MPResultsResourcesPage<Store> searchStores(
      Long userId, MPSearchRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search stores request");

    MPResponse response =
        search(String.format(URL_SEARCH_WITH_USER_ID, userId.toString()), request, requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<Store>>() {}.getType();
    MPElementsResourcesPage<Store> result =
        deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Updates an existing store.
   *
   * @param userId the identifier of the user that owns the store
   * @param id the unique identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the updated attributes
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores_id/put">api
   *     docs</a>
   */
  public Store updateStore(Long userId, Long id, StoreUpdateRequest request)
      throws MPException, MPApiException {
    return this.updateStore(userId, id, request, null);
  }

  /**
   * Updates an existing store with custom request options.
   *
   * @param userId the identifier of the user that owns the store
   * @param id the unique identifier of the store to update
   * @param request the {@link StoreUpdateRequest} with the updated attributes
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores_id/put">api
   *     docs</a>
   */
  public Store updateStore(
      Long userId, Long id, StoreUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_USER_ID_AND_ID, userId.toString(), id.toString()))
            .method(HttpMethod.PUT)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Store result = deserializeFromJson(Store.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Deletes an existing store.
   *
   * @param userId the identifier of the user that owns the store
   * @param id the unique identifier of the store to delete
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores_id/delete">api
   *     docs</a>
   */
  public void deleteStore(Long userId, Long id) throws MPException, MPApiException {
    this.deleteStore(userId, id, null);
  }

  /**
   * Deletes an existing store with custom request options.
   *
   * @param userId the identifier of the user that owns the store
   * @param id the unique identifier of the store to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/stores/_users_user_id_stores_id/delete">api
   *     docs</a>
   */
  public void deleteStore(Long userId, Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_USER_ID_AND_ID, userId.toString(), id.toString()))
            .method(HttpMethod.DELETE)
            .build();

    send(mpRequest, requestOptions);
  }
}
