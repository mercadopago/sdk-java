package com.mercadopago.client.preference;

import lombok.Builder;
import lombok.Getter;

/** Back URLs. */
@Getter
@Builder
public class PreferenceBackUrlsRequest {
  /** URL to return when the payment succeed. */
  private final String success;

  /** URL to return when the payment is pending. */
  private final String pending;

  /** URL to return when the payment fail. */
  private final String failure;
}
