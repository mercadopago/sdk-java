package com.mercadopago.resources.customer;

import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;
import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * Represents a customer resource in the MercadoPago API.
 *
 * <p>Stores customer data such as email, name, phone, identification, addresses, and saved cards
 * to improve the shopping experience. When used with the Cards resource, customers can complete
 * purchases faster without re-entering payment information.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/customers/_customers/post">
 *     Customer API Reference</a>
 */
@Getter
public class Customer extends MPResource {

  /** Unique identifier of the customer. */
  private String id;

  /** Email address of the customer. */
  private String email;

  /** First name of the customer. */
  private String firstName;

  /** Last name of the customer. */
  private String lastName;

  /** Phone information of the customer. */
  private Phone phone;

  /** Identification document information of the customer. */
  private Identification identification;

  /** Identifier of the customer's default address. */
  private String defaultAddress;

  /** Default address details of the customer. */
  private CustomerDefaultAddress address;

  /** Date when the customer registered on the merchant's site. */
  private OffsetDateTime dateRegistered;

  /** Free-text description or notes about the customer. */
  private String description;

  /** Date and time when the customer record was created in MercadoPago. */
  private OffsetDateTime dateCreated;

  /** Date and time when the customer record was last updated. */
  private OffsetDateTime dateLastUpdated;

  /** Custom metadata as key-value pairs associated with the customer. */
  private Map<String, Object> metadata;

  /** Identifier of the customer's default card. */
  private String defaultCard;

  /** List of saved cards associated with the customer. */
  private List<CustomerCard> cards;

  /** List of addresses registered for the customer. */
  private List<CustomerAddress> addresses;

  /** Whether the customer was created in production (live) mode. */
  private Boolean liveMode;
}
