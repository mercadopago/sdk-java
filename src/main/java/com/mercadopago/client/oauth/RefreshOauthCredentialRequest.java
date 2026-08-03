package com.mercadopago.client.oauth;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to refresh an expired OAuth access token using a previously obtained refresh
 * token. This avoids requiring the seller to re-authorize the application and ensures
 * uninterrupted API access.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/guides/security/oauth">OAuth Guide</a>
 */
@Getter
@Builder
public class RefreshOauthCredentialRequest {
  /**
   * Grant type for the OAuth token request. Fixed as {@code "refresh_token"} for the token
   * refresh flow.
   */
  private final String grantType = "refresh_token";
  /**
   * Application secret key (client_secret) used to authenticate the refresh request. Obtained
   * from the Mercado Pago developer credentials panel.
   */
  private final String clientSecret;
  /**
   * Application ID (client_id) that uniquely identifies your integration in Mercado Pago.
   */
  private final String clientId;
  /** Refresh token obtained from the initial OAuth authorization code exchange. */
  private final String refreshToken;
}
