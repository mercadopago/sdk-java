package mercadopago.client.oauth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.oauth.OauthClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.oauth.OauthCredential;
import java.io.IOException;
import mercadopago.BaseClientTest;
import mercadopago.helper.MockHelper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

public class OauthClientTest extends BaseClientTest {
  private static final String APPLICATION_JSON = "application/json";

  private static final int DEFAULT_TIMEOUT = 2000;

  private static HttpResponse oauthHttpResponse;

  private final String appId = "123";

  private final String redirectUri = "https://redirect-uri.com";

  private final String authorizationCode = "authCode";

  private final String refreshToken = "refreshToken";

  private HttpResponse userHttpResponse;

  private class HttpRequestMatcher extends ArgumentMatcher<HttpRequestBase> {
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

  @Test
  public void getAuthorizationURLSuccess() throws IOException, MPException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromString(
            "https://mercadopago-redirect-uri.mercadopago.com", HttpStatus.OK);
    userHttpResponse =
        MockHelper.generateHttpResponseFromFile("/user/user_base.json", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(userHttpResponse)
        .when(httpClient)
        .execute(
            argThat(
                new HttpRequestMatcher(
                    new HttpGet(String.format("%s/users/me", MercadoPagoConfig.BASE_URL)))),
            any(HttpContext.class));
    doReturn(oauthHttpResponse)
        .when(httpClient)
        .execute(argThat(
            new HttpRequestMatcher(
                new HttpGet("https://auth.mercadopago.com/oauth/token"))), any(HttpContext.class));

    String authorizationURL = new OauthClient().getAuthorizationURL(appId, redirectUri);
    assertNotNull(authorizationURL);
  }

  @Test
  public void getAuthorizationURLWithRequestOptionsSuccess() throws MPException, IOException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromString(
            "https://mercadopago-redirect-uri.mercadopago.com", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    userHttpResponse =
        MockHelper.generateHttpResponseFromFile("/user/user_base.json", HttpStatus.OK);
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(userHttpResponse)
        .when(httpClient)
        .execute(
            argThat(
                new HttpRequestMatcher(
                    new HttpGet(String.format("%s/users/me", MercadoPagoConfig.BASE_URL)))),
            any(HttpContext.class));
    doReturn(oauthHttpResponse)
        .when(httpClient)
        .execute(argThat(
            new HttpRequestMatcher(
                new HttpGet("https://auth.mercadopago.com/oauth/token"))), any(HttpContext.class));

    String authorizationURL =
        new OauthClient().getAuthorizationURL(appId, redirectUri, requestOptions);
    assertNotNull(authorizationURL);
  }

  @Test
  public void createCredentialSuccess() throws IOException, MPException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromFile("/oauth/oauth_credential.json", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(oauthHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    OauthCredential credential = new OauthClient().createCredential(authorizationCode, redirectUri);
    assertNotNull(credential);
  }

  @Test
  public void createCredentialWithRequestOptionsSuccess() throws MPException, IOException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromFile("/oauth/oauth_credential.json", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(MercadoPagoConfig.getConnectionTimeout())
            .connectionRequestTimeout(MercadoPagoConfig.getConnectionRequestTimeout())
            .socketTimeout(MercadoPagoConfig.getSocketTimeout())
            .build();

    doReturn(oauthHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    OauthCredential credential =
        new OauthClient().createCredential(authorizationCode, redirectUri, requestOptions);
    assertNotNull(credential);
  }

  @Test
  public void refreshCredentialSuccess() throws IOException, MPException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromFile("/oauth/oauth_refresh_token.json", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(oauthHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    OauthCredential credential = new OauthClient().refreshCredential(refreshToken);
    assertNotNull(credential);
  }

  @Test
  public void refreshCredentialWithRequestOptionsSuccess() throws MPException, IOException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromFile("/oauth/oauth_refresh_token.json", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(MercadoPagoConfig.getConnectionTimeout())
            .connectionRequestTimeout(MercadoPagoConfig.getConnectionRequestTimeout())
            .socketTimeout(MercadoPagoConfig.getSocketTimeout())
            .build();

    doReturn(oauthHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    OauthCredential credential = new OauthClient().refreshCredential(refreshToken, requestOptions);
    assertNotNull(credential);
  }
}
