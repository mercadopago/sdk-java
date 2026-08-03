package com.mercadopago.resources.oauth;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Resource representing the credential returned when creating a new OAuth authorization.
 * Extends {@link OauthCredential} with additional fields that indicate the public key
 * associated with the application and whether the credential operates in live (production) mode.
 *
 * @see OauthCredential
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CreateOauthCredential extends OauthCredential {
  /** Public key associated with the MercadoPago application for this credential. */
  private String publicKey;

  /** Whether this credential is configured for live (production) mode rather than sandbox. */
  private boolean liveMode;
}
