package com.mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPRequestOptions;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/** MPRequest class. */
@Getter
@Builder
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

  /**
   * Method responsible for build MP request.
   *
   * @param path path
   * @param method method
   * @param payload payload
   * @param queryParams queryParams
   * @param requestOptions requestOptions
   * @return MPRequest
   */
  public static MPRequest buildRequest(
      String path,
      HttpMethod method,
      JsonObject payload,
      Map<String, Object> queryParams,
      MPRequestOptions requestOptions) {
    MPRequest mpRequest;

    if (Objects.nonNull(requestOptions)) {
      mpRequest =
          MPRequest.builder()
              .uri(path)
              .method(method)
              .headers(requestOptions.getCustomHeaders())
              .payload(payload)
              .queryParams(queryParams)
              .accessToken(requestOptions.getAccessToken())
              .connectionRequestTimeout(requestOptions.getConnectionRequestTimeout())
              .connectionTimeout(requestOptions.getConnectionTimeout())
              .socketTimeout(requestOptions.getSocketTimeout())
              .build();
    } else {
      mpRequest = MPRequest.builder().uri(path).method(method).payload(payload).build();
    }

    return mpRequest;
  }

  /**
   * Method responsible for create a new Idempotency key.
   *
   * @return Idempotency key
   */
  public String createIdempotencyKey() {
    return UUID.randomUUID().toString();
  }
}
