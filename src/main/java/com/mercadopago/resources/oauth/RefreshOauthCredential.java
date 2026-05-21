package com.mercadopago.resources.oauth;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Resource representing the credential returned when refreshing an existing OAuth token.
 * Inherits all fields from {@link OauthCredential} including the new access token,
 * its expiration, scope, and a potentially rotated refresh token.
 *
 * @see OauthCredential
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class RefreshOauthCredential extends OauthCredential {}
