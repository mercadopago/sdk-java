package com.mercadopago.resources.common;

import lombok.Getter;

/**
 * Represents an identification document used across the MercadoPago API.
 *
 * <p>This base type holds document data such as type (e.g., CPF, DNI, CNPJ) and document number.
 * It is commonly used in payer, customer, and cardholder contexts to identify individuals.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">MercadoPago API Reference</a>
 */
@Getter
public class Identification {
  /** Type of identification document (e.g., CPF, DNI, CNPJ). */
  private String type;

  /** Unique number of the identification document. */
  private String number;
}
