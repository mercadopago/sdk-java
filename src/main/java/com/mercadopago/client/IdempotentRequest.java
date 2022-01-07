package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRequest;
import java.util.Map;
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
    super(uri, method, headers, payload);
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
