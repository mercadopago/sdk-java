package com.mercadopago.resources.user;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing the authenticated MercadoPago user account.
 *
 * <p>Contains profile information of the user associated with the current access token, including
 * personal details, site affiliation, and country. Typically retrieved to verify credentials or
 * obtain account metadata.
 *
 * @see com.mercadopago.client.user.UserClient
 */
@Getter
public class User extends MPResource {

  /** Unique numeric identifier of the user account. */
  private Long id;

  /** Public nickname or display name of the user. */
  private String nickname;

  /** First name of the user. */
  private String firstName;

  /** Last name of the user. */
  private String lastName;

  /** Email address associated with the user account. */
  private String email;

  /** Identifier of the MercadoPago site the user belongs to (e.g., MLA, MLB, MLM). */
  private String siteId;

  /** ISO 3166-1 country code of the user's registered country. */
  private String countryId;
}
