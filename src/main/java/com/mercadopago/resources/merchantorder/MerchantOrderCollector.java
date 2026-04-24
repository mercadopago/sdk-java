package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/**
 * Represents the collector (seller) of a merchant order in the MercadoPago API.
 *
 * <p>Contains the seller's identifier and nickname, identifying the merchant who will receive
 * the payment for the order.
 *
 * @see MerchantOrder
 */
@Getter
public class MerchantOrderCollector {
  /** Unique identifier of the collector (seller). */
  private Long id;

  /** Display nickname of the collector (seller). */
  private String nickname;
}
