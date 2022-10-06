package com.mercadopago.resources.payment;

import java.util.List;
import lombok.Getter;

/** PaymentTicketInfo class. */
@Getter
public class PaymentTicketInfo {

  /** Discounts. */
  private List<PaymentDiscount> discounts;

  /** Fine. */
  private PaymentFee fine;

  /** Interest. */
  private PaymentFee interest;
}
