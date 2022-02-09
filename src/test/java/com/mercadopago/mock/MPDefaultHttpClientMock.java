package com.mercadopago.mock;

import com.mercadopago.net.MPDefaultHttpClient;
import org.apache.http.client.HttpClient;

/** MPDefaultHttpClientMock class. */
public class MPDefaultHttpClientMock extends MPDefaultHttpClient {

  /**
   * MPDefaultHttpClientMock constructor.
   *
   * @param httpClientMock httpClientMock
   */
  public MPDefaultHttpClientMock(HttpClient httpClientMock) {
    super(httpClientMock);
  }
}
