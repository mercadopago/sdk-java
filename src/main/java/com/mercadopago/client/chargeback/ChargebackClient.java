package com.mercadopago.client.chargeback;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.deserializeResultsResourcesPageFromJson;

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
import com.mercadopago.resources.chargeback.Chargeback;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Chargebacks API.
 *
 * <p>Provides read-only access to chargeback dispute records initiated by cardholders through
 * their issuing bank.
 *
 * <p>Usage example:
 * <pre>{@code
 * ChargebackClient client = new ChargebackClient();
 * Chargeback chargeback = client.get("CB-001");
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/">
 *     Chargebacks API reference</a>
 */
public class ChargebackClient extends MercadoPagoClient {

  /** Class-level logger for chargeback operations. */
  private static final Logger LOGGER = Logger.getLogger(ChargebackClient.class.getName());

  /** URL template for single-chargeback endpoints. */
  private static final String URL_WITH_ID = "/v1/chargebacks/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public ChargebackClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code ChargebackClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} used to execute HTTP requests
   */
  public ChargebackClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a chargeback by its unique identifier.
   *
   * @param id the unique identifier of the chargeback
   * @return the requested {@link Chargeback}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Chargeback get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a chargeback by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the chargeback
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the requested {@link Chargeback}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Chargeback get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get chargeback request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    Chargeback result = deserializeFromJson(Chargeback.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Searches chargebacks matching the given criteria.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link Chargeback} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Chargeback> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches chargebacks matching the given criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link Chargeback} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Chargeback> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search chargeback request");
    MPResponse response = search("/v1/chargebacks/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Chargeback>>() {}.getType();
    MPResultsResourcesPage<Chargeback> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }
}
