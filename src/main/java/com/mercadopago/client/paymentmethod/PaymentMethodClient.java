package com.mercadopago.client.paymentmethod;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeListFromJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
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

  /** PaymentMethodClient constructor. */
  public PaymentMethodClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * PaymentMethodClient constructor.
   *
   * @param httpClient httpClient
   */
  public PaymentMethodClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
  }

  /**
   * List all payment methods.
   *
   * @return list of payment methods
   * @throws MPException exception
   */
  public MPResourceList<PaymentMethod> list() throws MPException {
    return this.list(null);
  }

  /**
   * List all payment methods.
   *
   * @param requestOptions requestOptions
   * @return list of payment methods
   * @throws MPException exception
   */
  public MPResourceList<PaymentMethod> list(MPRequestOptions requestOptions) throws MPException {
    LOGGER.info("Sending list payment method");

    MPResponse response = list("/v1/payment_methods", HttpMethod.GET, null, null, requestOptions);

    MPResourceList<PaymentMethod> paymentMethods =
        deserializeListFromJson(PaymentMethod.class, response.getContent());
    paymentMethods.forEach(paymentMethod -> paymentMethod.setResponse(response));

    return paymentMethods;
  }
}
