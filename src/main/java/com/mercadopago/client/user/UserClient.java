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

/**
 * Client for the MercadoPago Users API.
 *
 * <p>Retrieves information about the authenticated MercadoPago user, including country, site,
 * and account details. This client is used internally by {@link
 * com.mercadopago.client.oauth.OauthClient} to build country-specific authorization URLs.
 *
 * <p>Usage example:
 * <pre>{@code
 * UserClient client = new UserClient();
 * User user = client.get();
 * String country = user.getCountryId();
 * }</pre>
 */
public class UserClient extends MercadoPagoClient {

  /** Class-level logger for user operations. */
  private static final Logger LOGGER = Logger.getLogger(UserClient.class.getName());

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public UserClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code UserClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public UserClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves information about the currently authenticated user.
   *
   * @return the authenticated {@link User} with account details and country information
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public User get() throws MPException, MPApiException {
    return this.get(null);
  }

  /**
   * Retrieves information about the currently authenticated user with custom request options.
   *
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the authenticated {@link User} with account details and country information
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public User get(MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get user request");
    MPResponse response = send("/users/me", HttpMethod.GET, null, null, requestOptions);
    User user = Serializer.deserializeFromJson(User.class, response.getContent());
    user.setResponse(response);
    return user;
  }
}
