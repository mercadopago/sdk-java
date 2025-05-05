package com.mercadopago.resources.preference;

import com.mercadopago.resources.common.Address;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;

import java.time.OffsetDateTime;
import java.util.Date;

import lombok.Getter;

/** Payer information from preference. */
@Getter
public class PreferencePayer {
  /** Payer's name. */
  private String name;

  /** Payer's surname. */
  private String surname;

  /** Payer's email. */
  private String email;

  /** Payer's phone. */
  private Phone phone;

  /** Payer's identification. */
  private Identification identification;

  /** Payer's address. */
  private Address address;

  /** Date of creation of the payer user. */
  private OffsetDateTime dateCreated;

  /** Date of the last purchase. */
  private OffsetDateTime lastPurchase;

  /** Type of authentication used by the payer. */
  private String authenticationType;

  /** Indicates if the payer is a prime user. */
  private Boolean isPrimeUser;

  /** Indicates if this is the payer's first purchase online. */
  private Boolean isFirstPurchaseOnline;

  /** Date of registration of the payer. */
  private OffsetDateTime registrationDate;
}
