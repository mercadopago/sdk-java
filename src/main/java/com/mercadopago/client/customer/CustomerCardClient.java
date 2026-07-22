package com.mercadopago.client.customer;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.google.gson.JsonObject;
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
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.serialization.Serializer;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Customer Cards API.
 *
 * <p>Provides operations to get, create, delete, and list cards associated with a customer.
 * Cards are stored securely and can be reused for future payments without requiring the payer
 * to re-enter card details.
 *
 * <p>This client is typically used internally by {@link CustomerClient}, but can also be
 * instantiated directly.
 *
 * @see CustomerClient
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards/get">
 *     Customer Cards API reference</a>
 */
public class CustomerCardClient extends MercadoPagoClient {

  /** Class-level logger for customer card operations. */
  private static final Logger LOGGER = Logger.getLogger(CustomerCardClient.class.getName());

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public CustomerCardClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code CustomerCardClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public CustomerCardClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a specific card belonging to a customer.
   *
   * @param customerId the unique identifier of the customer who owns the card
   * @param cardId the unique identifier of the card to retrieve
   * @return the requested {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CustomerCard get(String customerId, String cardId) throws MPException, MPApiException {
    return this.get(customerId, encodePathParam(cardId), null);
  }

  /**
   * Retrieves a specific card belonging to a customer with custom request options.
   *
   * @param customerId the unique identifier of the customer who owns the card
   * @param cardId the unique identifier of the card to retrieve
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CustomerCard get(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPResponse response =
        send(
            String.format("/v1/customers/%s/cards/%s", encodePathParam(customerId), encodePathParam(cardId)),
            HttpMethod.GET,
            null,
            null,
            requestOptions);

    CustomerCard card = Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    card.setResponse(response);
    return card;
  }

  /**
   * Creates and associates a new card with a customer.
   *
   * @param customerId the unique identifier of the customer
   * @param request the {@link CustomerCardCreateRequest} with the card token and other details
   * @return the newly created {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CustomerCard create(String customerId, CustomerCardCreateRequest request)
      throws MPException, MPApiException {
    return this.create(customerId, request, null);
  }

  /**
   * Creates and associates a new card with a customer with custom request options.
   *
   * @param customerId the unique identifier of the customer
   * @param request the {@link CustomerCardCreateRequest} with the card token and other details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the newly created {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CustomerCard create(
      String customerId, CustomerCardCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create customer card request");
    JsonObject payload = Serializer.serializeToJson(request);
    MPRequest mpRequest =
        MPRequest.buildRequest(
            String.format("/v1/customers/%s/cards", encodePathParam(customerId)),
            HttpMethod.POST,
            payload,
            null,
            requestOptions);
    MPResponse response = send(mpRequest);

    CustomerCard card = Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    card.setResponse(response);
    return card;
  }

  /**
   * Removes a card from a customer.
   *
   * @param customerId the unique identifier of the customer
   * @param cardId the unique identifier of the card to remove
   * @return the removed {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CustomerCard delete(String customerId, String cardId) throws MPException, MPApiException {
    return this.delete(customerId, encodePathParam(cardId), null);
  }

  /**
   * Removes a card from a customer with custom request options.
   *
   * @param customerId the unique identifier of the customer
   * @param cardId the unique identifier of the card to remove
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the removed {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public CustomerCard delete(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete customer card request");

    MPResponse response =
        send(
            String.format("/v1/customers/%s/cards/%s", encodePathParam(customerId), encodePathParam(cardId)),
            HttpMethod.DELETE,
            null,
            null,
            requestOptions);

    CustomerCard card = Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    card.setResponse(response);
    return card;
  }

  /**
   * Lists all cards belonging to a customer.
   *
   * @param customerId the unique identifier of the customer
   * @return an {@link MPResourceList} of {@link CustomerCard} for the given customer
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResourceList<CustomerCard> listAll(String customerId)
      throws MPException, MPApiException {
    return this.listAll(customerId, null);
  }

  /**
   * Lists all cards belonging to a customer with custom request options.
   *
   * @param customerId the unique identifier of the customer
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResourceList} of {@link CustomerCard} for the given customer
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResourceList<CustomerCard> listAll(String customerId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending list all customer cards request");
    MPResponse response =
        list(
            String.format("/v1/customers/%s/cards", encodePathParam(customerId)),
            HttpMethod.GET,
            null,
            null,
            requestOptions);

    MPResourceList<CustomerCard> cards =
        Serializer.deserializeListFromJson(CustomerCard.class, response.getContent());
    cards.setResponse(response);
    return cards;
  }
}
