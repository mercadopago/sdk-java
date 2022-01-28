package com.mercadopago.client.oauth;

import lombok.Builder;
import lombok.Getter;

/** */
@Getter
@Builder
public class CreateOauthCredentialRequest {
  /** */
  private String clientSecret;

  /** */
  private final String grantType = "authorization_code";

  /** */
  private String code;

  /** */
  private String redirectUri;
}
