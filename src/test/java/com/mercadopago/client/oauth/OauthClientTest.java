package com.mercadopago.client.oauth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.argThat;

import com.mercadopago.BaseClientTest;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.mock.HttpRequestMatcher;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.oauth.CreateOauthCredential;
import com.mercadopago.resources.oauth.RefreshOauthCredential;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** OauthClientTest class. */
public class OauthClientTest extends BaseClientTest {
  private static HttpResponse oauthHttpResponse;

  private final String appId = "123";

  private final String redirectUri = "https://redirect-uri.com";

  private final String authorizationCode = "authCode";

  private final String refreshToken = "refreshToken";

  private HttpResponse userHttpResponse;

  @Test
  public void getAuthorizationURLSuccess()
      throws IOException, MPException, MPApiException, URISyntaxException {
    userHttpResponse =
        MockHelper.generateHttpResponseFromFile("/user/user_base.json", HttpStatus.OK);

    doReturn(userHttpResponse)
        .when(HTTP_CLIENT)
        .execute(
            argThat(
                new HttpRequestMatcher(
                    new HttpGet(String.format("%s/users/me", MercadoPagoConfig.BASE_URL)))),
            any(HttpContext.class));
    doReturn(oauthHttpResponse)
        .when(HTTP_CLIENT)
        .execute(
            argThat(
                new HttpRequestMatcher(new HttpGet("https://auth.mercadopago.com/oauth/token"))),
            any(HttpContext.class));

    String authorizationURL = new OauthClient().getAuthorizationURL(appId, redirectUri);

    assertAuthorizationUrlComponents(authorizationURL);
  }

  @Test
  public void getAuthorizationURLWithRequestOptionsSuccess()
      throws MPException, MPApiException, IOException, URISyntaxException {
    userHttpResponse =
        MockHelper.generateHttpResponseFromFile("/user/user_base.json", HttpStatus.OK);

    doReturn(userHttpResponse)
        .when(HTTP_CLIENT)
        .execute(
            argThat(
                new HttpRequestMatcher(
                    new HttpGet(String.format("%s/users/me", MercadoPagoConfig.BASE_URL)))),
            any(HttpContext.class));
    doReturn(oauthHttpResponse)
        .when(HTTP_CLIENT)
        .execute(
            argThat(
                new HttpRequestMatcher(new HttpGet("https://auth.mercadopago.com/oauth/token"))),
            any(HttpContext.class));

    String authorizationURL =
        new OauthClient().getAuthorizationURL(appId, redirectUri, buildRequestOptions());

    assertAuthorizationUrlComponents(authorizationURL);
  }

  @Test
  public void createCredentialSuccess() throws IOException, MPException, MPApiException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromFile("/oauth/oauth_credential.json", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(oauthHttpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CreateOauthCredential credential =
        new OauthClient().createCredential(authorizationCode, redirectUri);

    assertCreateOauthCredentialFields(credential);
  }

  @Test
  public void createCredentialWithRequestOptionsSuccess()
      throws MPException, MPApiException, IOException {
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
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CreateOauthCredential credential =
        new OauthClient().createCredential(authorizationCode, redirectUri, requestOptions);

    assertCreateOauthCredentialFields(credential);
  }

  @Test
  public void refreshCredentialSuccess() throws IOException, MPException, MPApiException {
    oauthHttpResponse =
        MockHelper.generateHttpResponseFromFile("/oauth/oauth_refresh_token.json", HttpStatus.OK);
    oauthHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(oauthHttpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    RefreshOauthCredential credential = new OauthClient().refreshCredential(refreshToken);
    assertRefreshOauthCredentialFields(credential);
  }

  @Test
  public void refreshCredentialWithRequestOptionsSuccess()
      throws MPException, MPApiException, IOException {
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
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    RefreshOauthCredential credential =
        new OauthClient().refreshCredential(refreshToken, requestOptions);
    assertRefreshOauthCredentialFields(credential);
  }

  private void assertAuthorizationUrlComponents(String authorizationURL) throws URISyntaxException {
    URI responseURL = new URI(authorizationURL);
    assertEquals("https", responseURL.getScheme());
    assertEquals("auth.mercadopago.com.br", responseURL.getHost());
    assertEquals("/authorization", responseURL.getPath());
    assertTrue(responseURL.getQuery().contains("client_id=123"));
    assertTrue(responseURL.getQuery().contains("response_type=code"));
    assertTrue(responseURL.getQuery().contains("platform_id=mp"));
    assertTrue(responseURL.getQuery().contains(String.format("redirect_uri=%s", redirectUri)));
  }

  private void assertCreateOauthCredentialFields(CreateOauthCredential credential) {
    assertEquals("APP_USR-4934588586838432-XXXXXXXX-241983636", credential.getAccessToken());
    assertEquals("bearer", credential.getTokenType());
    assertEquals("offline_access read write", credential.getScope());
    assertEquals(15552000, credential.getExpiresIn());
    assertEquals("TG-XXXXXXXX-241983636", credential.getRefreshToken());
    assertEquals("APP_USR-d0a26210-XXXXXXXX-479f0400869e", credential.getPublicKey());
    assertTrue(credential.isLiveMode());
  }

  private void assertRefreshOauthCredentialFields(RefreshOauthCredential credential) {
    assertEquals("APP_USR-4934588586838432-XXXXXXXX-241983636", credential.getAccessToken());
    assertEquals("bearer", credential.getTokenType());
    assertEquals("offline_access read write", credential.getScope());
    assertEquals(15552000, credential.getExpiresIn());
    assertEquals("TG-XXXXXXXXXXXX-241983636", credential.getRefreshToken());
  }
}
