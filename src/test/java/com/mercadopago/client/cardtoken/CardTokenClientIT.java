package com.mercadopago.client.cardtoken;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.client.customer.CustomerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import org.apache.http.ParseException;
import org.junit.jupiter.api.Test;

/** CardTokenClientTest class. */
public class CardTokenClientIT extends BaseClientIT {
  private final CustomerClient customerClient = new CustomerClient();
  private final CardTokenClient tokenClient = new CardTokenClient();
  private final CardTokenTestClient cardTokenTestClient = new CardTokenTestClient();

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

    CardToken createdCardToken = cardTokenTestClient.createTestCardToken();
    CardToken token = tokenClient.get(createdCardToken.getId(), buildRequestOptions());

    assertNotNull(token);
    assertEquals(OK, token.getResponse().getStatusCode());
    assertEquals(createdCardToken.getId(), token.getId());
  }

  @Test
  public void createCardTokenSuccess() throws MPException, MPApiException, ParseException {
    CustomerRequest customerRequest = buildCustomerRequest();
    Customer customer = customerClient.create(customerRequest);

    try {
      CustomerCardCreateRequest cardCreateRequest = buildCardCreateRequest();
      CustomerCard customerCard = customerClient.createCard(customer.getId(), cardCreateRequest);

      CardTokenRequest cardTokenRequest =
          CardTokenRequest.builder()
              .cardId(customerCard.getId())
              .customerId(customer.getId())
              .securityCode("123")
              .build();

      CardToken token = tokenClient.create(cardTokenRequest);

      assertNotNull(token);
      assertNotNull(token.getResponse());
      assertEquals(CREATED, token.getResponse().getStatusCode());
      assertNotNull(token.getId());
      assertEquals(customerCard.getId(), token.getCardId());
    } finally {
      customerClient.delete(customer.getId());
    }
  }

  @Test
  public void createCardTokenWithRequestOptionsSuccess()
      throws ParseException, MPException, MPApiException {
    CustomerRequest customerRequest = buildCustomerRequest();
    Customer customer = customerClient.create(customerRequest);

    try {
      CustomerCardCreateRequest cardCreateRequest = buildCardCreateRequest();
      CustomerCard customerCard = customerClient.createCard(customer.getId(), cardCreateRequest);

      CardTokenRequest cardTokenRequest =
          CardTokenRequest.builder()
              .cardId(customerCard.getId())
              .customerId(customer.getId())
              .securityCode("123")
              .build();

      CardToken token = tokenClient.create(cardTokenRequest, buildRequestOptions());
      assertNotNull(token);
      assertNotNull(token.getResponse());
      assertEquals(CREATED, token.getResponse().getStatusCode());
      assertNotNull(token.getId());
      assertEquals(customerCard.getId(), token.getCardId());
    } finally {
      customerClient.delete(customer.getId());
    }
  }

  private MPRequestOptions buildRequestOptions() {
    return MPRequestOptions.builder()
        .connectionTimeout(DEFAULT_TIMEOUT)
        .connectionRequestTimeout(DEFAULT_TIMEOUT)
        .socketTimeout(DEFAULT_TIMEOUT)
        .build();
  }

  private CustomerCardCreateRequest buildCardCreateRequest() throws MPException, MPApiException {
    CardToken cardToken = cardTokenTestClient.createTestCardToken();
    return CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
  }

  private CustomerRequest buildCustomerRequest() {
    String email = generateTestEmail();
    return CustomerRequest.builder().email(email).build();
  }

  private String generateTestEmail() {
    int minValue = 10000000;
    int maxValue = 99999999;
    int complement = (int) ((Math.random() * (maxValue - minValue)) + minValue);
    return String.format("test_user_%s@testuser.com", complement);
  }
}
