package com.mercadopago.client.oauth;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.IdempotentRequest;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.user.UserClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.UrlFormatter;
import com.mercadopago.resources.oauth.OauthCredential;
import com.mercadopago.resources.user.User;
import com.mercadopago.serialization.Serializer;
import java.util.Objects;
import java.util.Optional;

/** Client responsible for performing oauth authorization. */
public class OauthClient extends MercadoPagoClient {
  private final UserClient userClient;

  private final String authHost = "https://auth.mercadopago.com";

  private final String path = "/oauth/token";

  /** Default constructor. Uses the default http client used by the SDK. */
  public OauthClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * MercadoPagoClient constructor.
   *
   * @param httpClient http client
   */
  public OauthClient(MPHttpClient httpClient) {
    super(httpClient);
    userClient = new UserClient(httpClient);
  }

  /**
   * Get URL for Oauth authorization.
   *
   * @param appId Id of the app
   * @param redirectUri URL for redirection after authorization
   * @return URL to perform authorization
   */
  public String getAuthorizationURL(String appId, String redirectUri) throws MPException {
    return this.getAuthorizationURL(appId, redirectUri, null);
  }

  /**
   * Get URL for Oauth authorization.
   *
   * @param appId Id of the app
   * @param redirectUri URL for redirection after authorization
   * @param requestOptions
   * @return URL to perform authorization
   */
  public String getAuthorizationURL(
      String appId, String redirectUri, MPRequestOptions requestOptions) throws MPException {
    User user = userClient.get(requestOptions);

    if (Objects.isNull(user) || user.getCountryId().isEmpty()) {
      return null;
    }

    return UrlFormatter.format(
        String.format(
            "%s.%s/authorization?client_id=%s&response_type=code&platform_id=mp&redirect_uri=%s",
            authHost, user.getCountryId().toLowerCase(), appId, redirectUri));
  }

  /**
   * Create Oauth credentials to operate on behalf of a seller. Go <a
   * href=https://www.mercadopago.com.br/developers/en/guides/security/oauth>here</a> to learn more.
   *
   * @param authorizationCode authorization code received from calling getAuthorizationURL
   * @param redirectUri the redirectUri received from calling getAuthorizationURL
   * @return the Oauth credentials
   */
  public OauthCredential createCredential(String authorizationCode, String redirectUri)
      throws MPException {
    return this.createCredential(authorizationCode, redirectUri, null);
  }

  /**
   * Create Oauth credentials to operate on behalf of a seller. Go <a
   * href=https://www.mercadopago.com.br/developers/en/guides/security/oauth>here</a> to learn more.
   *
   * @param authorizationCode authorization code received from calling getAuthorizationURL
   * @param redirectUri the redirectUri received from calling getAuthorizationURL
   * @param requestOptions metadata to customize the request
   * @return the Oauth credentials
   */
  public OauthCredential createCredential(
      String authorizationCode, String redirectUri, MPRequestOptions requestOptions)
      throws MPException {
    CreateOauthCredentialRequest request =
        CreateOauthCredentialRequest.builder()
            .clientSecret(getAccessToken(requestOptions))
            .code(authorizationCode)
            .redirectUri(redirectUri)
            .build();
    IdempotentRequest idempotentRequest =
        IdempotentRequest.buildRequest(
            path, HttpMethod.POST, Serializer.serializeToJson(request), null, requestOptions);
    MPResponse response = send(idempotentRequest);

    OauthCredential credential =
        Serializer.deserializeFromJson(OauthCredential.class, response.getContent());
    credential.setResponse(response);

    return credential;
  }

  /**
   * Refresh Oauth credentials.
   *
   * @param refreshToken refresh token received when you create credentials
   * @return new Oauth credentials
   * @throws MPException any error retrieving Oauth credentials
   */
  public OauthCredential refreshCredential(String refreshToken) throws MPException {
    return this.refreshCredential(refreshToken, null);
  }

  /**
   * Refresh Oauth credentials.
   *
   * @param refreshToken refresh token received when you create credentials
   * @param requestOptions metadata to customize the request
   * @return new Oauth credentials
   * @throws MPException any error retrieving Oauth credentials
   */
  public OauthCredential refreshCredential(String refreshToken, MPRequestOptions requestOptions)
      throws MPException {
    RefreshOauthCredentialRequest request =
        RefreshOauthCredentialRequest.builder()
            .clientSecret(getAccessToken(requestOptions))
            .refreshToken(refreshToken)
            .build();

    IdempotentRequest idempotentRequest =
        IdempotentRequest.buildRequest(
            path, HttpMethod.POST, Serializer.serializeToJson(request), null, requestOptions);
    MPResponse response = send(idempotentRequest);
    OauthCredential credential =
        Serializer.deserializeFromJson(OauthCredential.class, response.getContent());
    credential.setResponse(response);

    return credential;
  }

  private String getAccessToken(MPRequestOptions requestOptions) {
    return Objects.isNull(requestOptions)
        ? MercadoPagoConfig.getAccessToken()
        : requestOptions.getAccessToken();
  }
}
