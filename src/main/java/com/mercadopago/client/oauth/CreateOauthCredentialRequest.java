package com.mercadopago.client.oauth;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to exchange an authorization code for OAuth access and refresh tokens. This
 * is part of the OAuth 2.0 authorization code flow, which allows your application to act on
 * behalf of a seller after they grant access through the Mercado Pago authorization URL.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/guides/security/oauth">OAuth Guide</a>
 */
@Getter
@Builder
public class CreateOauthCredentialRequest {
  /**
   * Grant type for the OAuth token request. Fixed as {@code "authorization_code"} for the
   * authorization code exchange flow.
   */
  private final String grantType = "authorization_code";
  /**
   * Application secret key (client_secret) used to authenticate the token request. Obtained from
   * the Mercado Pago developer credentials panel.
   */
  private final String clientSecret;
  /** Application ID (client_id) that uniquely identifies your integration. */
  private final String clientId;
  /** Authorization code received via the redirect URL after the seller grants permission. */
  private final String code;

  /** Redirect URI configured in the application settings, used to validate the token exchange. */
  private final String redirectUri;
}
