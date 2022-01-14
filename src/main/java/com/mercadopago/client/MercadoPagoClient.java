package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
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

/** Mercado Pago client class. */
public abstract class MercadoPagoClient {
  private static final String ACCEPT_HEADER_VALUE = "application/json";

  private static final String CONTENT_TYPE_HEADER_VALUE = "application/json; charset=UTF-8";

  protected final MPHttpClient httpClient;

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
        String.format("MercadoPago Java SDK/%s", MercadoPagoConfig.CURRENT_VERSION));
    defaultHeaders.put(Headers.TRACKING_ID, MercadoPagoConfig.TRACKING_ID);
    defaultHeaders.put(Headers.CONTENT_TYPE, CONTENT_TYPE_HEADER_VALUE);
  }

  /**
   * Method used directly or by other methods to make requests
   *
   * @param request request data
   * @return MPResponse response object
   * @throws MPException exception
   */
  protected MPResponse send(MPRequest request) throws MPException {
    addDefaultHeaders(request);
    addDefaultTimeouts(request);
    addQueryParams(request, null);
    addIdempotencyKey(request);

    return httpClient.send(request);
  }

  /**
   * Method used directly or by other methods to make requests with request options
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return MPResponse response
   * @throws MPException exception
   */
  protected MPResponse send(MPRequest request, RequestOptions requestOptions) throws MPException {
    addCustomHeaders(request, requestOptions);
    addCustomTimeouts(request, requestOptions);

    return this.send(request);
  }

  /**
   * Method used directly or by other methods to make requests
   *
   * @param path path of request url
   * @param method http method used in the request
   * @param payload request body
   * @param queryParams query string params
   * @return MPResponse response data
   * @throws MPException exception
   */
  protected MPResponse send(
      String path, HttpMethod method, JsonObject payload, Map<String, Object> queryParams)
      throws MPException {
    return this.send(path, method, payload, queryParams, null);
  }

  /**
   * Method used directly or by other methods to make requests
   *
   * @param path path of request url
   * @param method http method used in the request
   * @param payload request body
   * @param queryParams query string params
   * @param requestOptions extra data used to override configuration passed to MercadoPagoConfig for
   *     a single request
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse send(
      String path,
      HttpMethod method,
      JsonObject payload,
      Map<String, Object> queryParams,
      RequestOptions requestOptions)
      throws MPException {
    MPRequest mpRequest = buildRequest(path, method, payload, queryParams, requestOptions);
    return this.send(mpRequest);
  }

  /**
   * Convenience method to perform searches
   *
   * @param path path of request url
   * @param request parameters for performing search request
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse search(String path, MPSearchRequest request) throws MPException {
    return this.search(path, request, null);
  }

  /**
   * Convenience method to perform searches
   *
   * @param path path of searchRequest url
   * @param searchRequest parameters for performing search searchRequest
   * @param requestOptions extra data used to override configuration passed to MercadoPagoConfig for
   *     a single searchRequest
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse search(
      String path, MPSearchRequest searchRequest, RequestOptions requestOptions)
      throws MPException {
    Map<String, Object> queryParams =
        Objects.nonNull(searchRequest) ? searchRequest.getParameters() : null;

    return this.send(path, HttpMethod.GET, null, queryParams, requestOptions);
  }

  /**
   * Convenience method to perform requests that returns lists of results
   *
   * @param path path of request url
   * @param requestOptions extra data used to override configuration passed to MercadoPagoConfig for
   *     a single request
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse list(String path, RequestOptions requestOptions) throws MPException {
    return this.list(path, HttpMethod.GET, null, null, requestOptions);
  }

  /**
   * Convenience method to perform requests that returns lists of results
   *
   * @param path path of request url
   * @param method http method used in the request
   * @param payload request body
   * @param queryParams query string params
   * @param requestOptions extra data used to override configuration passed to MercadoPagoConfig for
   *     a single request
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse list(
      String path,
      HttpMethod method,
      JsonObject payload,
      Map<String, Object> queryParams,
      RequestOptions requestOptions)
      throws MPException {
    return this.send(path, method, payload, queryParams, requestOptions);
  }

  private void addIdempotencyKey(MPRequest request) {
    if (request.getMethod() == HttpMethod.POST) {
      if (request instanceof IdempotentRequest) {
        request.addHeader(
            Headers.IDEMPOTENCY_KEY, ((IdempotentRequest) request).createIdempotencyKey());
      }
    }
  }

  private MPRequest buildRequest(String path, HttpMethod method) throws MPException {
    return this.buildRequest(path, method, null, null, null);
  }

  private MPRequest buildRequest(String path, HttpMethod method, JsonObject payload)
      throws MPException {
    return this.buildRequest(path, method, payload, null, null);
  }

  private MPRequest buildRequest(
      String path, HttpMethod method, JsonObject payload, Map<String, Object> queryParams)
      throws MPException {
    return this.buildRequest(path, method, payload, queryParams, null);
  }

  private MPRequest buildRequest(
      String path,
      HttpMethod method,
      JsonObject payload,
      Map<String, Object> queryParams,
      RequestOptions requestOptions)
      throws MPException {
    MPRequest request = new MPRequest();
    request.setUri(UrlFormatter.format(path));
    request.setAccessToken(getAccessToken(requestOptions));
    if (Objects.nonNull(payload)) {
      request.setPayload(payload);
    }
    request.setMethod(method);
    addQueryParams(request, queryParams);
    addCustomHeaders(request, requestOptions);
    addCustomTimeouts(request, requestOptions);

    return request;
  }

  private void addQueryParams(MPRequest request, Map<String, Object> queryParams)
      throws MPException {

    try {
      URL url = new URL(request.getUri());
      if (Objects.nonNull(request.getQueryParams()) && Objects.isNull(url.getQuery())) {
        request.setUri(UrlFormatter.format(request.getUri(), request.getQueryParams()));
      } else if (Objects.isNull(url.getQuery()) && Objects.nonNull(queryParams)) {
        request.setUri(UrlFormatter.format(request.getUri(), queryParams));
      }
    } catch (UnsupportedEncodingException | MalformedURLException e) {
      throw new MPException(
          String.format("Error while trying to add query string to path: %s", e.getMessage()));
    }
  }

  private void addDefaultHeaders(MPRequest request) {
    for (Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
      request.addHeader(entry.getKey(), entry.getValue());
    }

    if (!request.getUri().contains("/oauth/token")
        && !request.getHeaders().containsKey("Authorization")) {
      request.addHeader("Authorization", String.format("Bearer %s", getAccessToken(null)));
    }
  }

  private void addCustomHeaders(MPRequest request, RequestOptions requestOptions) {
    if (Objects.nonNull(requestOptions) && Objects.nonNull(requestOptions.getCustomHeaders())) {
      for (Map.Entry<String, String> entry : requestOptions.getCustomHeaders().entrySet()) {
        request.addHeader(entry.getKey(), entry.getValue());
      }
    }

    if (!request.getUri().contains("/oauth/token")) {
      request.addHeader(
          "Authorization", String.format("Bearer %s", getAccessToken(requestOptions)));
    }
  }

  private void addCustomTimeouts(MPRequest request, RequestOptions requestOptions) {
    if (Objects.nonNull(requestOptions)) {
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
  }

  private void addDefaultTimeouts(MPRequest request) {
    if (request.getConnectionTimeout() == 0) {
      request.setConnectionTimeout(MercadoPagoConfig.getConnectionTimeout());
    }
    if (request.getConnectionRequestTimeout() == 0) {
      request.setConnectionRequestTimeout(MercadoPagoConfig.getConnectionRequestTimeout());
    }
    if (request.getSocketTimeout() == 0) {
      request.setSocketTimeout(MercadoPagoConfig.getSocketTimeout());
    }
  }

  private String getAccessToken(RequestOptions requestOptions) {
    if (Objects.nonNull(requestOptions)
        && Objects.nonNull(requestOptions.getAccessToken())
        && !requestOptions.getAccessToken().isEmpty()) {
      return requestOptions.getAccessToken();
    }

    return MercadoPagoConfig.getAccessToken();
  }
}
