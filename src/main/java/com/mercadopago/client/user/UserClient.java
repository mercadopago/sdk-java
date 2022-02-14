package com.mercadopago.client.user;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.user.User;
import com.mercadopago.serialization.Serializer;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/** Client to get user information. */
public class UserClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(UserClient.class.getName());

  /** Default constructor. Uses the default http client used by the SDK */
  public UserClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient Http Client
   */
  public UserClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Get user information.
   *
   * @return user information
   * @throws MPException an error if the request fails
   */
  public User get() throws MPException, MPApiException {
    return this.get(null);
  }

  /**
   * Get user information with custom attributes on request.
   *
   * @param requestOptions metadata to customize the request
   * @return user information
   * @throws MPException an error if the request fails
   */
  public User get(MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get user request");
    MPResponse response = send("/users/me", HttpMethod.GET, null, null, requestOptions);
    User user = Serializer.deserializeFromJson(User.class, response.getContent());
    user.setResponse(response);
    return user;
  }
}
