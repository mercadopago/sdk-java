package com.mercadopago.client.payment;


import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentAdditionalInfoItemRequest extends PaymentItemRequest {

  /**
   * Date of event
   */
  private OffsetDateTime eventDate;
}


