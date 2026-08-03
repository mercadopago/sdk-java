package com.mercadopago.resources.identificationtype;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Represents a type of identification document available in the MercadoPago API.
 *
 * <p>Describes the accepted identification document types for a given country (e.g., CPF, DNI,
 * CNPJ), including validation constraints such as minimum and maximum character lengths.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/identification_types/_identification_types/get">
 *     Identification Types API Reference</a>
 */
@Getter
public class IdentificationType extends MPResource {
  /** Unique identifier of the identification type. */
  private String id;

  /** Display name of the identification type (e.g., CPF, DNI). */
  private String name;

  /** Data type of the identification number (e.g., "number", "string"). */
  private String type;

  /** Minimum allowed length for the identification number. */
  private Integer minLength;

  /** Maximum allowed length for the identification number. */
  private Integer maxLength;
}
