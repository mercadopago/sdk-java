package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.UrlFormatter;
import java.util.HashMap;
import java.util.Map;

public abstract class MercadoPagoClient {
  protected final MPHttpClient httpClient;

  private final String ACCEPT_HEADER_VALUE = "application/json";

  protected Map<String, String> defaultHeaders;

  public MercadoPagoClient(MPHttpClient httpClient) {
    this.httpClient = httpClient;
    this.defaultHeaders = new HashMap<>();
    defaultHeaders.put(Headers.ACCEPT, ACCEPT_HEADER_VALUE);
    defaultHeaders.put(Headers.PRODUCT_ID, MercadoPagoConfig.PRODUCT_ID);
    defaultHeaders.put(Headers.USER_AGENT, String.format("MercadoPago Java SDK %s", MercadoPagoConfig.CURRENT_VERSION));
    defaultHeaders.put(Headers.CONTENT_TYPE, "application/json");
  }

  protected MPResponse send(MPRequest request) throws MPException {
    for (Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
      request.addHeader(entry.getKey(), entry.getValue());
    }
    if (!request.getUri().contains("/oauth/token")) {
      request.addHeader("Authorization", String.format("Bearer %s", getAccessToken(request)));
    }
    return httpClient.send(addIdempotencyKey(request));
  }

  protected MPResponse send(String path, HttpMethod method, JsonObject payload) throws MPException {
    MPRequest mpRequest = new MPRequest(UrlFormatter.format(path), method, defaultHeaders, payload);
    return this.send(mpRequest);
  }

  protected MPResponse send(String path, HttpMethod method, JsonObject payload, Map<String, String> headers) throws MPException {
    MPRequest mpRequest = new MPRequest(UrlFormatter.format(path), method, headers, payload);
    return this.send(mpRequest);
  }

  private MPRequest addIdempotencyKey(MPRequest request) {
    if (request.getMethod() == HttpMethod.POST) {
      if (request instanceof IdempotentRequest) {
        request.addHeader(Headers.IDEMPOTENCY_KEY, ((IdempotentRequest) request).createIdempotencyKey());
      }
    }

    return request;
  }

  private String getAccessToken(MPRequest mpRequest) {
    if (mpRequest.getAccessToken() != null && !mpRequest.getAccessToken().isEmpty()) {
      return mpRequest.getAccessToken();
    }

    return MercadoPagoConfig.getAccessToken();
  }

  public MPHttpClient getHttpClient() {
    return httpClient;
  }
}
