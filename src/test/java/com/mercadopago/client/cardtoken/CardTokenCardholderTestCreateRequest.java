package com.mercadopago.client.cardtoken;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;

/** CardTokenCardholderTestCreateRequest class. */
@Builder
public class CardTokenCardholderTestCreateRequest {

  /** Cardholder name. */
  public String name;

  /** Cardholder identification. */
  public IdentificationRequest identification;
}
