package mercadopago.client.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.mercadopago.client.customer.CustomerCardClient;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.customer.CustomerCard;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import mercadopago.helper.MockHelper;
import mercadopago.mock.MPDefaultHttpClientMock;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerCardClientTest {
  private HttpClient httpClientMock;

  private MPDefaultHttpClientMock mpHttpClient;

  private CustomerCardClient cardClient;

  private String cardId;

  private String customerId;

  private String responseFileSingleCard;

  private String responseFileAllCards;

  @BeforeEach
  public void init() {
    this.httpClientMock = mock(HttpClient.class);
    this.mpHttpClient = new MPDefaultHttpClientMock(httpClientMock);
    this.cardClient = new CustomerCardClient(mpHttpClient);
    this.cardId = "1562188766852";
    this.customerId = "649457098-FybpOkG6zH8QRm";
    this.responseFileSingleCard = "/card/card_single.json";
    this.responseFileAllCards = "/card/card_all.json";
  }

  @Test
  public void getCardSuccess() throws IOException, MPException, ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.get(customerId, cardId);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void getCardWithRequestOptionsSuccess() throws IOException, MPException, ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .setAccessToken("abc")
            .setConnectionTimeout(1000)
            .setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = cardClient.get(customerId, cardId, requestOptions);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void createCardSuccess() throws IOException, MPException, ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCardCreateRequest request = new CustomerCardCreateRequest("abc");
    CustomerCard card = cardClient.create(customerId, request);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void createCardWithRequestOptionsSuccess()
      throws ParseException, IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .setAccessToken("abc")
            .setConnectionTimeout(1000)
            .setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCardCreateRequest request = new CustomerCardCreateRequest("abc");
    CustomerCard card = cardClient.create(customerId, request, requestOptions);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void deleteCardSuccess() throws ParseException, MPException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCardCreateRequest request = new CustomerCardCreateRequest("abc");
    CustomerCard card = cardClient.delete(customerId, cardId);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void deleteCardWithRequestOptionsSuccess()
      throws MPException, IOException, ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .setAccessToken("abc")
            .setConnectionTimeout(1000)
            .setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileSingleCard, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCardCreateRequest request = new CustomerCardCreateRequest("abc");
    CustomerCard card = cardClient.delete(customerId, cardId, requestOptions);

    assertNotNull(card);
    assertCustomerCardFields(card);
  }

  @Test
  public void listAllCardsSuccess() throws IOException, MPException, ParseException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFileAllCards, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCardCreateRequest request = new CustomerCardCreateRequest("abc");
    MPResourceList<CustomerCard> cards = cardClient.listAll(customerId);

    assertNotNull(cards);
    assertEquals(1, cards.size());
    assertCustomerCardFields(cards.get(0));
  }

  @Test
  public void listAllCardsWithRequestOptionsSuccess()
      throws IOException, MPException, ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .setAccessToken("abc")
            .setConnectionTimeout(1000)
            .setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000)
            .build();
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFileAllCards, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCardCreateRequest request = new CustomerCardCreateRequest("abc");
    MPResourceList<CustomerCard> cards = cardClient.listAll(customerId, requestOptions);

    assertNotNull(cards);
    assertEquals(1, cards.size());
    assertCustomerCardFields(cards.get(0));
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
    assertEquals(25, card.getIssuer().getId());
    assertEquals("Visa", card.getIssuer().getName());
    assertEquals("APRO", card.getCardholder().getName());
    assertEquals("19119119100", card.getCardholder().getIdentification().getNumber());
    assertEquals("CPF", card.getCardholder().getIdentification().getType());

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    assertEquals(df.parse("2019-07-03T21:15:35.000Z"), card.getDateCreated());
    assertEquals(df.parse("2019-07-03T21:19:18.000Z"), card.getDateLastUpdated());
    assertEquals("649457098-FybpOkG6zH8QRm", card.getCustomerId());
  }
}
