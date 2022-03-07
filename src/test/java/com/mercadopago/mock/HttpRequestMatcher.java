package com.mercadopago.mock;

import org.apache.http.client.methods.HttpRequestBase;
import org.mockito.ArgumentMatcher;

/** HttpRequestMatcher class. */
public class HttpRequestMatcher extends ArgumentMatcher<HttpRequestBase> {
  private final HttpRequestBase httpRequest;

  public HttpRequestMatcher(HttpRequestBase httpRequest) {
    super();
    this.httpRequest = httpRequest;
  }

  @Override
  public boolean matches(Object other) {
    return other instanceof HttpRequestBase
        && httpRequest.getURI().compareTo(((HttpRequestBase) other).getURI()) == 0;
  }
}
