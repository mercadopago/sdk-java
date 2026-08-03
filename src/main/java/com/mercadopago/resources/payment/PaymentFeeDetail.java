package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource that represents a single fee charged on a MercadoPago payment.
 *
 * <p>Each payment may have multiple fee details, such as the MercadoPago marketplace fee,
 * financing costs, or shipping fees. This resource identifies the type, who absorbs the
 * cost, and the monetary amount of each fee.
 *
 * @see Payment#getFeeDetails()
 */
@Getter
public class PaymentFeeDetail {
  /** Type of fee (e.g. mercadopago_fee, coupon_fee, financing_fee). */
  private String type;

  /** Party that absorbs this fee cost (e.g. collector, payer). */
  private String feePayer;

  /** Monetary amount of the fee. */
  private BigDecimal amount;
}
