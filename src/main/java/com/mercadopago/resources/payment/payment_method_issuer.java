package com.mercadopago.resources.payment;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.resources.MPBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Issuer information for card-based payment methods.
 *
 * <p>Identifies the financial institution that issued the card used in the transaction.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">Payment Methods API reference</a>
 */
@Getter
@Setter
public class PaymentMethodIssuer extends MPBase {

  /** Unique identifier of the card issuer. */
  @SerializedName("id")
  private String id;

  /** Name of the issuing bank or financial institution. */
  @SerializedName("name")
  private String name;
}