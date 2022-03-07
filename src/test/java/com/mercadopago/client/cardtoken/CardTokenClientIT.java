package com.mercadopago.client.cardtoken;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.client.customer.CustomerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import org.junit.jupiter.api.Test;

/** CardTokenClientIT class. */
public class CardTokenClientIT extends BaseClientIT {
  private final CustomerClient customerClient = new CustomerClient();

  private final CardTokenClient tokenClient = new CardTokenClient();

  private final CardTokenTestClient cardTokenTestClient = new CardTokenTestClient();

  @Test
  public void getCardTokenSuccess() {
    try {
      CardToken createdCardToken = cardTokenTestClient.createTestCardToken("approved");
      CardToken token = tokenClient.get(createdCardToken.getId());

      assertNotNull(token);
      assertEquals(OK, token.getResponse().getStatusCode());
      assertEquals(createdCardToken.getId(), token.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getCardTokenWithRequestOptionsSuccess() {
    try {
      CardToken createdCardToken = cardTokenTestClient.createTestCardToken("approved");
      CardToken token = tokenClient.get(createdCardToken.getId(), buildRequestOptions());

      assertNotNull(token);
      assertEquals(OK, token.getResponse().getStatusCode());
      assertEquals(createdCardToken.getId(), token.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createCardTokenSuccess() {
    try {
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
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createCardTokenWithRequestOptionsSuccess() {
    try {
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
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  private CustomerCardCreateRequest buildCardCreateRequest() throws MPException, MPApiException {
    CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
    return CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
  }

  private CustomerRequest buildCustomerRequest() {
    String email = generateTestEmail();
    return CustomerRequest.builder().email(email).build();
  }
}
