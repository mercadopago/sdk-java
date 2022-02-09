package com.mercadopago.resources.oauth;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/** Credential information for an Oauth authorization. */
@Getter
public class OauthCredential extends MPResource {
  /** Access token of the user. */
  private String accessToken;

  /** Type of token. */
  private String tokenType;

  /** Expiration date of the token. */
  private Long expiresIn;

  /** The Oauth scope associated to the token. */
  private String scope;

  /** Token used to renew Oauth credential. */
  private String refreshToken;
}
