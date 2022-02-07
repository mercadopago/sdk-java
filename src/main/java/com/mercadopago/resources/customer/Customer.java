package com.mercadopago.resources.customer;

import com.mercadopago.net.MPResource;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;
import java.util.Date;
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

  private String id;

  private String email;

  private String firstName;

  private String lastName;

  private Phone phone;

  private Identification identification;

  private String defaultAddress;

  private CustomerDefaultAddress address;

  private Date dateRegistered;

  private String description;

  private Date dateCreated;

  private Date dateLastUpdated;

  private Map<String, Object> metadata;

  private String defaultCard;

  private List<CustomerCard> cards;

  private List<CustomerAddress> addresses;

  private Boolean liveMode;
}
