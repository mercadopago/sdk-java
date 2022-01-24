package mercadopago.client.cardtoken;

import static org.apache.maven.plugins.javadoc.JavadocUtil.DEFAULT_TIMEOUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.mercadopago.client.cardtoken.CardTokenClient;
import com.mercadopago.client.cardtoken.CardTokenRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.CardToken;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import mercadopago.helper.MockHelper;
import mercadopago.mock.MPDefaultHttpClientMock;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTokenClientTest {
  private static final String APPLICATION_JSON = "application/json";

  private HttpClient httpClientMock;

  private CardTokenClient tokenClient;

  private String cardId;

  private String responseFileCardToken;

  private CardTokenRequest cardTokenRequest;

  @BeforeEach
  public void init() {
    this.httpClientMock = mock(HttpClient.class);
    MPDefaultHttpClientMock mpHttpClient = new MPDefaultHttpClientMock(httpClientMock);
    this.tokenClient = new CardTokenClient(mpHttpClient);
    this.cardId = "1562188766852";
    this.responseFileCardToken = "/cardtoken/card_token_base.json";
    this.cardTokenRequest =
        CardTokenRequest.builder()
            .cardId(cardId)
            .customerId("649457098-FybpOkG6zH8QRm")
            .securityCode("456")
            .build();
  }

  @Test
  public void getCardTokenSuccess() throws IOException, MPException, ParseException, java.text.ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileCardToken, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.get(cardId);

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  @Test
  public void getCardTokenWithRequestOptionsSuccess()
      throws IOException, MPException, ParseException, java.text.ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileCardToken, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.get(cardId, requestOptions);

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  @Test
  public void createCardTokenSuccess() throws IOException, MPException, ParseException, java.text.ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileCardToken, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.create(cardTokenRequest);

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  @Test
  public void createCardTokenWithRequestOptionsSuccess()
      throws ParseException, IOException, MPException, java.text.ParseException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileCardToken, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.create(cardTokenRequest, requestOptions);

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  private void assertCardTokenFields(CardToken token) throws java.text.ParseException {
    assertEquals("97849c845e879427b5cb1cb941a52806", token.getId());
    assertEquals("503143",token.getFirstSixDigits());
    assertEquals(11, token.getExpirationMonth());
    assertEquals(2025, token.getExpirationYear());
    assertEquals("6351", token.getLastFourDigits());
    assertEquals("57096407006", token.getCardholder().getIdentification().getNumber());
    assertEquals("CPF", token.getCardholder().getIdentification().getType());
    assertEquals("APRO", token.getCardholder().getName());
    assertEquals("active", token.getStatus());
    assertTrue(token.getLuhnValidation());
    assertFalse(token.getRequireEsc());
    assertTrue(token.getLiveMode());
    assertEquals(16, token.getCardNumberLength());
    assertEquals(3, token.getSecurityCodeLength());

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    assertEquals(df.parse("2022-01-24T08:03:55.227-04:00"), token.getDateCreated());
    assertEquals(df.parse("2022-01-24T08:03:55.227-04:00"), token.getDateLastUpdated());
    assertEquals(df.parse("2022-02-01T08:03:55.227-04:00"), token.getDateDue());
    assertNotNull(token.getResponse().getContent());
    assertEquals(HttpStatus.OK, token.getResponse().getStatusCode());
    assertEquals(1, token.getResponse().getHeaders().size());
  }
}
