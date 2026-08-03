package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource representing the redirect URLs configured in a checkout preference.
 *
 * <p>Defines the URLs where the buyer is redirected after completing the checkout flow, depending
 * on the payment outcome: approved, pending, or failed.
 *
 * @see Preference
 */
@Getter
public class PreferenceBackUrls {
  /** URL to redirect the buyer when the payment is approved. */
  private String success;

  /** URL to redirect the buyer when the payment is pending review or processing. */
  private String pending;

  /** URL to redirect the buyer when the payment is rejected or fails. */
  private String failure;
}
