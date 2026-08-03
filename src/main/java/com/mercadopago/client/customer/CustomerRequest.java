package com.mercadopago.client.customer;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO used to create or update a customer in Mercado Pago. Customers can be associated
 * with saved cards, addresses, and identification documents to streamline recurring payments
 * and checkout experiences.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/customers">Customers API Reference</a>
 */
@Getter
@Builder
public class CustomerRequest {
  /** Customer's email address, used as a unique identifier for the customer. */
  private final String email;

  /** Customer's first name. */
  private final String firstName;

  /** Customer's last name. */
  private final String lastName;

  /** Customer's phone information including area code and number. */
  private final PhoneRequest phone;

  /** Customer's identification document (e.g., CPF, DNI). */
  private final IdentificationRequest identification;

  /** ID of the customer's default address from their address list. */
  private final String defaultAddress;

  /** Customer's address details for registration purposes. */
  private final CustomerAddressRequest address;

  /** ID of the customer's default saved card. */
  private final String defaultCard;

  /** Date when the customer was registered in the merchant's system. */
  private final OffsetDateTime dateRegistred;

  /** Free-text description or notes about the customer. */
  private final String description;

  /** Custom key-value metadata associated with the customer. */
  private final Map<String, Object> metadata;
}
