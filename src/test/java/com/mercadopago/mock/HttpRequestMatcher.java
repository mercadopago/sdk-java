package com.mercadopago.mock;

import org.apache.http.client.methods.HttpRequestBase;
import org.mockito.ArgumentMatcher;

public class HttpRequestMatcher implements ArgumentMatcher<HttpRequestBase> {
  private final HttpRequestBase httpRequest;

  public HttpRequestMatcher(HttpRequestBase httpRequest) {
    this.httpRequest = httpRequest;
  }

  @Override
  public boolean matches(HttpRequestBase other) {
    return other != null && httpRequest.getURI().compareTo(other.getURI()) == 0;
  }
}