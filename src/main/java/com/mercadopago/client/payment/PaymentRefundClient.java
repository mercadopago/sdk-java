package com.mercadopago.client.payment;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.deserializeListFromJson;
import static com.mercadopago.serialization.Serializer.serializeToJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.payment.PaymentRefund;
import java.math.BigDecimal;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Payment Refunds API.
 *
 * <p>Provides operations to create total or partial refunds for a payment, retrieve a specific
 * refund by its identifier, and list all refunds associated with a payment.
 *
 * <p>This client is typically used internally by {@link PaymentClient}, but can also be
 * instantiated directly for standalone refund management.
 *
 * @see PaymentClient
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">
 *     Payment Refunds API reference</a>
 */
public class PaymentRefundClient extends MercadoPagoClient {

  /** Class-level logger for refund operations. */
  private static final Logger LOGGER = Logger.getLogger(PaymentRefundClient.class.getName());

  /** URL template for refund endpoints scoped to a payment (e.g. {@code /v1/payments/{id}/refunds}). */
  private static final String URL_WITH_PAYMENT_ID = "/v1/payments/%s/refunds";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public PaymentRefundClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code PaymentRefundClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public PaymentRefundClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Creates a total refund for a payment, returning the full amount to the payer.
   *
   * @param paymentId the unique identifier of the payment to refund
   * @return the created {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">api
   *     docs</a>
   */
  public PaymentRefund refund(Long paymentId) throws MPException, MPApiException {
    return this.refund(paymentId, null, null);
  }

  /**
   * Creates a total refund for a payment with custom request options.
   *
   * @param paymentId the unique identifier of the payment to refund
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">api
   *     docs</a>
   */
  public PaymentRefund refund(Long paymentId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.refund(paymentId, null, requestOptions);
  }

  /**
   * Creates a partial refund for a payment, returning the specified amount to the payer.
   *
   * @param paymentId the unique identifier of the payment to refund
   * @param amount the amount to refund; if {@code null}, a total refund is performed
   * @return the created {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">api
   *     docs</a>
   */
  public PaymentRefund refund(Long paymentId, BigDecimal amount)
      throws MPException, MPApiException {
    return this.refund(paymentId, amount, null);
  }

  /**
   * Creates a total or partial refund for a payment with custom request options.
   *
   * <p>If {@code amount} is {@code null}, a total refund is performed. Otherwise, the specified
   * amount is refunded (partial refund).
   *
   * @param paymentId the unique identifier of the payment to refund
   * @param amount the amount to refund, or {@code null} for a total refund
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">api
   *     docs</a>
   */
  public PaymentRefund refund(Long paymentId, BigDecimal amount, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending refund payment request");
    PaymentRefundCreateRequest request =
        PaymentRefundCreateRequest.builder().amount(amount).build();

    MPResponse response =
        send(
            String.format(URL_WITH_PAYMENT_ID, paymentId),
            HttpMethod.POST,
            serializeToJson(request),
            null,
            requestOptions);
    PaymentRefund result = deserializeFromJson(PaymentRefund.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Retrieves a specific refund by its identifier from a payment.
   *
   * @param paymentId the unique identifier of the payment
   * @param refundId the unique identifier of the refund to retrieve
   * @return the requested {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds_refund_id/get">api
   *     docs</a>
   */
  public PaymentRefund get(Long paymentId, Long refundId) throws MPException, MPApiException {
    return this.get(paymentId, refundId, null);
  }

  /**
   * Retrieves a specific refund by its identifier from a payment with custom request options.
   *
   * @param paymentId the unique identifier of the payment
   * @param refundId the unique identifier of the refund to retrieve
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link PaymentRefund} with refund details
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds_refund_id/get">api
   *     docs</a>
   */
  public PaymentRefund get(Long paymentId, Long refundId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get refund payment request");
    MPResponse response =
        send(
            String.format("/v1/payments/%s/refunds/%s", paymentId, refundId),
            HttpMethod.GET,
            null,
            null,
            requestOptions);
    PaymentRefund result = deserializeFromJson(PaymentRefund.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Lists all refunds associated with a payment.
   *
   * @param paymentId the unique identifier of the payment
   * @return an {@link MPResourceList} of {@link PaymentRefund} for the given payment
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/get">api
   *     docs</a>
   */
  public MPResourceList<PaymentRefund> list(Long paymentId) throws MPException, MPApiException {
    return this.list(paymentId, null);
  }

  /**
   * Lists all refunds associated with a payment with custom request options.
   *
   * @param paymentId the unique identifier of the payment
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResourceList} of {@link PaymentRefund} for the given payment
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/get">api
   *     docs</a>
   */
  public MPResourceList<PaymentRefund> list(Long paymentId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending list refund payment request");
    MPResponse response =
        send(
            String.format(URL_WITH_PAYMENT_ID, paymentId),
            HttpMethod.GET,
            null,
            null,
            requestOptions);
    MPResourceList<PaymentRefund> result =
        deserializeListFromJson(PaymentRefund.class, response.getContent());
    result.setResponse(response);

    return result;
  }
}
