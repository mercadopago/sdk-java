package com.mercadopago.client.paymentmethod;

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
import com.mercadopago.resources.paymentmethod.PaymentMethod;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Payment Methods API.
 *
 * <p>Retrieves the list of payment methods available for the country associated with the
 * authenticated user's credentials (e.g., credit cards, debit cards, bank transfers, cash).
 *
 * <p>Usage example:
 * <pre>{@code
 * PaymentMethodClient client = new PaymentMethodClient();
 * MPResourceList<PaymentMethod> methods = client.list();
 * }</pre>
 *
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/payment_methods/_payment_methods/get">
 *     Payment Methods API reference</a>
 */
public class PaymentMethodClient extends MercadoPagoClient {

  /** Class-level logger for payment method operations. */
  private static final Logger LOGGER = Logger.getLogger(PaymentMethodClient.class.getName());

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public PaymentMethodClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code PaymentMethodClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public PaymentMethodClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Lists all available payment methods for the authenticated user's country.
   *
   * @return an {@link MPResourceList} of {@link PaymentMethod} (credit, debit, cash, etc.)
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/payment_methods/_payment_methods/get">api
   *     docs</a>
   */
  public MPResourceList<PaymentMethod> list() throws MPException, MPApiException {
    return this.list(null);
  }

  /**
   * Lists all available payment methods with custom request options.
   *
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResourceList} of {@link PaymentMethod} (credit, debit, cash, etc.)
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/payment_methods/_payment_methods/get">api
   *     docs</a>
   */
  public MPResourceList<PaymentMethod> list(MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending list payment method");

    MPResponse response = list("/v1/payment_methods", HttpMethod.GET, null, null, requestOptions);

    MPResourceList<PaymentMethod> paymentMethods =
        deserializeListFromJson(PaymentMethod.class, response.getContent());
    paymentMethods.setResponse(response);

    return paymentMethods;
  }
}
