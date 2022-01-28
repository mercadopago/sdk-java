package com.mercadopago.client.oauth;

import lombok.Builder;
import lombok.Getter;

/** */
@Getter
@Builder
public class RefreshOauthCredentialRequest {
  /** */
  private String clientSecret;

  /** */
  private final String grantType = "refresh_token";

  /** */
  private String refreshToken;
}
