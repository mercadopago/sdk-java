package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.net.UrlFormatter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

/** Mercado Pago client class. */
public abstract class MercadoPagoClient {
  private static final String ACCEPT_HEADER_VALUE = "application/json";

  @Getter protected final MPHttpClient httpClient;

  protected Map<String, String> defaultHeaders;

  /**
   * MercadoPagoClient constructor.
   *
   * @param httpClient http client
   */
  public MercadoPagoClient(MPHttpClient httpClient) {
    this.httpClient = httpClient;
    this.defaultHeaders = new HashMap<>();
    defaultHeaders.put(Headers.ACCEPT, ACCEPT_HEADER_VALUE);
    defaultHeaders.put(Headers.PRODUCT_ID, MercadoPagoConfig.PRODUCT_ID);
    defaultHeaders.put(
        Headers.USER_AGENT,
        String.format("MercadoPago Java SDK %s", MercadoPagoConfig.CURRENT_VERSION));
    defaultHeaders.put(Headers.TRACKING_ID,
        String.format("platform:%s,type:SDK%s,so;",
            MercadoPagoConfig.getJavaVersion(System.getProperty("java.runtime.version")),
            MercadoPagoConfig.CURRENT_VERSION));
    defaultHeaders.put(Headers.CONTENT_TYPE, ACCEPT_HEADER_VALUE);
  }

  protected MPResponse send(MPRequest request) throws MPException {
    addDefaultHeaders(request);
    addDefaultTimeouts(request);
    addQueryParams(request, null);
    addIdempotencyKey(request);

    return httpClient.send(request);
  }


  protected MPResponse send(String path, HttpMethod method, Map<String, Object> queryParams, JsonObject payload) throws MPException {
    return this.send(path, method, payload, queryParams, null);
  }

  protected MPResponse send(
      String path, HttpMethod method, JsonObject payload, Map<String, Object> queryParams, MPRequestOptions requestOptions)
      throws MPException {
    MPRequest mpRequest = buildRequest(path, method, payload, queryParams, requestOptions);
    return this.send(mpRequest);
  }

  protected MPResponse search(String path, MPSearchRequest request) throws MPException {
    return this.search(path, request, null);
  }

  protected MPResponse search(String path, MPSearchRequest request, MPRequestOptions requestOptions) throws MPException {
    Map<String, Object> queryParams = null;

    if(Objects.nonNull(request)) {
      queryParams = request.getParameters();
    }
    return send(path, HttpMethod.GET, null, queryParams, requestOptions);
  }

  protected MPResponse list(
      String path, MPSearchRequest searchRequest, MPRequestOptions requestOptions)
      throws MPException {
    return this.list(path, HttpMethod.GET, null, searchRequest, null, requestOptions);
  }

  protected MPResponse list(String path,
                            HttpMethod method,
                            JsonObject payload,
                            MPSearchRequest searchRequest,
                            Map<String, Object> queryParams,
                            MPRequestOptions requestOptions)
      throws MPException {
    if(Objects.nonNull(queryParams) && Objects.nonNull(searchRequest)) {
      queryParams.putAll(searchRequest.getParameters());
    }
    return this.send(path, method, payload, queryParams, requestOptions);
  }

  private MPRequest addIdempotencyKey(MPRequest request) {
    if (request.getMethod() == HttpMethod.POST) {
      if (request instanceof IdempotentRequest) {
        request.addHeader(
            Headers.IDEMPOTENCY_KEY, ((IdempotentRequest) request).createIdempotencyKey());
      }
    }

    return request;
  }

  private MPRequest buildRequest(String path, HttpMethod method) throws MPException {
    return this.buildRequest(path, method, null, null, null);
  }

  private MPRequest buildRequest(String path, HttpMethod method, JsonObject payload) throws MPException {
    return this.buildRequest(path, method, payload, null, null);
  }

