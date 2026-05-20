package com.mercadopago.client.identificationtype;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeListFromJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.identificationtype.IdentificationType;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Identification Types API.
 *
 * <p>Retrieves the list of identification document types available for the country associated
 * with the authenticated user's credentials (e.g., CPF for Brazil, DNI for Argentina).
 *
 * <p>Usage example:
 * <pre>{@code
 * IdentificationTypeClient client = new IdentificationTypeClient();
 * MPResourceList<IdentificationType> types = client.list();
 * }</pre>
 *
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/identification_types/_identification_types/get">
 *     Identification Types API reference</a>
 */
public class IdentificationTypeClient extends MercadoPagoClient {

  /** Class-level logger for identification type operations. */
  private static final Logger LOGGER = Logger.getLogger(IdentificationTypeClient.class.getName());

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public IdentificationTypeClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs an {@code IdentificationTypeClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public IdentificationTypeClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Lists all available identification types for the authenticated user's country.
   *
   * @return an {@link MPResourceList} of {@link IdentificationType} (e.g., CPF, DNI, CURP)
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/identification_types/_identification_types/get">api
   *     docs</a>
   */
  public MPResourceList<IdentificationType> list() throws MPException, MPApiException {
    return this.list(null);
  }

  /**
   * Lists all available identification types with custom request options.
   *
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResourceList} of {@link IdentificationType} (e.g., CPF, DNI, CURP)
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/identification_types/_identification_types/get">api
   *     docs</a>
   */
  public MPResourceList<IdentificationType> list(MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending list identification types");

    MPResponse response =
        list("/v1/identification_types", HttpMethod.GET, null, null, requestOptions);

    MPResourceList<IdentificationType> identificationTypes =
        deserializeListFromJson(IdentificationType.class, response.getContent());
    identificationTypes.setResponse(response);

    return identificationTypes;
  }
}
