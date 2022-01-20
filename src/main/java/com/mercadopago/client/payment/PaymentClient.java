package com.mercadopago.client.payment;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.deserializeResultsResourcesPageFromJson;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.IdempotentRequest;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.net.UrlFormatter;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/** PaymentClient class. */
public class PaymentClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PaymentClient.class.getName());

  private static final String URL_WITH_ID = "/v1/payments/%s";

  /** PaymentClient constructor. */
  public PaymentClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * PaymentClient constructor.
   *
   * @param httpClient httpClient
   */
  public PaymentClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
  }

  /**
   * Method responsible for getting payment.
   *
   * @param id paymentId
   * @return payment
   * @throws MPException exception
   */
  public Payment get(Long id) throws MPException {
    return this.get(id, null);
  }

  /**
   * Method responsible for getting payment.
   *
   * @param id paymentId
   * @param requestOptions requestOptions
   * @return payment
   * @throws MPException exception
   */
  public Payment get(Long id, MPRequestOptions requestOptions) throws MPException {
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
   * @throws MPException exception
   */
  public Payment create(PaymentCreateRequest request) throws MPException {
    return this.create(request, null);
  }

  /**
   * Method responsible for creating payment with request opetions.
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return payment response
   * @throws MPException exception
   */
  public Payment create(PaymentCreateRequest request, MPRequestOptions requestOptions)
      throws MPException {
    LOGGER.info("Sending create payment request");
    IdempotentRequest idempotentRequest =
        new IdempotentRequest(
            UrlFormatter.format("/v1/payments"),
            HttpMethod.POST,
            new HashMap<>(),
            Serializer.serializeToJson(request));
    MPResponse response = send(idempotentRequest, requestOptions);
    Payment result = deserializeFromJson(Payment.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for cancel payment.
   *
   * @param id id
   * @return Payment payment
   * @throws MPException exception
   */
  public Payment cancel(Long id) throws MPException {
    return this.cancel(id, null);
  }

  /**
   * Method responsible for cancel payment with request options.
   *
   * @param id paymentId
   * @param requestOptions requestOptions
   * @return payment
   * @throws MPException exception
   */
  public Payment cancel(Long id, MPRequestOptions requestOptions) throws MPException {
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
   * @return payment
   * @throws MPException exception
   */
  public Payment capture(Long id) throws MPException {
    return this.capture(id, null, null);
  }

  /**
   * Method responsible for capture payment.
   *
   * @param id id
   * @param requestOptions requestOptions
   * @return payment
   * @throws MPException exception
   */
  public Payment capture(Long id, MPRequestOptions requestOptions) throws MPException {
    return this.capture(id, null, requestOptions);
  }

  /**
   * Method responsible for capture payment.
   *
   * @param id id
   * @param amount amount
   * @return payment
   * @throws MPException exception
   */
  public Payment capture(Long id, BigDecimal amount) throws MPException {
    return this.capture(id, amount, null);
  }

  /**
   * Method responsible for capture payment.
   *
   * @param id paymentId
   * @param amount amount
   * @param requestOptions requestOptions
   * @return payment
   * @throws MPException exception
   */
  public Payment capture(Long id, BigDecimal amount, MPRequestOptions requestOptions)
      throws MPException {
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
   * @param request request
   * @return list of results
   * @throws MPException exception
   */
  public MPResultsResourcesPage<Payment> search(MPSearchRequest request) throws MPException {
    return this.search(request, null);
  }

  /**
   * Method responsible for search payments.
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return list of results
   * @throws MPException exception
   */
  public MPResultsResourcesPage<Payment> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException {
    LOGGER.info("Sending search payment request");
    MPResponse response = search("/v1/payments/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Payment>>() {}.getType();
    MPResultsResourcesPage<Payment> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
