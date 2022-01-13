package com.mercadopago.client.payment;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.IdempotentRequest;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.SearchRequest;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.UrlFormatter;
import com.mercadopago.resources.ResultsResourcesPage;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.serialization.Serializer;
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
    LOGGER.info("Sending get payment request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id.toString()), HttpMethod.GET, null, new HashMap<>());

    Payment result = Serializer.deserializeFromJson(Payment.class, response.getContent());
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
    LOGGER.info("Sending create payment request");
    IdempotentRequest idempotentRequest =
        new IdempotentRequest(
            UrlFormatter.format("/v1/payments"),
            HttpMethod.POST,
            new HashMap<>(),
            Serializer.serializeToJson(request));
    MPResponse response = send(idempotentRequest);
    Payment result = Serializer.deserializeFromJson(Payment.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for cancel payment.
   *
   * @param id paymentId
   * @return payment
   * @throws MPException exception
   */
  public Payment cancel(Long id) throws MPException {
    LOGGER.info("Sending cancel payment request");
    PaymentCancelRequest payload = new PaymentCancelRequest();
    MPResponse response =
        send(
            String.format(URL_WITH_ID, id.toString()),
            HttpMethod.PUT,
            Serializer.serializeToJson(payload),
            new HashMap<>());

    Payment result = Serializer.deserializeFromJson(Payment.class, response.getContent());
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
    return capture(id, null);
  }

  /**
   * Method responsible for capture payment.
   *
   * @param id paymentId
   * @param amount amount
   * @return payment
   * @throws MPException exception
   */
  public Payment capture(Long id, BigDecimal amount) throws MPException {
    LOGGER.info("Sending capture payment request");
    PaymentCaptureRequest payload =
        PaymentCaptureRequest.builder().transactionAmount(amount).build();

    MPResponse response =
        send(
            String.format(URL_WITH_ID, id.toString()),
            HttpMethod.PUT,
            Serializer.serializeToJson(payload),
            new HashMap<>());

    Payment result = Serializer.deserializeFromJson(Payment.class, response.getContent());
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
  public ResultsResourcesPage<Payment> search(SearchRequest request) throws MPException {
    MPResponse response = send("/v1/payments/search", HttpMethod.GET, null, new HashMap<>());

    ResultsResourcesPage<Payment> result =
        Serializer.deserializeFromJsonToResultsResources(Payment.class, response.getContent());
    result.setResponse(response);

    return result;
  }
}
