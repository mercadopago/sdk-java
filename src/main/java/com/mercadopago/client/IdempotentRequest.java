package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.UrlFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;

/** IdempotentRequest class. */
public class IdempotentRequest extends MPRequest {

  /**
   * Idempotent request constructor.
   *
   * @param uri uri
   * @param method method
   * @param headers headers
   * @param payload payload
   * @param queryParams queryParams
   * @param accessToken accessToken
   * @param connectionTimeout connectionTimeout
   * @param connectionRequestTimeout connectionRequestTimeout
   * @param socketTimeout socketTimeout
   */
  @Builder(builderMethodName = "idempotentBuilder")
  public IdempotentRequest(
      String uri,
      HttpMethod method,
      Map<String, String> headers,
      JsonObject payload,
      Map<String, Object> queryParams,
      String accessToken,
      int connectionTimeout,
      int connectionRequestTimeout,
      int socketTimeout) {
    super(
        uri,
        method,
        headers,
        payload,
        queryParams,
        accessToken,
        connectionTimeout,
        connectionRequestTimeout,
        socketTimeout);
  }

  /**
   * Method responsible for build Idempotent request.
   *
   * @param path path
   * @param method method
   * @param payload payload
   * @param queryParams queryParams
   * @param requestOptions requestOptions
   * @return IdempotentRequest
   */
  public static IdempotentRequest buildRequest(
      String path,
      HttpMethod method,
      JsonObject payload,
      Map<String, Object> queryParams,
      MPRequestOptions requestOptions) {
    IdempotentRequest idempotentRequest;
    String uri = UrlFormatter.format(path);

    if (Objects.nonNull(requestOptions)) {
      idempotentRequest =
          IdempotentRequest.idempotentBuilder()
              .uri(uri)
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
      idempotentRequest =
          IdempotentRequest.idempotentBuilder().uri(uri).method(method).payload(payload).build();
    }

    return idempotentRequest;
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
