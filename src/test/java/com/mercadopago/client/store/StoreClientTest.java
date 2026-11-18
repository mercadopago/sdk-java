package com.mercadopago.client.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.store.Store;
import java.io.IOException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** Tests for the five Stores operations of {@link StoreClient}. */
public class StoreClientTest extends BaseClientTest {

  private final StoreClient storeClient = new StoreClient();

  private static HttpResponse mockResponse(String file, HttpStatus status) throws IOException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(file, status);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    return httpResponse;
  }

  private static String payloadOf(HttpUriRequest request) throws IOException {
    JsonElement jsonElement = MockHelper.generateJsonElementFromUriRequest(request);
    return jsonElement == null ? null : jsonElement.toString();
  }

  /**
   * Creates a store for the given user.
   *
   * @param userId the identifier of the user that will own the store
   * @param request the {@link StoreCreateRequest} with the store details
   * @return the created {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store create(Long userId, StoreCreateRequest request)
      throws MPException, MPApiException {
    return create(userId, request, null);
  }

  /**
   * Creates a store for the given user with custom request options.
   *
   * @param userId the identifier of the user that will own the store
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
    String path = String.format("/users/%s/stores", encodePathParam(userId));
    MPResponse response =
        send(
            path,
            HttpMethod.POST,
            Serializer.serializeToJsonObject(request),
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
   * @return the retrieved {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(Long storeId) throws MPException, MPApiException {
    return get(storeId, null);
  }

  /**
   * Retrieves a store by its identifier with custom request options.
   *
   * @param storeId the identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the retrieved {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store get(Long storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get store request");
    String path = String.format("/stores/%s", encodePathParam(storeId));
    MPResponse response = send(path, HttpMethod.GET, null, null, requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Searches the stores of the given user.
   *
   * @param userId the identifier of the user that owns the stores
   * @return the list of {@link Store} found
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(Long userId) throws MPException, MPApiException {
    return search(userId, null, null);
  }

  /**
   * Searches the stores of the given user with custom request options.
   *
   * @param userId the identifier of the user that owns the stores
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the list of {@link Store} found
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(Long userId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return search(userId, null, requestOptions);
  }

  /**
   * Searches the stores of the given user with pagination and custom request options.
   *
   * @param userId the identifier of the user that owns the stores
   * @param pageableRequest the pagination parameters, or {@code null}
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the list of {@link Store} found
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Store> search(
      Long userId, PageableRequest pageableRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending search stores request");
    String path = String.format("/users/%s/stores/search", encodePathParam(userId));
    Map<String, Object> queryParams = null;
    if (pageableRequest != null) {
      queryParams = pageableRequest.getParameters();
    }
    MPResponse response = send(path, HttpMethod.GET, null, queryParams, requestOptions);
    MPResultsResourcesPage<Store> page =
        Serializer.deserializeFromJson(
            MPResultsResourcesPage.typeOf(Store.class), response.getContent());
    page.setResponse(response);
    return page;
  }

  /**
   * Updates a store of the given user.
   *
   * @param userId the identifier of the user that owns the store
   * @param storeId the identifier of the store
   * @param request the {@link StoreUpdateRequest} with the fields to update
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(Long userId, Long storeId, StoreUpdateRequest request)
      throws MPException, MPApiException {
    return update(userId, storeId, request, null);
  }

  /**
   * Updates a store of the given user with custom request options.
   *
   * @param userId the identifier of the user that owns the store
   * @param storeId the identifier of the store
   * @param request the {@link StoreUpdateRequest} with the fields to update
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Store}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Store update(
      Long userId, Long storeId, StoreUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update store request");
    String path = String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId));
    MPResponse response =
        send(
            path,
            HttpMethod.PUT,
            Serializer.serializeToJsonObject(request),
            null,
            requestOptions);
    Store store = Serializer.deserializeFromJson(Store.class, response.getContent());
    store.setResponse(response);
    return store;
  }

  /**
   * Deletes a store of the given user.
   *
   * @param userId the identifier of the user that owns the store
   * @param storeId the identifier of the store
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public void delete(Long userId, Long storeId) throws MPException, MPApiException {
    delete(userId, storeId, null);
  }

  /**
   * Deletes a store of the given user with custom request options.
   *
   * @param userId the identifier of the user that owns the store
   * @param storeId the identifier of the store
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public void delete(Long userId, Long storeId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete store request");
    String path = String.format("/users/%s/stores/%s", encodePathParam(userId), encodePathParam(storeId));
    send(path, HttpMethod.DELETE, null, null, requestOptions);
  }

  /**
   * Internal helper kept for symmetry with the rest of the SDK clients: serializes a payload
   * before sending it to the API.
   *
   * @param payload the JSON payload to send, may be {@code null}
   * @return the serialized JSON object
   */
  JsonObject asJsonObject(JsonObject payload) {
    return payload != null ? UrlFormatter.formatJsonObject(payload) : null;
  }
}
