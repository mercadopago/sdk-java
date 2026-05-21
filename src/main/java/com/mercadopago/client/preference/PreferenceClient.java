package com.mercadopago.client.preference;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeElementsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

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
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceSearch;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Checkout Preferences API.
 *
 * <p>Provides operations to create, retrieve, update, and search checkout preferences. A
 * preference defines the payment experience for the buyer, including items, payment methods,
 * redirect URLs, and expiration settings.
 *
 * <p>Usage example:
 * <pre>{@code
 * PreferenceClient client = new PreferenceClient();
 * Preference preference = client.create(preferenceRequest);
 * String initPoint = preference.getInitPoint();
 * }</pre>
 *
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences/post">
 *     Checkout Preferences API reference</a>
 */
public class PreferenceClient extends MercadoPagoClient {

  /** Class-level logger for preference operations. */
  private static final Logger LOGGER = Logger.getLogger(PreferenceClient.class.getName());

  /** URL template for single-preference endpoints (e.g. {@code /checkout/preferences/{id}}). */
  private static final String URL_WITH_ID = "/checkout/preferences/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public PreferenceClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code PreferenceClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public PreferenceClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a checkout preference by its unique identifier.
   *
   * @param id the unique identifier of the preference
   * @return the requested {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/get">api
   *     docs</a>
   */
  public Preference get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a checkout preference by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the preference
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/get">api
   *     docs</a>
   */
  public Preference get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get preference request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    Preference result = deserializeFromJson(Preference.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a new checkout preference.
   *
   * @param request the {@link PreferenceRequest} with items, payer info, payment methods, and
   *     other preference attributes
   * @return the created {@link Preference} including its {@code initPoint} URL
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences/post">api
   *     docs</a>
   */
  public Preference create(PreferenceRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new checkout preference with custom request options.
   *
   * @param request the {@link PreferenceRequest} with items, payer info, payment methods, and
   *     other preference attributes
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Preference} including its {@code initPoint} URL
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences/post">api
   *     docs</a>
   */
  public Preference create(PreferenceRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create preference request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri("/checkout/preferences")
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Preference result = deserializeFromJson(Preference.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Updates an existing checkout preference.
   *
   * @param id the unique identifier of the preference to update
   * @param request the {@link PreferenceRequest} with the updated preference attributes
   * @return the updated {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/put">api
   *     docs</a>
   */
  public Preference update(String id, PreferenceRequest request)
      throws MPException, MPApiException {
    return this.update(id, request, null);
  }

  /**
   * Updates an existing checkout preference with custom request options.
   *
   * @param id the unique identifier of the preference to update
   * @param request the {@link PreferenceRequest} with the updated preference attributes
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Preference}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/put">api
   *     docs</a>
   */
  public Preference update(String id, PreferenceRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update preference request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_ID, id))
            .method(HttpMethod.PUT)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Preference result = deserializeFromJson(Preference.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Searches for checkout preferences matching the specified criteria.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPElementsResourcesPage} of {@link PreferenceSearch} with matching results
   *     and pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<PreferenceSearch> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches for checkout preferences matching the specified criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPElementsResourcesPage} of {@link PreferenceSearch} with matching results
   *     and pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<PreferenceSearch> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search preference request");

    MPResponse response = search("/checkout/preferences/search", request, requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<PreferenceSearch>>() {}.getType();
    MPElementsResourcesPage<PreferenceSearch> result =
        deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
