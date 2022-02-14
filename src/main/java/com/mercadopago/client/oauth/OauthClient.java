package com.mercadopago.client.oauth;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.user.UserClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.UrlFormatter;
import com.mercadopago.resources.oauth.CreateOauthCredential;
import com.mercadopago.resources.oauth.RefreshOauthCredential;
import com.mercadopago.resources.user.User;
import com.mercadopago.serialization.Serializer;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/** Client responsible for performing oauth authorization. */
public class OauthClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(OauthClient.class.getName());

  private final UserClient userClient;

  private final String authHost = "https://auth.mercadopago.com";

  private final String path = "/oauth/token";

  /** Default constructor. Uses the default http client used by the SDK. */
  public OauthClient() {
    this(MercadoPagoConfig.getHttpClient());
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Constructor used for providing a custom http client.
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
   * @throws MPException an error if the request fails
   */
  public String getAuthorizationURL(String appId, String redirectUri)
      throws MPException, MPApiException {
    return this.getAuthorizationURL(appId, redirectUri, null);
  }

  /**
   * Get URL for Oauth authorization.
   *
   * @param appId Id of the app
   * @param redirectUri URL for redirection after authorization
   * @param requestOptions metadata to customize the request
   * @return URL to perform authorization
   * @throws MPException an error if the request fails
   */
  public String getAuthorizationURL(
      String appId, String redirectUri, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get oauth authorization url request");

    User user = userClient.get(requestOptions);

    if (Objects.isNull(user) || user.getCountryId().isEmpty()) {
      return null;
    }

    HashMap<String, Object> queryParams = new HashMap<>();
    queryParams.put("client_id", appId);
    queryParams.put("response_type", "code");
    queryParams.put("platform_id", "mp");
    queryParams.put("redirect_uri", redirectUri);

    return UrlFormatter.format(
        String.format("%s.%s/authorization", authHost, user.getCountryId().toLowerCase()),
        queryParams);
  }

  /**
   * Create Oauth credentials to operate on behalf of a seller. Go <a
   * href=https://www.mercadopago.com.br/developers/en/guides/security/oauth>here</a> to learn more.
   *
   * @param authorizationCode authorization code received from calling getAuthorizationURL
   * @param redirectUri the redirectUri received from calling getAuthorizationURL
   * @return the Oauth credentials
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post">api
   *     docs</a>
   */
  public CreateOauthCredential createCredential(String authorizationCode, String redirectUri)
      throws MPException, MPApiException {
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
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post">api
   *     docs</a>
   */
  public CreateOauthCredential createCredential(
      String authorizationCode, String redirectUri, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create oauth credential request");
    CreateOauthCredentialRequest request =
        CreateOauthCredentialRequest.builder()
            .clientSecret(getAccessToken(requestOptions))
            .code(authorizationCode)
            .redirectUri(redirectUri)
            .build();
    MPRequest mpRequest =
        MPRequest.buildRequest(
            path, HttpMethod.POST, Serializer.serializeToJson(request), null, requestOptions);
    MPResponse response = send(mpRequest);

    CreateOauthCredential credential =
        Serializer.deserializeFromJson(CreateOauthCredential.class, response.getContent());
    credential.setResponse(response);

    return credential;
  }

  /**
   * Refresh Oauth credentials.
   *
   * @param refreshToken refresh token received when you create credentials
   * @return new Oauth credentials
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post">api
   *     docs</a>
   */
  public RefreshOauthCredential refreshCredential(String refreshToken)
      throws MPException, MPApiException {
    return this.refreshCredential(refreshToken, null);
  }

  /**
   * Refresh Oauth credentials.
   *
   * @param refreshToken refresh token received when you create credentials
   * @param requestOptions metadata to customize the request
   * @return new Oauth credentials
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post">api
   *     docs</a>
   */
  public RefreshOauthCredential refreshCredential(
      String refreshToken, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending refresh oauth credential request");
    RefreshOauthCredentialRequest request =
        RefreshOauthCredentialRequest.builder()
            .clientSecret(getAccessToken(requestOptions))
            .refreshToken(refreshToken)
            .build();

    MPRequest mpRequest =
        MPRequest.buildRequest(
            path, HttpMethod.POST, Serializer.serializeToJson(request), null, requestOptions);
    MPResponse response = send(mpRequest);
    RefreshOauthCredential credential =
        Serializer.deserializeFromJson(RefreshOauthCredential.class, response.getContent());
    credential.setResponse(response);

    return credential;
  }

  private String getAccessToken(MPRequestOptions requestOptions) {
    return Objects.isNull(requestOptions)
        ? MercadoPagoConfig.getAccessToken()
        : requestOptions.getAccessToken();
  }
}
