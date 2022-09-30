package com.mercadopago.client.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** PaymentTicketInfoRequest class. */
@Getter
@Builder
public class PaymentTicketInfoRequest {

  /** Discounts. */
  private List<PaymentDiscountRequest> discounts;

  /** Fine. */
  private PaymentFeeRequest fine;

  /** Interest. */
  private PaymentFeeRequest interest;
}
