package com.mercadopago.client;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** RequestOptions class. */
@Data
@Builder
@AllArgsConstructor
public class RequestOptions {
  private String accessToken;

  private int connectionTimeout;

  private int connectionRequestTimeout;

  private int socketTimeout;

  private Map<String, String> customHeaders;
}
