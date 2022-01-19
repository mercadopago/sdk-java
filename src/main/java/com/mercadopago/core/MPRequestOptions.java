package com.mercadopago.core;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

/** MPRequestOptions class. */
@Data
@Builder
public class MPRequestOptions {
  private String accessToken;

  private int connectionTimeout;

  private int connectionRequestTimeout;

  private int socketTimeout;

  private Map<String, String> customHeaders;

  /**
   * Create default MPRequestOptions.
   *
   * @return MPRequestOptions
   */
  public static MPRequestOptions createDefault() {
    return MPRequestOptions.builder().build();
  }
}
