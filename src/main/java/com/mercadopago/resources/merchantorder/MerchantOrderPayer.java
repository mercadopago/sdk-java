package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/**
 * Represents the payer (buyer) of a merchant order in the MercadoPago API.
 *
 * <p>Contains the buyer's identifier and nickname, identifying the customer who placed the order.
 *
 * @see MerchantOrder
 */
@Getter
public class MerchantOrderPayer {
  /** Unique identifier of the payer (buyer). */
  private Long id;

  /** Display nickname of the payer (buyer). */
  private String nickname;
}
