package com.mercadopago.client.cardtoken;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.customer.CustomerCard;
import org.apache.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** CardTokenClientTest class. */
public class CardTokenClientIT {
  private static final int DEFAULT_TIMEOUT = 2000;
  private final String customerId = "1005259837-aQFrILCS6bbxXn";
  private final String accessToken =
      "APP_USR-558881221729581-091712-44fdc612e60e3e638775d8b4003edd51-471763966";
  private CustomerClient customerClient;
  private CardTokenClient tokenClient;
  private CardTokenTestClient cardTokenTestClient;

  /** Init method. */
  @BeforeEach
  public void init() {
    MercadoPagoConfig.setAccessToken(accessToken);
    this.tokenClient = new CardTokenClient();
    this.customerClient = new CustomerClient();
    this.cardTokenTestClient = new CardTokenTestClient();
  }

  @Test
  public void getCardTokenSuccess() throws MPException, MPApiException, ParseException {

    CardToken createdCardToken = cardTokenTestClient.createTestCardToken();
    CardToken token = tokenClient.get(createdCardToken.getId());

    assertNotNull(token);
    assertEquals(OK, token.getResponse().getStatusCode());
    assertEquals(createdCardToken.getId(), token.getId());
  }

  @Test
  public void getCardTokenWithRequestOptionsSuccess()
      throws MPException, MPApiException, ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    CardToken createdCardToken = cardTokenTestClient.createTestCardToken();
    CardToken token = tokenClient.get(createdCardToken.getId(), requestOptions);

    assertNotNull(token);
    assertEquals(OK, token.getResponse().getStatusCode());
    assertEquals(createdCardToken.getId(), token.getId());
  }

  @Test
  public void createCardTokenSuccess() throws MPException, MPApiException, ParseException {

    CustomerCardCreateRequest cardCreateRequest = buildCardCreateRequest();
    CustomerCard customerCard = customerClient.createCard(customerId, cardCreateRequest);

    CardTokenRequest cardTokenRequest =
        CardTokenRequest.builder()
            .cardId(customerCard.getId())
            .customerId(customerId)
            .securityCode("123")
            .build();

    CardToken token = tokenClient.create(cardTokenRequest);

    assertNotNull(token);
    assertNotNull(token.getResponse());
    assertEquals(CREATED, token.getResponse().getStatusCode());
    assertNotNull(token.getId());
    assertEquals(customerCard.getId(), token.getCardId());
  }

  @Test
  public void createCardTokenWithRequestOptionsSuccess()
      throws ParseException, MPException, MPApiException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken(accessToken)
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    CustomerCardCreateRequest cardCreateRequest = buildCardCreateRequest();
    CustomerCard customerCard = customerClient.createCard(customerId, cardCreateRequest);

    CardTokenRequest cardTokenRequest =
        CardTokenRequest.builder()
            .cardId(customerCard.getId())
            .customerId(customerId)
            .securityCode("123")
            .build();

    CardToken token = tokenClient.create(cardTokenRequest, requestOptions);
    assertNotNull(token);
    assertNotNull(token.getResponse());
    assertEquals(CREATED, token.getResponse().getStatusCode());
    assertNotNull(token.getId());
    assertEquals(customerCard.getId(), token.getCardId());
  }

  private CustomerCardCreateRequest buildCardCreateRequest() throws MPException, MPApiException {
    CardToken cardToken = cardTokenTestClient.createTestCardToken();
    return CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
  }
}
