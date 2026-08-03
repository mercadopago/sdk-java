package com.mercadopago.resources.oauth;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Base resource representing credential information obtained from an OAuth authorization flow.
 * Contains the access token, its type and expiration, the granted scope, and a refresh token
 * that can be used to renew the credential without repeating the authorization process.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/oauth/_oauth_token/post">OAuth API reference</a>
 */
@Getter
public class OauthCredential extends MPResource {
  /** OAuth access token used to authenticate API requests on behalf of the user. */
  private String accessToken;

  /** Token type indicating the authentication scheme, typically "Bearer". */
  private String tokenType;

  /** Number of seconds until the access token expires. */
  private Long expiresIn;

  /** OAuth scope that defines the permissions granted by this credential. */
  private String scope;

  /** Refresh token used to obtain a new access token when the current one expires. */
  private String refreshToken;
}
