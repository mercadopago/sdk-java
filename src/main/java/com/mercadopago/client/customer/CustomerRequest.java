package com.mercadopago.client.customer;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** Information used to create/update a customer. */
@Getter
@Builder
public class CustomerRequest {
  /** Customer's email. */
  private final String email;

  /** Customer's first name. */
  private final String firstName;

  /** Customer's last name. */
  private final String lastName;

  /** Customer's phone. */
  private final PhoneRequest phone;

  /** Customer's identification. */
  private final IdentificationRequest identification;

  /** Customer's default address. */
  private final String defaultAddress;

  /** Default address information. */
  private final CustomerAddressRequest address;

  /** Customer's default card. */
  private final String defaultCard;

  /** Customer's registration date. */
  private final OffsetDateTime dateRegistred;

  /** Customer's description. */
  private final String description;

  /** Metadata. */
  private final Map<String, Object> metadata;
}
