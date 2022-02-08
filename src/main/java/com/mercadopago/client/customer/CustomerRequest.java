package com.mercadopago.client.customer;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** Information used to create/update a customer. */
@Getter
@Builder
public class CustomerRequest {
  /** Customer's email */
  private String email;

  /** Customer's first name */
  private String firstName;

  /** Customer's last name */
  private String lastName;

  /** Customer's phone */
  private PhoneRequest phone;

  /** Customer's identification */
  private IdentificationRequest identification;

  /** Customer's default address */
  private String defaultAddress;

  /** Default address information */
  private CustomerAddressRequest address;

  /** Customer's default card */
  private String defaultCard;

  /** Customer's registration date */
  private Date dateRegistred;

  /** Customer's description */
  private String description;

  /** Metadata */
  private Map<String, Object> metadata;
}
