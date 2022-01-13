package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Data;

/** PaymentPassengerRequest class. */
@Data
@Builder
public class PaymentPassengerRequest {
  public String firstName;

  public String lastName;

  public IdentificationRequest identification;
}
