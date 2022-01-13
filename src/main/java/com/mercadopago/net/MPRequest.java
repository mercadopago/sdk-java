package com.mercadopago.net;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Data;

/** MPRequest class. */
@Data
public class MPRequest {
  private String uri;

  private HttpMethod method;

  private Map<String, String> headers;

  private JsonObject payload;

  private Map<String, Object> queryParams;

  private String accessToken;

  private int connectionTimeout;

  private int connectionRequestTimeout;

  private int socketTimeout;

  /** MPRequest constructor. */
  public MPRequest() {
    this.method = HttpMethod.GET;
    this.headers = new HashMap<>();
  }

  /**
   * MPRequest constructor.
   *
   * @param uri uri
   * @param method method
   * @param headers headers
   * @param payload payload
   */
  public MPRequest(String uri, HttpMethod method, Map<String, String> headers, JsonObject payload) {
    this(uri, method, headers, payload, null);
  }

  /**
   * MPRequest constructor.
   *
   * @param uri uri
   * @param method method
   * @param headers headers
   * @param payload payload
   */
  public MPRequest(String uri, HttpMethod method, Map<String, String> headers, JsonObject payload, Map<String, Object> queryParams) {
    this.uri = uri;
    this.method = method;
    this.headers = headers;
    this.payload = payload;
    this.queryParams = queryParams;
  }

  /**
   * Method responsible to add a new header to request.
   *
   * @param name header name
   * @param value header value
   */
  public void addHeader(String name, String value) {
    if (Objects.isNull(this.headers)) {
      this.headers = new HashMap<>();
    }
    this.headers.put(name, value);
  }
}
