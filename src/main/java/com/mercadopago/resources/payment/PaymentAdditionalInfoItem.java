package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import java.time.OffsetDateTime;
import lombok.Getter;

/** PaymentAdditionalInfoIten class. */
@Getter
public class PaymentAdditionalInfoItem extends PaymentItem {


  /** Date of event */
  private OffsetDateTime eventDate;
}
