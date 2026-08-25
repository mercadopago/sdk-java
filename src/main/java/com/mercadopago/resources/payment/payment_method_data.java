package com.mercadopago.resources.payment;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.resources.MPBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Additional data provided by the payment method.
 *
 * <p>Contains metadata and rules specific to the payment method used in the transaction.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">Payment Methods API reference</a>
 */
@Getter
@Setter
public class PaymentMethodData extends MPBase {

  /** Rules or constraints specific to the payment method. */
  @SerializedName("rules")
  private Object rules;
}