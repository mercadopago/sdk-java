package com.mercadopago.client.payment;

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
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentRefund;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Payments API (v1).
 *
 * <p>Supports the full payment lifecycle: creation, retrieval, cancellation, capture (full and
 * partial amounts), and search with pagination. Refund operations are delegated to an internal
 * {@link PaymentRefundClient} and exposed via convenience methods on this class.
 *
 * <p>Usage example:
 * <pre>{@code
 * PaymentClient client = new PaymentClient();
 * Payment payment = client.create(paymentCreateRequest);
 * Payment captured = client.capture(payment.getId());
 * PaymentRefund refund = client.refund(payment.getId());
 * }</pre>
 *
 * @see PaymentRefundClient
 * @see <a href="https://www.mercadopago.com/developers/en/reference/payments/_payments/post">
 *     Payments API reference</a>
 */
public class PaymentClient extends MercadoPagoClient {

  /** Class-level logger for payment operations. */
  private static final Logger LOGGER = Logger.getLogger(PaymentClient.class.getName());

  /** URL template for single-payment endpoints (e.g. {@code /v1/payments/{id}}). */
  private static final String URL_WITH_ID = "/v1/payments/%s";

  /** Internal client used to perform refund operations on behalf of this client. */
  private final PaymentRefundClient refundClient;

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public PaymentClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code PaymentClient} with a custom HTTP client.
   *
   * <p>Also initialises the internal {@link PaymentRefundClient} with the same HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public PaymentClient(MPHttpClient httpClient) {
    super(httpClient);
    refundClient = new PaymentRefundClient(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a payment by its unique identifier.
   *
   * @param id the unique identifier of the payment
   * @return the requested {@link Payment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment get(Long id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a payment by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the payment
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Payment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment get(Long id, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get payment request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id.toString()), HttpMethod.GET, null, null, requestOptions);

    Payment result = deserializeFromJson(Payment.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a new payment.
   *
   * @param request the {@link PaymentCreateRequest} with payment details (amount, payer, method,
   *     etc.)
   * @return the created {@link Payment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment create(PaymentCreateRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new payment with custom request options.
   *
   * @param request the {@link PaymentCreateRequest} with payment details (amount, payer, method,
   *     etc.)
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Payment}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment create(PaymentCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create payment request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri("/v1/payments")
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Payment result = deserializeFromJson(Payment.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Cancels a pending payment by setting its status to {@code cancelled}.
   *
   * @param id the unique identifier of the payment to cancel
   * @return the cancelled {@link Payment} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment cancel(Long id) throws MPException, MPApiException {
    return this.cancel(id, null);
  }

  /**
   * Cancels a pending payment with custom request options.
   *
   * @param id the unique identifier of the payment to cancel
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the cancelled {@link Payment} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment cancel(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending cancel payment request");
    PaymentCancelRequest payload = new PaymentCancelRequest();
    MPResponse response =
        send(
            String.format(URL_WITH_ID, id.toString()),
            HttpMethod.PUT,
            Serializer.serializeToJson(payload),
            new HashMap<>(),
            requestOptions);

    Payment result = deserializeFromJson(Payment.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Captures a previously authorized payment for its full amount.
   *
   * @param id the unique identifier of the payment to capture
   * @return the captured {@link Payment} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment capture(Long id) throws MPException, MPApiException {
    return this.capture(id, null, null);
  }

  /**
   * Captures a previously authorized payment for its full amount with custom request options.
   *
   * @param id the unique identifier of the payment to capture
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the captured {@link Payment} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment capture(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.capture(id, null, requestOptions);
  }

  /**
   * Captures a previously authorized payment for the specified amount (partial capture).
   *
   * @param id the unique identifier of the payment to capture
   * @param amount the amount to capture; if {@code null}, the full authorized amount is captured
   * @return the captured {@link Payment} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment capture(Long id, BigDecimal amount) throws MPException, MPApiException {
    return this.capture(id, amount, null);
  }

  /**
   * Captures a previously authorized payment for the specified amount with custom request options.
   *
   * <p>If {@code amount} is {@code null}, the full authorized amount is captured (full capture).
   * Otherwise, a partial capture is performed for the given amount.
   *
   * @param id the unique identifier of the payment to capture
   * @param amount the amount to capture, or {@code null} for the full authorized amount
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the captured {@link Payment} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Payment capture(Long id, BigDecimal amount, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending capture payment request");
    PaymentCaptureRequest payload =
        PaymentCaptureRequest.builder().transactionAmount(amount).build();

    MPResponse response =
        send(
            String.format(URL_WITH_ID, id.toString()),
            HttpMethod.PUT,
            Serializer.serializeToJson(payload),
            new HashMap<>(),
            requestOptions);

    Payment result = deserializeFromJson(Payment.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Searches for payments matching the specified criteria.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link Payment} with the matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Payment> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches for payments matching the specified criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link Payment} with the matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<Payment> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search payment request");
    MPResponse response = search("/v1/payments/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Payment>>() {}.getType();
    MPResultsResourcesPage<Payment> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a total refund for a payment, returning the full amount to the payer.
   *
   * <p>Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment to refund
   * @return the {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PaymentRefund refund(Long paymentId) throws MPException, MPApiException {
    return this.refund(paymentId, null, null);
  }

  /**
   * Creates a total refund for a payment with custom request options.
   *
   * <p>Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment to refund
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PaymentRefund refund(Long paymentId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.refund(paymentId, null, requestOptions);
  }

  /**
   * Creates a partial refund for a payment, returning the specified amount to the payer.
   *
   * <p>Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment to refund
   * @param amount the amount to refund; if {@code null}, a total refund is performed
   * @return the {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PaymentRefund refund(Long paymentId, BigDecimal amount)
      throws MPException, MPApiException {
    return this.refund(paymentId, amount, null);
  }

  /**
   * Creates a total or partial refund for a payment with custom request options.
   *
   * <p>If {@code amount} is {@code null}, a total refund is performed. Otherwise, the specified
   * amount is refunded (partial refund). Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment to refund
   * @param amount the amount to refund, or {@code null} for a total refund
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PaymentRefund refund(Long paymentId, BigDecimal amount, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return refundClient.refund(paymentId, amount, requestOptions);
  }

  /**
   * Retrieves a specific refund associated with a payment.
   *
   * <p>Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment
   * @param refundId the unique identifier of the refund
   * @return the {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PaymentRefund getRefund(Long paymentId, Long refundId) throws MPException, MPApiException {
    return this.getRefund(paymentId, refundId, null);
  }

  /**
   * Retrieves a specific refund associated with a payment with custom request options.
   *
   * <p>Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment
   * @param refundId the unique identifier of the refund
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PaymentRefund getRefund(Long paymentId, Long refundId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return refundClient.get(paymentId, refundId, requestOptions);
  }

  /**
   * Lists all refunds associated with a payment.
   *
   * <p>Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment
   * @return an {@link MPResourceList} of {@link PaymentRefund} for the given payment
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResourceList<PaymentRefund> listRefunds(Long paymentId)
      throws MPException, MPApiException {
    return this.listRefunds(paymentId, null);
  }

  /**
   * Lists all refunds associated with a payment with custom request options.
   *
   * <p>Delegates to the internal {@link PaymentRefundClient}.
   *
   * @param paymentId the unique identifier of the payment
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResourceList} of {@link PaymentRefund} for the given payment
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResourceList<PaymentRefund> listRefunds(Long paymentId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return refundClient.list(paymentId, requestOptions);
  }
}
