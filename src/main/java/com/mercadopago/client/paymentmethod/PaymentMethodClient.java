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

/** Client with methods of Payment Method APIs. */
public class PaymentMethodClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PaymentMethodClient.class.getName());

  /** Default constructor. Uses the default http client used by the SDK. */
  public PaymentMethodClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
   */
  public PaymentMethodClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * List all payment methods.
   *
   * @return list of payment methods
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/payment_methods/_payment_methods/get">api
   *     docs</a>
   */
  public MPResourceList<PaymentMethod> list() throws MPException, MPApiException {
    return this.list(null);
  }

  /**
   * List all payment methods.
   *
   * @param requestOptions metadata to customize the request
   * @return list of payment methods
   * @throws MPException an error if the request fails
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
