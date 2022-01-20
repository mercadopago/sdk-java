package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.UrlFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** IdempotentRequest class. */
public class IdempotentRequest extends MPRequest {

  /**
   * IdempotentRequest constructor.
   *
   * @param uri uri
   * @param method method
   * @param headers headers
   * @param payload payload
   */
  public IdempotentRequest(
      String uri, HttpMethod method, Map<String, String> headers, JsonObject payload) {
    this(uri, method, headers, payload, null);
  }

  /**
   * IdempotentRequest constructor.
   *
   * @param uri uri
   * @param method method
   * @param headers headers
   * @param payload payload
   * @param queryParams queryParams
   */
  public IdempotentRequest(
      String uri,
      HttpMethod method,
      Map<String, String> headers,
      JsonObject payload,
      Map<String, Object> queryParams) {
    super(uri, method, headers, payload, queryParams);
  }

  /**
   * Method responsible for create a new Idempotency key.
   *
   * @return Idempotency key
   */
  public String createIdempotencyKey() {
    return UUID.randomUUID().toString();
  }

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
          new IdempotentRequest(
              uri, method, requestOptions.getCustomHeaders(), payload, queryParams);
      idempotentRequest.setAccessToken(requestOptions.getAccessToken());
      idempotentRequest.setConnectionRequestTimeout(requestOptions.getConnectionRequestTimeout());
      idempotentRequest.setConnectionTimeout(requestOptions.getConnectionTimeout());
      idempotentRequest.setSocketTimeout(requestOptions.getSocketTimeout());
    } else {
      idempotentRequest = new IdempotentRequest(uri, method, null, payload);
    }

    return idempotentRequest;
  }
}
