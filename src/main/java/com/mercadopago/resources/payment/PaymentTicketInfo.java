package com.mercadopago.resources.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** PaymentTicketInfo class. */
@Getter
@Builder
public class PaymentTicketInfo {

  /** Discounts. */
  private List<PaymentDiscount> discounts;

  /** Fine. */
  private PaymentFee fine;

  /** Interest. */
  private PaymentFee interest;
}
