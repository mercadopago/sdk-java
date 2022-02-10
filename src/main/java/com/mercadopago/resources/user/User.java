package com.mercadopago.resources.user;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/** User information.. */
@Getter
public class User extends MPResource {

  /** Id of the user. */
  private Long id;

  /** Nickname of user. */
  private String nickname;

  /** First name of user. */
  private String firstName;

  /** Last name of user. */
  private String lastName;

  /** Email of the user. */
  private String email;

  /** Id of the user's site. */
  private String siteId;

  /** Id of the user's country. */
  private String countryId;
}
