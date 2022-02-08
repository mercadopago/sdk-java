package com.mercadopago.resources.customer;

import com.mercadopago.net.MPResource;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * This class allows you to store customers data safely to improve the shopping experience. This
 * will allow your customer to complete their purchases much faster and easily when used in
 * conjunction with the Cards class.
 */
@Getter
public class Customer extends MPResource {

  /** Customer ID */
  private String id;

  /** Customer's email */
  private String email;

  /** Customer's first name */
  private String firstName;

  /** Customer's last name */
  private String lastName;

  /** Customer's phone information */
  private Phone phone;

  /** Customer's identification information */
  private Identification identification;

  /** Customer's default address */
  private String defaultAddress;

  /** */
  private CustomerDefaultAddress address;

  /** */
  private OffsetDateTime dateRegistered;

  /** */
  private String description;

  /** */
  private OffsetDateTime dateCreated;

  /** */
  private OffsetDateTime dateLastUpdated;

  /** */
  private Map<String, Object> metadata;

  /** */
  private String defaultCard;

  /** */
  private List<CustomerCard> cards;

  /** */
  private List<CustomerAddress> addresses;

  /** */
  private Boolean liveMode;
}
