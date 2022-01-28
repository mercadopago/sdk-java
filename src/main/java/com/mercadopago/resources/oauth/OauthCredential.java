package com.mercadopago.resources.oauth;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/** */
@Getter
public class OauthCredential extends MPResource {
  /** */
  private String accessToken;
  /** */
  private String tokenType;
  /** */
  private Long expiresIn;
  /** */
  private String scope;
  /** */
  private String refreshToken;
}
