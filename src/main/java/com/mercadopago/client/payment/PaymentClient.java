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

/** Client responsible for performing payment actions. */
public class PaymentClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PaymentClient.class.getName());

  private static final String URL_WITH_ID = "/v1/payments/%s";

  private final PaymentRefundClient refundClient;

  /** Default constructor. Uses the default http client used by the SDK. */
  public PaymentClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
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
   * Method responsible for getting payment.
   *
   * @param id paymentId
   * @return payment
   * @throws MPException an error if the request fails
   */
  public Payment get(Long id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Method responsible for getting payment.
   *
   * @param id paymentId
   * @param requestOptions metadata to customize the request
   * @return payment
   * @throws MPException an error if the request fails
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
   * Method responsible for creating payment.
   *
   * @param request request
   * @return payment response
   * @throws MPException an error if the request fails
   */
  public Payment create(PaymentCreateRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Method responsible for creating payment with request options.
   *
   * @param request request
   * @param requestOptions metadata to customize the request
   * @return payment response
   * @throws MPException an error if the request fails
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
   * Method responsible for cancel payment.
   *
   * @param id id
   * @return Payment payment that was cancelled
   * @throws MPException an error if the request fails
   */
  public Payment cancel(Long id) throws MPException, MPApiException {
    return this.cancel(id, null);
  }

  /**
   * Method responsible for cancel payment with request options.
   *
   * @param id payment id
   * @param requestOptions metadata to customize the request
   * @return Payment payment that was cancelled
   * @throws MPException an error if the request fails
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
   * Method responsible for capture payment.
   *
   * @param id id
   * @return Payment payment that was captured
   * @throws MPException an error if the request fails
   */
  public Payment capture(Long id) throws MPException, MPApiException {
    return this.capture(id, null, null);
  }

  /**
   * Method responsible for capture payment.
   *
   * @param id id
   * @param requestOptions metadata to customize the request
   * @return Payment payment that was captured
   * @throws MPException an error if the request fails
   */
  public Payment capture(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.capture(id, null, requestOptions);
  }

  /**
   * Method responsible for capture payment.
   *
   * @param id id
   * @param amount amount to be captured
   * @return Payment payment that was captured
   * @throws MPException an error if the request fails
   */
  public Payment capture(Long id, BigDecimal amount) throws MPException, MPApiException {
    return this.capture(id, amount, null);
  }

  /**
   * Method responsible for capture payment.
   *
   * @param id paymentId
   * @param amount amount to be captured
   * @param requestOptions metadata to customize the request
   * @return Payment payment that was captured
   * @throws MPException an error if the request fails
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
   * Method responsible for search payments.
   *
   * @param request search request information
   * @return list of results
   * @throws MPException an error if the request fails
   */
  public MPResultsResourcesPage<Payment> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Method responsible for search payments.
   *
   * @param request search request information
   * @param requestOptions metadata to customize the request
   * @return list of results
   * @throws MPException an error if the request fails
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
   * Creates a total refund for payment.
   *
   * @param paymentId payment id
   * @return PaymentRefund refund information
   * @throws MPException an error if the request fails
   */
  public PaymentRefund refund(Long paymentId) throws MPException, MPApiException {
    return this.refund(paymentId, null, null);
  }

  /**
   * Creates a total refund for payment.
   *
   * @param paymentId payment id
   * @param requestOptions metadata to customize the request
   * @return PaymentRefund refund information
   * @throws MPException an error if the request fails
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
   * @return PaymentRefund refund information
   * @throws MPException an error if the request fails
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
   * @return PaymentRefund refund information
   * @throws MPException an error if the request fails
   */
  public PaymentRefund refund(Long paymentId, BigDecimal amount, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return refundClient.refund(paymentId, amount, requestOptions);
  }

  /**
   * Gets a refund by id from the payment.
   *
   * @param paymentId payment id
   * @param refundId refund id
   * @return PaymentRefund refund information
   * @throws MPException an error if the request fails
   */
  public PaymentRefund getRefund(Long paymentId, Long refundId) throws MPException, MPApiException {
    return this.getRefund(paymentId, refundId, null);
  }

  /**
   * Gets a refund by id from the payment.
   *
   * @param paymentId payment id
   * @param refundId refund id
   * @param requestOptions metadata to customize the request
   * @return PaymentRefund refund information
   * @throws MPException an error if the request fails
   */
  public PaymentRefund getRefund(Long paymentId, Long refundId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return refundClient.get(paymentId, refundId, requestOptions);
  }

  /**
   * Lists the refunds of the payment.
   *
   * @param paymentId payment id
   * @return list of PaymentRefund
   * @throws MPException an error if the request fails
   */
  public MPResourceList<PaymentRefund> listRefunds(Long paymentId)
      throws MPException, MPApiException {
    return this.listRefunds(paymentId, null);
  }

  /**
   * Lists the refunds of the payment.
   *
   * @param paymentId payment id
   * @param requestOptions metadata to customize the request
   * @return list of PaymentRefund
   * @throws MPException an error if the request fails
   */
  public MPResourceList<PaymentRefund> listRefunds(Long paymentId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return refundClient.list(paymentId, requestOptions);
  }
}
