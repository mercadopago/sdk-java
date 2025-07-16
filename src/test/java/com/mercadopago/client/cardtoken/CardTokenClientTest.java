package com.mercadopago.client.cardtoken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.CardToken;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** CardTokenClientTest class. */
public class CardTokenClientTest extends BaseClientTest {
  private final String responseFileCardToken = "/cardtoken/card_token_base.json";

  private final String cardId = "1562188766852";

  private final CardTokenClient tokenClient = new CardTokenClient();

  @Test
  public void getCardTokenSuccess()
      throws IOException, MPException, MPApiException, ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileCardToken, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.get(cardId);

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  @Test
  public void getCardTokenWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException, ParseException {
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
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.get(cardId, requestOptions);

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  @Test
  public void createCardTokenSuccess()
      throws IOException, MPException, MPApiException, ParseException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(responseFileCardToken, HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.create(buildCardTokenRequest());

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  @Test
  public void createCardTokenWithRequestOptionsSuccess()
      throws ParseException, IOException, MPException, MPApiException {
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
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CardToken token = tokenClient.create(buildCardTokenRequest(), requestOptions);

    assertNotNull(token);
    assertCardTokenFields(token);
  }

  private CardTokenRequest buildCardTokenRequest() {
    return CardTokenRequest.builder()
        .cardId(cardId)
        .customerId("649457098-FybpOkG6zH8QRm")
        .securityCode("456")
        .build();
  }

  private void assertCardTokenFields(CardToken token) {
    assertEquals("97849c845e879427b5cb1cb941a52806", token.getId());
    assertEquals("989192037129", token.getCardId());
    assertEquals("503143", token.getFirstSixDigits());
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

    assertEquals(
        OffsetDateTime.of(2022, 1, 24, 8, 3, 55, 227000000, ZoneOffset.ofHours(-4)),
        token.getDateCreated());
    assertEquals(
        OffsetDateTime.of(2022, 1, 24, 8, 3, 55, 227000000, ZoneOffset.ofHours(-4)),
        token.getDateLastUpdated());
    assertEquals(
        OffsetDateTime.of(2022, 2, 1, 8, 3, 55, 227000000, ZoneOffset.ofHours(-4)),
        token.getDateDue());
    assertNotNull(token.getResponse().getContent());
    assertEquals(HttpStatus.OK, token.getResponse().getStatusCode());
    assertEquals(1, token.getResponse().getHeaders().size());
  }
}
