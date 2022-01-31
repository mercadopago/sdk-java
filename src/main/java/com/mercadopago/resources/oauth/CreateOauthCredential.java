package com.mercadopago.resources.oauth;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/** */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CreateOauthCredential extends OauthCredential {
  /** */
  private String publicKey;

  /** */
  private boolean liveMode;
}
