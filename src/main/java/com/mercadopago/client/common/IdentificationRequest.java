package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing a personal identification document used to identify payers,
 * customers, and cardholders. The type and number vary by country (e.g., CPF in Brazil,
 * DNI in Argentina, CC in Colombia).
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">Mercado Pago API Reference</a>
 */
@Getter
@Builder
public class IdentificationRequest {
  /** Type of identification document (e.g., "CPF", "DNI", "CNPJ", "CC"). */
  private final String type;

  /** Identification document number. */
  private final String number;
}
