package com.mercadopago.client.invoice;

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
import com.mercadopago.resources.invoice.Invoice;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Authorized Payments (Invoice) API.
 *
 * <p>Provides access to invoices generated from subscription (preapproval) billing cycles. Each
 * invoice represents a scheduled payment attempt against the subscriber's payment method.
 *
 * <p>Usage example:
 * <pre>{@code
 * InvoiceClient client = new InvoiceClient();
 * Invoice invoice = client.get(6114264375L);
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/online-payments/subscriptions/get-authorized-payment/get">
 *     Invoice API reference</a>
 */
public class InvoiceClient extends MercadoPagoClient {

  /** Class-level logger for invoice operations. */
  private static final Logger LOGGER = Logger.getLogger(InvoiceClient.class.getName());

  /** URL template for single-invoice endpoints. */
  private static final String URL_WITH_ID = "/authorized_payments/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public InvoiceClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs an {@code InvoiceClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} used to execute HTTP requests
   */
  public InvoiceClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves an invoice (authorized payment) by its unique identifier.
   *
   * @param id the unique numeric identifier of the invoice
   * @return the requested {@link Invoice}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Invoice get(Long id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves an invoice (authorized payment) by its unique identifier with custom request options.
   *
   * @param id the unique numeric identifier of the invoice
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the requested {@link Invoice}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Invoice get(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get invoice request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    Invoice result = deserializeFromJson(Invoice.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Searches invoices matching the given criteria.
   *
   * @param request the {@link MPSearchRequest} with filters such as preapproval_id and payer_id
   * @return an {@link MPResultsResourcesPage} of {@link Invoice} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Invoice> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches invoices matching the given criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} with filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link Invoice} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Invoice> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search invoice request");
    MPResponse response = search("/authorized_payments/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Invoice>>() {}.getType();
    MPResultsResourcesPage<Invoice> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }
}
