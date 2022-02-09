package com.mercadopago.resources.oauth;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/** CreateOauthCredential class. */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CreateOauthCredential extends OauthCredential {
  /** Public Key. */
  private String publicKey;

  /** Live Mode. */
  private boolean liveMode;
}