  private MPRequest buildRequest(String path, HttpMethod method, JsonObject payload, Map<String, Object> queryParams) throws MPException {
    return this.buildRequest(path, method, payload, queryParams, null);
  }

  private MPRequest buildRequest(String path, HttpMethod method, JsonObject payload, Map<String, Object> queryParams, MPRequestOptions requestOptions)
      throws MPException {
    MPRequest request = new MPRequest();
    request.setUri(UrlFormatter.format(path));
    request.setAccessToken(getAccessToken(requestOptions));
    if(Objects.nonNull(payload)) {
      request.setPayload(payload);
    }
    request.setMethod(method);
    addQueryParams(request, queryParams);
    addCustomHeaders(request, requestOptions);
    addCustomTimeouts(request, requestOptions);

    return request;
  }

  private MPRequest addQueryParams(MPRequest request, Map<String, Object> queryParams) throws MPException {

    try {
        URL url = new URL(request.getUri());
        if(Objects.nonNull(request.getQueryParams()) && Objects.isNull(url.getQuery())) {
          request.setUri(UrlFormatter.format(request.getUri(), request.getQueryParams()));
        }
        else if(Objects.isNull(url.getQuery()) && Objects.nonNull(queryParams)) {
          request.setUri(UrlFormatter.format(request.getUri(), queryParams));
        }
      } catch (UnsupportedEncodingException | MalformedURLException e) {
        throw new MPException(String.format("Error while trying to add query string to path: %s", e.getMessage()));
      }
    return request;
  }

  private MPRequest addDefaultHeaders(MPRequest request) {
    for (Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
      request.addHeader(entry.getKey(), entry.getValue());
    }

    if (!request.getUri().contains("/oauth/token") && !request.getHeaders().containsKey("Authorization")) {
      request.addHeader("Authorization", String.format("Bearer %s", getAccessToken(null)));
    }

    return request;
  }

  private MPRequest addCustomHeaders(MPRequest request, MPRequestOptions requestOptions) {
    if(Objects.nonNull(requestOptions) && Objects.nonNull(requestOptions.getCustomHeaders())) {
      for (Map.Entry<String, String> entry : requestOptions.getCustomHeaders().entrySet()) {
        request.addHeader(entry.getKey(), entry.getValue());
      }
    }

    if (!request.getUri().contains("/oauth/token")) {
      request.addHeader("Authorization", String.format("Bearer %s", getAccessToken(requestOptions)));
    }

    return request;
  }

  private MPRequest addCustomTimeouts(MPRequest request, MPRequestOptions requestOptions) {
    if(Objects.nonNull(requestOptions)) {
      if (requestOptions.getConnectionTimeout() > 0) {
        request.setConnectionTimeout(requestOptions.getConnectionTimeout());
      }
      if (requestOptions.getConnectionRequestTimeout() > 0) {
        request.setConnectionRequestTimeout(requestOptions.getConnectionRequestTimeout());
      }
      if (requestOptions.getSocketTimeout() > 0) {
        request.setSocketTimeout(requestOptions.getSocketTimeout());
      }
    }

    return request;
  }

  private MPRequest addDefaultTimeouts(MPRequest request) {
      if (request.getConnectionTimeout() == 0) {
        request.setConnectionTimeout(MercadoPagoConfig.getConnectionTimeout());
      }
      if (request.getConnectionRequestTimeout() == 0) {
        request.setConnectionRequestTimeout(MercadoPagoConfig.getConnectionRequestTimeout());
      }
      if (request.getSocketTimeout() == 0) {
        request.setSocketTimeout(MercadoPagoConfig.getSocketTimeout());
      }

    return request;
  }

  private String getAccessToken(MPRequestOptions requestOptions) {
    if (Objects.nonNull(requestOptions) && Objects.nonNull(requestOptions.getAccessToken()) && !requestOptions.getAccessToken().isEmpty()) {
      return requestOptions.getAccessToken();
    }

    return MercadoPagoConfig.getAccessToken();
  }
}
