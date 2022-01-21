package com.mercadopago.net;

import com.google.gson.JsonObject;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** MPRequest class. */
@Getter
@SuperBuilder
public class MPRequest {
  private final String uri;

  private final HttpMethod method;

  private final Map<String, String> headers;

  private final JsonObject payload;

  private final Map<String, Object> queryParams;

  private final String accessToken;

  private final int connectionTimeout;

  private final int connectionRequestTimeout;

  private final int socketTimeout;
}
