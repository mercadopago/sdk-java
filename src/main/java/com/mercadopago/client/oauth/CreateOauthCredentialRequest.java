package com.mercadopago.client.oauth;

import lombok.Builder;
import lombok.Getter;

/**
 * Credential information to perform a create credential request.
 * Go to <a href="https://www.mercadopago.com.br/developers/en/guides/security/oauth">this page</a> to learn more. */
@Getter
@Builder
public class CreateOauthCredentialRequest {
  /** Private key to be used in some plugins to generate payments. You can get it in Your credentials. */
  private String clientSecret;

  /** Unique ID that identifies your integration. You can get it in Your credentials. */
  private String clientId;

  /** Specify type of operation to perform to get your credentials. This is a fixed parameter with an authorization_code value */
  private final String grantType = "authorization_code";

  /** The authorization code you get in the authorization url for linking */
  private String code;

  /** This is the URL you set up in the Redirect URL field in your application */
  private String redirectUri;
}
