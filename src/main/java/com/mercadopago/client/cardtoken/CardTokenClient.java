package com.mercadopago.client.cardtoken;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.CardToken;
import com.mercadopago.serialization.Serializer;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Card Tokens API.
 *
 * <p>Provides PCI-compliant card tokenization operations. Tokens represent card data securely
 * and are used when creating payments or saving cards to a customer profile.
 *
 * <p>Usage example:
 * <pre>{@code
 * CardTokenClient client = new CardTokenClient();
 * CardToken token = client.create(cardTokenRequest);
 * // Use token.getId() when creating a payment
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/card_tokens/_card_tokens/post">
 *     Card Tokens API reference</a>
 */
public class CardTokenClient extends MercadoPagoClient {

  /** Class-level logger for card token operations. */
  private static final Logger LOGGER = Logger.getLogger(CardTokenClient.class.getName());

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public CardTokenClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code CardTokenClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public CardTokenClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a card token by its unique identifier.
   *
   * @param id the unique identifier of the card token
   * @return the requested {@link CardToken}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CardToken get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a card token by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the card token
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link CardToken}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CardToken get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPResponse response =
        send(String.format("/v1/card_tokens/%s", id), HttpMethod.GET, null, null, requestOptions);
    CardToken cardToken = Serializer.deserializeFromJson(CardToken.class, response.getContent());
    cardToken.setResponse(response);
    return cardToken;
  }

  /**
   * Creates a new card token from card data.
   *
   * @param request the {@link CardTokenRequest} with card number, expiration, security code, and
   *     cardholder details
   * @return the created {@link CardToken} containing the token identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CardToken create(CardTokenRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new card token from card data with custom request options.
   *
   * @param request the {@link CardTokenRequest} with card number, expiration, security code, and
   *     cardholder details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link CardToken} containing the token identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CardToken create(CardTokenRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPResponse response =
        send(
            "/v1/card_tokens",
            HttpMethod.POST,
            Serializer.serializeToJson(request),
            null,
            requestOptions);
    CardToken cardToken = Serializer.deserializeFromJson(CardToken.class, response.getContent());
    cardToken.setResponse(response);
    return cardToken;
  }
}
