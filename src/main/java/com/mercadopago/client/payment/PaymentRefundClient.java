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

/** Client that use the Payment Refunds APIs. */
public class PaymentRefundClient extends MercadoPagoClient {

  private static final Logger LOGGER = Logger.getLogger(PaymentRefundClient.class.getName());

  private static final String URL_WITH_PAYMENT_ID = "/v1/payments/%s/refunds";

  /** Default constructor. Uses the default http client used by the SDK. */
  public PaymentRefundClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
   */
  public PaymentRefundClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Creates a refund for payment.
   *
   * @param paymentId payment id
   * @return PaymentRefund
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">api
   *     docs</a>
   */
  public PaymentRefund refund(Long paymentId) throws MPException, MPApiException {
    return this.refund(paymentId, null, null);
  }

  /**
   * Creates a refund for payment.
   *
   * @param paymentId payment id
   * @param requestOptions metadata to customize the request
   * @return PaymentRefund
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">api
   *     docs</a>
   */
  public PaymentRefund refund(Long paymentId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.refund(paymentId, null, requestOptions);
  }

  /**
   * Creates a refund for payment.
   *
   * @param paymentId payment id
   * @param amount refund amount
   * @return PaymentRefund
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post">api
   *     docs</a>
   */
  public PaymentRefund refund(Long paymentId, BigDecimal amount)
      throws MPException, MPApiException {
    return this.refund(paymentId, amount, null);
  }

  /**
   * Creates a refund for payment.
   *
   * @param paymentId payment id
   * @param amount refund amount
   * @param requestOptions metadata to customize the request
   * @return PaymentRefund
   * @throws MPException an error if the request fails
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
   * Gets refund information by id from the payment.
   *
   * @param paymentId payment id
   * @param refundId refund id
   * @return PaymentRefund
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds_refund_id/get">api
   *     docs</a>
   */
  public PaymentRefund get(Long paymentId, Long refundId) throws MPException, MPApiException {
    return this.get(paymentId, refundId, null);
  }

  /**
   * Gets refund information by id from the payment.
   *
   * @param paymentId payment id
   * @param refundId refund id
   * @param requestOptions metadata to customize the request
   * @return PaymentRefund
   * @throws MPException an error if the request fails
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
   * Lists the refunds of the payment.
   *
   * @param paymentId payment id
   * @return list of PaymentRefund
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/get">api
   *     docs</a>
   */
  public MPResourceList<PaymentRefund> list(Long paymentId) throws MPException, MPApiException {
    return this.list(paymentId, null);
  }

  /**
   * Lists the refunds of the payment.
   *
   * @param paymentId payment id
   * @param requestOptions metadata to customize the request
   * @return list of PaymentRefund
   * @throws MPException an error if the request fails
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
