package com.mercadopago.client.advancedpayment;

import lombok.Builder;
import lombok.Getter;

/** Request object for updating an advanced payment. */
@Getter
@Builder
public class AdvancedPaymentUpdateRequest {

  private Boolean capture;

  private String status;
}
