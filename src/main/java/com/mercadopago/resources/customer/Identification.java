package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents a customer's identification document in the MercadoPago API.
 *
 * <p>Holds the document type (e.g., CPF, DNI, CNPJ) and the document number, used for identity
 * verification in customer-related operations.
 *
 * @see Customer
 */
@Getter
public class Identification {

  /** Type of identification document (e.g., CPF, DNI, CNPJ). */
  private String type;

  /** Number of the identification document. */
  private String number;
}
