package com.mercadopago.client;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

/** RequestOptions class. */
@Data
@AllArgsConstructor
public class RequestOptions {
  private String accessToken;

  private Map<String, String> customHeaders;
}
