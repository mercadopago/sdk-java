package mercadopago.mock;

import org.apache.http.client.methods.HttpRequestBase;
import org.mockito.ArgumentMatcher;

public class HttpRequestMatcher extends ArgumentMatcher<HttpRequestBase> {
  private final HttpRequestBase httpRequest;

  public HttpRequestMatcher(HttpRequestBase httpRequest) {
    this.httpRequest = httpRequest;
  }

  @Override
  public boolean matches(Object other) {
    if (!(other instanceof HttpRequestBase)) {
      return false;
    }

    return httpRequest.getURI().compareTo(((HttpRequestBase) other).getURI()) == 0;
  }
}
