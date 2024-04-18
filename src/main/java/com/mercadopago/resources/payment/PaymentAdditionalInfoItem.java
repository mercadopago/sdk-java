package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/** PaymentAdditionalInfoIten class. */
@Getter
@Builder
public class PaymentAdditionalInfoItem extends PaymentItem {


  /** Date of event */
  private OffsetDateTime eventDate;
}
