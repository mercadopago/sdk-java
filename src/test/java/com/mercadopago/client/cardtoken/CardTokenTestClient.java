package com.mercadopago.client.cardtoken;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.CardToken;
import com.mercadopago.serialization.Serializer;

/** CardTokenTestClient class. */
public class CardTokenTestClient extends MercadoPagoClient {

  /** Default constructor. Uses http client provided by MercadoPagoConfig. */
  public CardTokenTestClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * MercadoPagoClient constructor.
   *
   * @param httpClient http client
   */
  public CardTokenTestClient(MPHttpClient httpClient) {
    super(httpClient);
  }

  /**
   * Method for creating test card token.
   *
   * @return Card token
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if api call fails
   */
  public CardToken createTestCardToken() throws MPException, MPApiException {

    CardTokenTestCreateRequest request =
        CardTokenTestCreateRequest.builder()
            .cardNumber("5031433215406351")
            .expirationYear(2025)
            .expirationMonth(12)
            .securityCode("123")
            .cardholder(
                CardTokenCardholderTestCreateRequest.builder()
                    .identification(
                        IdentificationRequest.builder().type("CPF").number("19119119100").build())
                    .name("APRO")
                    .build())
            .build();

    MPResponse response =
        send("/v1/card_tokens", HttpMethod.POST, Serializer.serializeToJson(request), null, null);
    CardToken cardToken = Serializer.deserializeFromJson(CardToken.class, response.getContent());
    cardToken.setResponse(response);
    return cardToken;
  }
}
