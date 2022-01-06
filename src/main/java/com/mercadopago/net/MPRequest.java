package com.mercadopago.net;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Data;

@Data
public class MPRequest {
  private String uri;

  private HttpMethod method;

  private Map<String, String> headers;

  private JsonObject payload;

  private String accessToken;

  private int connectionTimeout;

  private int connectionRequestTimeout;

  private int socketTimeout;

  public MPRequest() {
    this.method = HttpMethod.GET;
    this.headers = new HashMap<>();
  }

  public MPRequest(String uri, HttpMethod method, Map<String, String> headers, JsonObject payload) {
    this.uri = uri;
    this.method = method;
    this.headers = headers;
    this.payload = payload;
  }

  public void addHeader(String name, String value) {
    if (Objects.isNull(this.headers)) {
      this.headers = new HashMap<>();
    }
    this.headers.put(name, value);
  }
}
