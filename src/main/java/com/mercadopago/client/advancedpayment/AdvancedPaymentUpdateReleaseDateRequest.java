package com.mercadopago.client.advancedpayment;

import lombok.Builder;
import lombok.Getter;

/** Request to change the money release date for all disbursements. */
@Getter
@Builder
public class AdvancedPaymentUpdateReleaseDateRequest {

  /** New release date in ISO 8601 format. */
  private final String moneyReleaseDate;
}
