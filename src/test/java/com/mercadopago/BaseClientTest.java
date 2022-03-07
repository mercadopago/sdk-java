package com.mercadopago;

import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.mock.HttpClientMock;
import com.mercadopago.mock.MPDefaultHttpClientMock;
import java.util.TimeZone;
import org.apache.http.client.HttpClient;
import org.junit.jupiter.api.BeforeAll;

/** BaseClientTest class. */
public abstract class BaseClientTest {
  protected static final String APPLICATION_JSON = "application/json";

  protected static final HttpClientMock HTTP_CLIENT_MOCK = new HttpClientMock();

  protected static final HttpClient HTTP_CLIENT = HTTP_CLIENT_MOCK.getHttpClient();

  protected static final int DEFAULT_TIMEOUT = 1000;

  @BeforeAll
  static void setup() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    String accessToken = "token";
    MercadoPagoConfig.setAccessToken(accessToken);
    MercadoPagoConfig.setHttpClient(new MPDefaultHttpClientMock(HTTP_CLIENT_MOCK));
  }

  protected static MPRequestOptions buildRequestOptions() {
    return MPRequestOptions.builder()
        .accessToken("abc")
        .connectionTimeout(DEFAULT_TIMEOUT)
        .connectionRequestTimeout(DEFAULT_TIMEOUT)
        .socketTimeout(DEFAULT_TIMEOUT)
        .build();
  }
}
