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
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** CardTokenTestClient class. */
public class CardTokenTestClient extends MercadoPagoClient {

  private final Map<String, String> status =
      Stream.of(
              new AbstractMap.SimpleImmutableEntry<>("approved", "APRO"),
              new AbstractMap.SimpleImmutableEntry<>("pending", "CONT"),
              new AbstractMap.SimpleImmutableEntry<>("rejected", "OTHE"))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

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
  public CardToken createTestCardToken(String paymentStatus) throws MPException, MPApiException {

    CardTokenTestCreateRequest request =
        CardTokenTestCreateRequest.builder()
            .cardNumber("5031433215406351")
            .expirationYear(2099)
            .expirationMonth(12)
            .securityCode("123")
            .cardholder(
                CardTokenCardholderTestCreateRequest.builder()
                    .identification(
                        IdentificationRequest.builder().type("CPF").number("19119119100").build())
                    .name(status.get(paymentStatus))
                    .build())
            .build();

    MPResponse response =
        send("/v1/card_tokens", HttpMethod.POST, Serializer.serializeToJson(request), null, null);
    CardToken cardToken = Serializer.deserializeFromJson(CardToken.class, response.getContent());
    cardToken.setResponse(response);
    return cardToken;
  }
}
