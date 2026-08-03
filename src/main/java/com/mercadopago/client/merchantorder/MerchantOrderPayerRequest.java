package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing the payer associated with a Merchant Order. Identifies the buyer
 * who will pay for the items in the order.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderPayerRequest {
  /** Unique identifier of the payer in Mercado Pago. */
  private final Long id;

  /** Display nickname or username of the payer. */
  private final String nickname;
}
