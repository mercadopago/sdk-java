package com.mercadopago.client.advancedpayment;

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
import com.mercadopago.resources.advancedpayment.AdvancedPayment;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Advanced Payments (Marketplace Split Payments) API.
 *
 * <p>Enables marketplace integrations to collect a single payment and distribute funds among
 * multiple sellers (disbursements). Supports two-step flows (authorise → capture) and individual
 * disbursement release-date control.
 *
 * <p>Usage example:
 * <pre>{@code
 * AdvancedPaymentClient client = new AdvancedPaymentClient();
 * AdvancedPayment payment = client.create(createRequest);
 * AdvancedPayment captured = client.capture(payment.getId());
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">
 *     Advanced Payments API reference</a>
 */
public class AdvancedPaymentClient extends MercadoPagoClient {

  /** Class-level logger for advanced payment operations. */
  private static final Logger LOGGER = Logger.getLogger(AdvancedPaymentClient.class.getName());

  /** URL for the advanced payments collection endpoint. */
  private static final String URL = "/v1/advanced_payments";

  /** URL template for single advanced payment endpoints. */
  private static final String URL_WITH_ID = "/v1/advanced_payments/%s";

  /** URL template for the disburses (release date) endpoint. */
  private static final String URL_DISBURSES = "/v1/advanced_payments/%s/disburses";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public AdvancedPaymentClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs an {@code AdvancedPaymentClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} used to execute HTTP requests
   */
  public AdvancedPaymentClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves an advanced payment by its unique identifier.
   *
   * @param id the unique identifier of the advanced payment
   * @return the requested {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment get(Long id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves an advanced payment by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the advanced payment
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the requested {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment get(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get advanced payment request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    AdvancedPayment result = deserializeFromJson(AdvancedPayment.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Creates a new advanced (split) payment.
   *
   * @param request the {@link AdvancedPaymentCreateRequest} with payment and disbursement details
   * @return the created {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment create(AdvancedPaymentCreateRequest request)
      throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new advanced (split) payment with custom request options.
   *
   * @param request the {@link AdvancedPaymentCreateRequest} with payment and disbursement details
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the created {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment create(AdvancedPaymentCreateRequest request,
      MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending create advanced payment request");
    MPResponse response =
        send(URL, HttpMethod.POST, serializeToJson(request), null, requestOptions);

    AdvancedPayment result = deserializeFromJson(AdvancedPayment.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Captures a previously authorised advanced payment.
   *
   * @param id the unique identifier of the advanced payment to capture
   * @return the captured {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment capture(Long id) throws MPException, MPApiException {
    return this.capture(id, null);
  }

  /**
   * Captures a previously authorised advanced payment with custom request options.
   *
   * @param id the unique identifier of the advanced payment to capture
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the captured {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment capture(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending capture advanced payment request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.PUT,
            serializeToJson(new AdvancedPaymentCaptureRequest()), null, requestOptions);

    AdvancedPayment result = deserializeFromJson(AdvancedPayment.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Cancels an advanced payment by setting its status to "cancelled".
   *
   * @param id the unique identifier of the advanced payment to cancel
   * @return the cancelled {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment cancel(Long id) throws MPException, MPApiException {
    return this.cancel(id, null);
  }

  /**
   * Cancels an advanced payment with custom request options.
   *
   * @param id the unique identifier of the advanced payment to cancel
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the cancelled {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment cancel(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending cancel advanced payment request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.PUT,
            serializeToJson(new AdvancedPaymentCancelRequest()), null, requestOptions);

    AdvancedPayment result = deserializeFromJson(AdvancedPayment.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Changes the money release date for all disbursements of an advanced payment.
   *
   * @param id the unique identifier of the advanced payment
   * @param releaseDate the new release date in ISO 8601 format
   * @return the updated {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment updateReleaseDate(Long id, String releaseDate)
      throws MPException, MPApiException {
    return this.updateReleaseDate(id, releaseDate, null);
  }

  /**
   * Changes the money release date for all disbursements with custom request options.
   *
   * @param id the unique identifier of the advanced payment
   * @param releaseDate the new release date in ISO 8601 format
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the updated {@link AdvancedPayment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public AdvancedPayment updateReleaseDate(Long id, String releaseDate,
      MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending update release date advanced payment request");
    AdvancedPaymentUpdateReleaseDateRequest releaseDateRequest =
        AdvancedPaymentUpdateReleaseDateRequest.builder().moneyReleaseDate(releaseDate).build();
    MPResponse response =
        send(String.format(URL_DISBURSES, id), HttpMethod.POST,
            serializeToJson(releaseDateRequest), null, requestOptions);

    AdvancedPayment result = deserializeFromJson(AdvancedPayment.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Searches advanced payments matching the given criteria.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link AdvancedPayment} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<AdvancedPayment> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches advanced payments matching the given criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link AdvancedPayment} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<AdvancedPayment> search(MPSearchRequest request,
      MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search advanced payment request");
    MPResponse response = search("/v1/advanced_payments/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<AdvancedPayment>>() {}.getType();
    MPResultsResourcesPage<AdvancedPayment> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }
}
