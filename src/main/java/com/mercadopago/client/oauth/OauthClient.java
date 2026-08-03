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

/**
 * Client for the MercadoPago OAuth 2.0 API.
 *
 * <p>Provides operations for the OAuth authorization flow used in marketplace and platform
 * integrations: generating authorization URLs, creating credentials from authorization codes,
 * and refreshing expired tokens.
 *
 * <p>Usage example:
 * <pre>{@code
 * OauthClient client = new OauthClient();
 * String authUrl = client.getAuthorizationURL(appId, redirectUri);
 * // After user authorizes, exchange the code for credentials:
 * CreateOauthCredential credential = client.createCredential(authorizationCode, redirectUri);
 * }</pre>
 *
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post">
 *     OAuth API reference</a>
 */
public class OauthClient extends MercadoPagoClient {

  /** Class-level logger for OAuth operations. */
  private static final Logger LOGGER = Logger.getLogger(OauthClient.class.getName());

  /** Internal client used to retrieve user information for building authorization URLs. */
  private final UserClient userClient;

  /** Base host for MercadoPago OAuth authorization endpoints. */
  private final String authHost = "https://auth.mercadopago.com";

  /** Relative path for the OAuth token endpoint. */
  private final String path = "/oauth/token";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public OauthClient() {
    this(MercadoPagoConfig.getHttpClient());
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Constructs an {@code OauthClient} with a custom HTTP client.
   *
   * <p>Also initialises the internal {@link UserClient} with the same HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public OauthClient(MPHttpClient httpClient) {
    super(httpClient);
    userClient = new UserClient(httpClient);
  }

  /**
   * Builds the OAuth authorization URL that the seller must visit to grant access.
   *
   * <p>Internally fetches the authenticated user's country to construct the correct
   * country-specific authorization host.
   *
   * @param appId the application ID (client_id) registered in MercadoPago
   * @param redirectUri the URL to which the user is redirected after authorizing
   * @return the full authorization URL, or {@code null} if the user's country cannot be determined
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public String getAuthorizationURL(String appId, String redirectUri)
      throws MPException, MPApiException {
    return this.getAuthorizationURL(appId, redirectUri, null);
  }

  /**
   * Builds the OAuth authorization URL with custom request options.
   *
   * <p>Internally fetches the authenticated user's country to construct the correct
   * country-specific authorization host.
   *
   * @param appId the application ID (client_id) registered in MercadoPago
   * @param redirectUri the URL to which the user is redirected after authorizing
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the full authorization URL, or {@code null} if the user's country cannot be determined
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
   * Creates OAuth credentials by exchanging an authorization code for access and refresh tokens.
   *
   * <p>Used in the marketplace authorization flow to operate on behalf of a seller. See the
   * <a href="https://www.mercadopago.com.br/developers/en/guides/security/oauth">OAuth guide</a>
   * for more details.
   *
   * @param authorizationCode the authorization code received after the seller grants access via the
   *     authorization URL
   * @param redirectUri the same redirect URI used when generating the authorization URL
   * @return the {@link CreateOauthCredential} containing access token, refresh token, and
   *     expiration
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post">api
   *     docs</a>
   */
  public CreateOauthCredential createCredential(String authorizationCode, String redirectUri)
      throws MPException, MPApiException {
    return this.createCredential(authorizationCode, redirectUri, null);
  }

  /**
   * Creates OAuth credentials with custom request options by exchanging an authorization code.
   *
   * <p>Used in the marketplace authorization flow to operate on behalf of a seller. See the
   * <a href="https://www.mercadopago.com.br/developers/en/guides/security/oauth">OAuth guide</a>
   * for more details.
   *
   * @param authorizationCode the authorization code received after the seller grants access via the
   *     authorization URL
   * @param redirectUri the same redirect URI used when generating the authorization URL
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link CreateOauthCredential} containing access token, refresh token, and
   *     expiration
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
   * Refreshes OAuth credentials using a previously obtained refresh token.
   *
   * @param refreshToken the refresh token received during credential creation
   * @return a new {@link RefreshOauthCredential} containing a fresh access token and refresh token
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post">api
   *     docs</a>
   */
  public RefreshOauthCredential refreshCredential(String refreshToken)
      throws MPException, MPApiException {
    return this.refreshCredential(refreshToken, null);
  }

  /**
   * Refreshes OAuth credentials with custom request options.
   *
   * @param refreshToken the refresh token received during credential creation
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return a new {@link RefreshOauthCredential} containing a fresh access token and refresh token
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
