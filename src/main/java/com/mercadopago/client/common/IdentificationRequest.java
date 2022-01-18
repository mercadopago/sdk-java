package com.mercadopago.client.common;

import lombok.Data;

/** Customer identification data used in requests. */
@Data
public class IdentificationRequest {
  /** Identification type */
  private String type;

  /** Identification number */
  private String number;
}
