package com.mercadopago.resources.preference;

import lombok.Getter;

/** Back URLs from preference. */
@Getter
public class PreferenceBackUrls {
  /** URL to return when the payment succeed. */
  private String success;

  /** URL to return when the payment is pending. */
  private String pending;

  /** URL to return when the payment fail. */
  private String failure;
}
