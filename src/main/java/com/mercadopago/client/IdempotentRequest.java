package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRequest;
import java.util.Map;
import java.util.UUID;

public class IdempotentRequest extends MPRequest {

  public IdempotentRequest() {
    super();
  }

  public IdempotentRequest(String uri, HttpMethod method, Map<String, String> headers,
                           JsonObject payload) {
    super(uri, method, headers, payload);
  }

  public String createIdempotencyKey() {
    return UUID.randomUUID().toString();
  }
}
