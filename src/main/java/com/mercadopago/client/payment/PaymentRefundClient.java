package com.mercadopago.client.payment;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.deserializeListFromJson;
import static com.mercadopago.serialization.Serializer.serializeToJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
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

  /**
   * PaymentRefundClient constructor.
   *
   * @param httpClient httpClient
   */
  public PaymentRefundClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
  }

  /**
   * Creates a refund for payment.
   *
   * @param paymentId paymentId
   * @param amount amount
   * @param requestOptions requestOptions
   * @return PaymentRefund
   * @throws MPException exception
   */
  public PaymentRefund refund(Long paymentId, BigDecimal amount, MPRequestOptions requestOptions)
      throws MPException {
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
   * Gets a refund by id from the payment.
   *
   * @param paymentId paymentId
   * @param refundId refundId
   * @param requestOptions requestOptions
   * @return PaymentRefund
   * @throws MPException exception
   */
  public PaymentRefund get(Long paymentId, Long refundId, MPRequestOptions requestOptions)
      throws MPException {
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
   * @param paymentId paymentId
   * @param requestOptions requestOptions
   * @return list of PaymentRefund
   * @throws MPException exception
   */
  public MPResourceList<PaymentRefund> list(Long paymentId, MPRequestOptions requestOptions)
      throws MPException {
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
    result.forEach(paymentRefund -> paymentRefund.setResponse(response));

    return result;
  }
}
