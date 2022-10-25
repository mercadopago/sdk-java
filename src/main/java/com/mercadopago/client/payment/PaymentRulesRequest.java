package com.mercadopago.client.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** PaymentRulesRequest class. */
@Getter
@Builder
public class PaymentRulesRequest {

  /** Discounts. */
  private List<PaymentDiscountRequest> discounts;

  /** Fine. */
  private PaymentFeeRequest fine;

  /** Interest. */
  private PaymentFeeRequest interest;
}
