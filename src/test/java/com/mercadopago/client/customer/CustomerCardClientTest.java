package com.mercadopago.client.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.resources.customer.CustomerCardIssuer;
import java.io.IOException;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** CustomerCardClientTest class. */
public class CustomerCardClientTest extends BaseClientTest {
  private static final String APPLICATION_JSON = "application/json";

  private static final int DEFAULT_TIMEOUT = 1000;

  private final CustomerCardClient cardClient = new CustomerCardClient();

  private String cardId;

  private String customerId;

  private String responseFileSingleCard;

  private String responseFileAllCards;

  private CustomerCardCreateRequest customerCardCreateRequest;

  /** Init method. */
  @BeforeEach
  public void init() {
    customerCardCreateRequest =
        CustomerCardCreateRequest.builder()
            .token("abc")
            .issuer(CustomerCardIssuer.builder().id("123").name("visa").build())
            .build();
    this.cardId = "1562188766852";
    this.customerId = "649457098-FybpOkG6zH8QRm";
    this.responseFileSingleCard = "/card/card_single.json";
    this.responseFileAllCards = "/card/card_all.json";
  }

  @Test
  public void getCardSuccess() throws IOException, MPException, ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.get(customerId, cardId);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void getCardWithRequestOptionsSuccess() throws IOException, MPException, ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.get(customerId, cardId, requestOptions);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void createCardSuccess() throws IOException, MPException, ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.create(customerId, customerCardCreateRequest);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void createCardWithRequestOptionsSuccess()
      throws ParseException, IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.create(customerId, customerCardCreateRequest, requestOptions);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void deleteCardSuccess() throws ParseException, MPException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.delete(customerId, cardId);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void deleteCardWithRequestOptionsSuccess()
      throws MPException, IOException, ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.delete(customerId, cardId, requestOptions);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void listAllCardsSuccess() throws IOException, MPException, ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileAllCards, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResourceList<CustomerCard> cards = cardClient.listAll(customerId);

    assertNotNull(cards);
    assertNotNull(cards.getResponse());
    assertEquals(HttpStatus.OK, cards.getResponse().getStatusCode());
    assertEquals(1, cards.getResponse().getHeaders().size());
    assertEquals(1, cards.getResults().size());
    assertCustomerCardFields(cards.getResults().get(0));
  }

  @Test
  public void listAllCardsWithRequestOptionsSuccess()
      throws IOException, MPException, ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileAllCards, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResourceList<CustomerCard> cards = cardClient.listAll(customerId, requestOptions);

    assertNotNull(cards);
    assertNotNull(cards.getResponse());
    assertEquals(HttpStatus.OK, cards.getResponse().getStatusCode());
    assertEquals(1, cards.getResponse().getHeaders().size());
    assertEquals(1, cards.getResults().size());
    assertCustomerCardFields(cards.getResults().get(0));
  }

  private void assertCustomerCardFields(CustomerCard card) throws ParseException {
    assertEquals("1562188766852", card.getId());
    assertEquals(6, card.getExpirationMonth());
    assertEquals(2023, card.getExpirationYear());
    assertEquals("423564", card.getFirstSixDigits());
    assertEquals("5682", card.getLastFourDigits());
    assertEquals("visa", card.getPaymentMethod().getId());
    assertEquals("visa", card.getPaymentMethod().getName());
    assertEquals("credit_card", card.getPaymentMethod().getPaymentTypeId());
    assertEquals(
        "http://img.mlstatic.com/org-img/MP3/API/logos/visa.gif",
        card.getPaymentMethod().getThumbnail());
    assertEquals(
        "https://www.mercadopago.com/org-img/MP3/API/logos/visa.gif",
        card.getPaymentMethod().getSecureThumbnail());
    assertEquals(3, card.getSecurityCode().getLength());
    assertEquals("back", card.getSecurityCode().getCardLocation());
    assertEquals("25", card.getIssuer().getId());
    assertEquals("Visa", card.getIssuer().getName());
    assertEquals("APRO", card.getCardholder().getName());
    assertEquals("19119119100", card.getCardholder().getIdentification().getNumber());
    assertEquals("CPF", card.getCardholder().getIdentification().getType());

    assertEquals(
        OffsetDateTime.of(2019, 7, 3, 21, 15, 35, 0, ZoneOffset.UTC), card.getDateCreated());
    assertEquals(
        OffsetDateTime.of(2019, 7, 3, 21, 19, 18, 0, ZoneOffset.UTC), card.getDateLastUpdated());
    assertEquals("649457098-FybpOkG6zH8QRm", card.getCustomerId());
    assertEquals("448870796", card.getUserId());
    assertTrue(card.isLiveMode());
  }
}
