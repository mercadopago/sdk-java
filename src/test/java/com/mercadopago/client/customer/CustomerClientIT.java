package com.mercadopago.client.customer;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.cardtoken.CardTokenTestClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** CustomerClientIT class. */
public class CustomerClientIT extends BaseClientIT {
  private final CustomerClient customerClient = new CustomerClient();

  private final CardTokenTestClient cardTokenTestClient = new CardTokenTestClient();

  @Test
  public void getSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);

      Customer customer = customerClient.get(createdCustomer.getId());

      assertNotNull(customer.getResponse());
      assertEquals(OK, customer.getResponse().getStatusCode());
      assertEquals(createdCustomer.getId(), customer.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);

      Customer customer = customerClient.get(createdCustomer.getId(), buildRequestOptions());

      assertNotNull(customer.getResponse());
      assertEquals(OK, customer.getResponse().getStatusCode());
      assertEquals(createdCustomer.getId(), customer.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();

      Customer customer = customerClient.create(request);

      assertNotNull(customer.getResponse());
      assertEquals(CREATED, customer.getResponse().getStatusCode());
      assertNotNull(customer.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();

      Customer customer = customerClient.create(request, buildRequestOptions());

      assertNotNull(customer.getResponse());
      assertEquals(CREATED, customer.getResponse().getStatusCode());
      assertNotNull(customer.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void updateSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);

      CustomerRequest updateRequest =
          CustomerRequest.builder().firstName("John").lastName("Doe").build();

      Customer customer = customerClient.update(createdCustomer.getId(), updateRequest);
      assertNotNull(customer.getResponse());
      assertEquals(OK, customer.getResponse().getStatusCode());
      assertNotNull(customer.getId());
      assertEquals("John", customer.getFirstName());
      assertEquals("Doe", customer.getLastName());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void updateWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);

      CustomerRequest updateRequest =
          CustomerRequest.builder().firstName("John").lastName("Doe").build();

      Customer customer =
          customerClient.update(createdCustomer.getId(), updateRequest, buildRequestOptions());
      assertNotNull(customer.getResponse());
      assertEquals(OK, customer.getResponse().getStatusCode());
      assertNotNull(customer.getId());
      assertEquals("John", customer.getFirstName());
      assertEquals("Doe", customer.getLastName());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void deleteSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);

      Customer customer = customerClient.delete(createdCustomer.getId());

      assertNotNull(customer.getResponse());
      assertEquals(OK, customer.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void deleteWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);

      Customer customer = customerClient.delete(createdCustomer.getId(), buildRequestOptions());

      assertNotNull(customer.getResponse());
      assertEquals(OK, customer.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void searchSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);

      Map<String, Object> filters = new HashMap<>();
      filters.put("email", createdCustomer.getEmail());

      MPSearchRequest searchRequest =
          MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();

      MPResultsResourcesPage<Customer> result = customerClient.search(searchRequest);

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getCardSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
      CustomerCard createdCard =
          customerClient.createCard(createdCustomer.getId(), customerCardCreateRequest);

      CustomerCard card = customerClient.getCard(createdCustomer.getId(), createdCard.getId());

      assertNotNull(card.getResponse());
      assertEquals(OK, card.getResponse().getStatusCode());
      assertEquals(createdCard.getId(), card.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getCardWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
      CustomerCard createdCard =
          customerClient.createCard(createdCustomer.getId(), customerCardCreateRequest);

      CustomerCard card =
          customerClient.getCard(
              createdCustomer.getId(), createdCard.getId(), buildRequestOptions());

      assertNotNull(card.getResponse());
      assertEquals(OK, card.getResponse().getStatusCode());
      assertEquals(createdCard.getId(), card.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createCardSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();

      CustomerCard card =
          customerClient.createCard(createdCustomer.getId(), customerCardCreateRequest);

      assertNotNull(card.getResponse());
      assertEquals(CREATED, card.getResponse().getStatusCode());
      assertNotNull(card.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createCardWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();

      CustomerCard card =
          customerClient.createCard(
              createdCustomer.getId(), customerCardCreateRequest, buildRequestOptions());

      assertNotNull(card.getResponse());
      assertEquals(CREATED, card.getResponse().getStatusCode());
      assertNotNull(card.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void deleteCardSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
      CustomerCard createdCard =
          customerClient.createCard(createdCustomer.getId(), customerCardCreateRequest);

      CustomerCard card = customerClient.deleteCard(createdCustomer.getId(), createdCard.getId());

      assertNotNull(card.getResponse());
      assertEquals(OK, card.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void deleteCardWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
      CustomerCard createdCard =
          customerClient.createCard(createdCustomer.getId(), customerCardCreateRequest);

      CustomerCard card =
          customerClient.deleteCard(
              createdCustomer.getId(), createdCard.getId(), buildRequestOptions());

      assertNotNull(card.getResponse());
      assertEquals(OK, card.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void listCardsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
      customerClient.createCard(createdCustomer.getId(), customerCardCreateRequest);

      MPResourceList<CustomerCard> cards = customerClient.listCards(createdCustomer.getId());

      assertNotNull(cards.getResponse());
      assertEquals(OK, cards.getResponse().getStatusCode());
      assertFalse(cards.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void listCardsWithRequestOptionsSuccess() {
    try {
      CustomerRequest request = CustomerRequest.builder().email(generateTestEmail()).build();
      Customer createdCustomer = customerClient.create(request);
      CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");
      CustomerCardCreateRequest customerCardCreateRequest =
          CustomerCardCreateRequest.builder().token(cardToken.getId()).build();
      customerClient.createCard(createdCustomer.getId(), customerCardCreateRequest);

      MPResourceList<CustomerCard> cards =
          customerClient.listCards(createdCustomer.getId(), buildRequestOptions());

      assertNotNull(cards.getResponse());
      assertEquals(OK, cards.getResponse().getStatusCode());
      assertFalse(cards.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }
}
