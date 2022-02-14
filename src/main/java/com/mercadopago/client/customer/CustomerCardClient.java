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

/** Client responsible for performing customer card actions. */
public class CustomerCardClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(CustomerCardClient.class.getName());

  /** Default constructor. Uses the default http client used by the SDK */
  public CustomerCardClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient http client used for performing requests
   */
  public CustomerCardClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Get card of customer.
   *
   * @param customerId id of the customer to which the card belongs
   * @param cardId id of the card being requested
   * @return the requested customer card
   * @throws MPException any error retrieving the customer card
   */
  public CustomerCard get(String customerId, String cardId) throws MPException, MPApiException {
    return this.get(customerId, cardId, null);
  }

  /**
   * Get card of customer.
   *
   * @param customerId id of the customer
   * @param cardId id of the card being retrieved
   * @param requestOptions metadata to customize the request
   * @return customer card retrieved
   * @throws MPException any error retrieving the customer card
   */
  public CustomerCard get(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPResponse response =
        send(
            String.format("/v1/customers/%s/cards/%s", customerId, cardId),
            HttpMethod.GET,
            null,
            null,
            requestOptions);

    CustomerCard card = Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    card.setResponse(response);
    return card;
  }

  /**
   * Add card for customer.
   *
   * @param customerId id of the customer
   * @param request attributes used to perform the request
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public CustomerCard create(String customerId, CustomerCardCreateRequest request)
      throws MPException, MPApiException {
    return this.create(customerId, request, null);
  }

  /**
   * Add card for customer.
   *
   * @param customerId id of the customer
   * @param request attributes used to perform the request
   * @param requestOptions metadata to customize the request
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public CustomerCard create(
      String customerId, CustomerCardCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create customer card request");
    JsonObject payload = Serializer.serializeToJson(request);
    MPRequest mpRequest =
        MPRequest.buildRequest(
            String.format("/v1/customers/%s/cards", customerId),
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
   * Remove card for customer.
   *
   * @param customerId id of the customer
   * @param cardId id of the card being removed
   * @return the customer card just removed
   * @throws MPException any error removing the customer card
   */
  public CustomerCard delete(String customerId, String cardId) throws MPException, MPApiException {
    return this.delete(customerId, cardId, null);
  }

  /**
   * Remove card for customer.
   *
   * @param customerId id of the customer
   * @param cardId id of the card being retrieved
   * @param requestOptions metadata to customize the request
   * @return the customer card just removed
   * @throws MPException any error removing the customer card
   */
  public CustomerCard delete(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete customer card request");

    MPResponse response =
        send(
            String.format("/v1/customers/%s/cards/%s", customerId, cardId),
            HttpMethod.DELETE,
            null,
            null,
            requestOptions);

    CustomerCard card = Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    card.setResponse(response);
    return card;
  }

  /**
   * List all cards of customer.
   *
   * @param customerId id of the customer
   * @return list of customer cards retrieved
   * @throws MPException any error listing customer cards
   */
  public MPResourceList<CustomerCard> listAll(String customerId)
      throws MPException, MPApiException {
    return this.listAll(customerId, null);
  }

  /**
   * List all cards of customer.
   *
   * @param customerId id of the customer
   * @param requestOptions metadata to customize the request
   * @return list of customer cards retrieved
   * @throws MPException any error listing customer cards
   */
  public MPResourceList<CustomerCard> listAll(String customerId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending list all customer cards request");
    MPResponse response =
        list(
            String.format("/v1/customers/%s/cards", customerId),
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
