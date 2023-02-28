package com.mercadopago.resources.payment;

import java.util.List;
import lombok.Getter;

/** PaymentRules class. */
@Getter
public class PaymentRules {

  /** Discounts. */
  private List<PaymentDiscount> discounts;

  /** Fine. */
  private PaymentFee fine;

  /** Interest. */
  private PaymentFee interest;
}
