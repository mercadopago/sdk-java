package com.mercadopago.client.preapproval;

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
import com.mercadopago.resources.preapproval.Preapproval;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Subscriptions (Preapproval) API.
 *
 * <p>Provides operations to create, retrieve, update, and search preapprovals (subscriptions).
 * A preapproval authorises recurring charges against a payer's payment method over a defined
 * billing period.
 *
 * <p>Usage example:
 * <pre>{@code
 * PreapprovalClient client = new PreapprovalClient();
 * Preapproval subscription = client.create(preapprovalCreateRequest);
 * Preapproval updated = client.update(subscription.getId(), preapprovalUpdateRequest);
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/subscriptions/resource">
 *     Subscriptions API reference</a>
 */
public class PreapprovalClient extends MercadoPagoClient {

  /** Class-level logger for preapproval/subscription operations. */
  private static final Logger LOGGER = Logger.getLogger(PreapprovalClient.class.getName());

  /** URL template for single-preapproval endpoints (e.g. {@code /preapproval/{id}}). */
  private static final String URL_WITH_ID = "/preapproval/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public PreapprovalClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code PreapprovalClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public PreapprovalClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a preapproval (subscription) by its unique identifier.
   *
   * @param id the unique identifier of the preapproval
   * @return the requested {@link Preapproval}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Preapproval get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a preapproval (subscription) by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the preapproval
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Preapproval}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Preapproval get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get preapproval request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    Preapproval result = deserializeFromJson(Preapproval.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a new preapproval (subscription).
   *
   * @param request the {@link PreapprovalCreateRequest} with subscription details (reason, amount,
   *     frequency, etc.)
   * @return the created {@link Preapproval}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Preapproval create(PreapprovalCreateRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new preapproval (subscription) with custom request options.
   *
   * @param request the {@link PreapprovalCreateRequest} with subscription details (reason, amount,
   *     frequency, etc.)
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Preapproval}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Preapproval create(PreapprovalCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create preapproval request");
    MPResponse response =
        send("/preapproval", HttpMethod.POST, serializeToJson(request), null, requestOptions);

    Preapproval result = deserializeFromJson(Preapproval.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Updates an existing preapproval (subscription).
   *
   * @param id the unique identifier of the preapproval to update
   * @param request the {@link PreapprovalUpdateRequest} with the updated subscription attributes
   * @return the updated {@link Preapproval}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Preapproval update(String id, PreapprovalUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(id, request, null);
  }

  /**
   * Updates an existing preapproval (subscription) with custom request options.
   *
   * @param id the unique identifier of the preapproval to update
   * @param request the {@link PreapprovalUpdateRequest} with the updated subscription attributes
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Preapproval}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Preapproval update(
      String id, PreapprovalUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update preapproval request");
    MPResponse response =
        send(
            String.format(URL_WITH_ID, id),
            HttpMethod.PUT,
            serializeToJson(request),
            null,
            requestOptions);

    Preapproval result = deserializeFromJson(Preapproval.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Searches for preapprovals (subscriptions) matching the specified criteria.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link Preapproval} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Preapproval> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches for preapprovals (subscriptions) matching the specified criteria with custom request
   * options.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link Preapproval} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Preapproval> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search preapproval request");
    MPResponse response = search("/preapproval/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Preapproval>>() {}.getType();
    MPResultsResourcesPage<Preapproval> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
