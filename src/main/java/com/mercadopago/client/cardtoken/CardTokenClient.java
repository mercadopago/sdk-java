package com.mercadopago.client.cardtoken;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.CardToken;
import com.mercadopago.serialization.Serializer;

/** Client for retrieving the token for a card. */
public class CardTokenClient extends MercadoPagoClient {
  /** Default constructor. Uses http client provided by MercadoPagoConfig. */
  public CardTokenClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * CardTokenClient constructor.
   *
   * @param httpClient http client
   */
  public CardTokenClient(MPHttpClient httpClient) {
    super(httpClient);
  }

  /**
   * Get card token.
   *
   * @param id card id
   * @return card token
   */
  public CardToken get(String id) throws MPException {
    return this.get(id, null);
  }

  /**
   * Get card token.
   *
   * @param id card id
   * @param requestOptions metadata to customize the request
   * @return card token
   */
  public CardToken get(String id, MPRequestOptions requestOptions) throws MPException {
    MPResponse response =
        send(
            String.format("/v1/card_tokens/%s", id),
            HttpMethod.GET,
            null,
            null,
            requestOptions);
    CardToken cardToken = Serializer.deserializeFromJson(CardToken.class, response.getContent());
    cardToken.setResponse(response);
    return cardToken;
  }

  /**
   * Create token associated with a card.
   *
   * @param request attributes used to perform the request
   * @return card token
   * @throws MPException any error creating the customer card
   */
  public CardToken create(CardTokenRequest request)
      throws MPException {
    return this.create(request, null);
  }

  /**
   * Create token associated with a card.
   *
   * @param request attributes used to perform the request
   * @param requestOptions metadata to customize the request
   * @return card token
   * @throws MPException any error creating the customer card
   */
  public CardToken create(CardTokenRequest request, MPRequestOptions requestOptions)
      throws MPException {
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
