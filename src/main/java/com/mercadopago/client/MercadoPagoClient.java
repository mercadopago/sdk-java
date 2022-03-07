package com.mercadopago.client;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.net.UrlFormatter;
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
   * Method used directly or by other methods to make requests.
   *
   * @param request request data
   * @return MPResponse response object
   * @throws MPException exception
   */
  protected MPResponse send(MPRequest request) throws MPException, MPApiException {
    String uri = UrlFormatter.format(request.getUri(), request.getQueryParams());

    return httpClient.send(
        MPRequest.builder()
            .uri(uri)
            .method(request.getMethod())
            .headers(addDefaultHeaders(request))
            .payload(request.getPayload())
            .connectionRequestTimeout(addConnectionRequestTimeout(request, null))
            .connectionTimeout(addConnectionTimeout(request, null))
            .socketTimeout(addSocketTimeout(request, null))
            .build());
  }

  /**
   * Method used directly or by other methods to make requests with request options.
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return MPResponse response
   * @throws MPException exception
   */
  protected MPResponse send(MPRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.send(
        this.buildRequest(
            request.getUri(),
            request.getMethod(),
            request.getPayload(),
            request.getQueryParams(),
            requestOptions));
  }

  /**
   * Method used directly or by other methods to make requests.
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
      throws MPException, MPApiException {
    return this.send(path, method, payload, queryParams, null);
  }

  /**
   * Method used directly or by other methods to make requests.
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
      MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPRequest mpRequest = buildRequest(path, method, payload, queryParams, requestOptions);
    return this.send(mpRequest);
  }

  /**
   * Convenience method to perform searches.
   *
   * @param path path of request url
   * @param request parameters for performing search request
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse search(String path, MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(path, request, null);
  }

  /**
   * Convenience method to perform searches.
   *
   * @param path path of searchRequest url
   * @param searchRequest parameters for performing search searchRequest
   * @param requestOptions extra data used to override configuration passed to MercadoPagoConfig for
   *     a single searchRequest
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse search(
      String path, MPSearchRequest searchRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    Map<String, Object> queryParams =
        Objects.nonNull(searchRequest) ? searchRequest.getParameters() : null;

    return this.send(path, HttpMethod.GET, null, queryParams, requestOptions);
  }

  /**
   * Convenience method to perform requests that returns lists of results.
   *
   * @param path path of request url
   * @param requestOptions extra data used to override configuration passed to MercadoPagoConfig for
   *     a single request
   * @return response data
   * @throws MPException exception
   */
  protected MPResponse list(String path, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.list(path, HttpMethod.GET, null, null, requestOptions);
  }

  /**
   * Convenience method to perform requests that returns lists of results.
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
      MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.send(path, method, payload, queryParams, requestOptions);
  }

  private MPRequest buildRequest(
      String path,
      HttpMethod method,
      JsonObject payload,
      Map<String, Object> queryParams,
      MPRequestOptions requestOptions) {

    return MPRequest.builder()
        .uri(path)
        .accessToken(getAccessToken(requestOptions))
        .payload(payload)
        .method(method)
        .queryParams(queryParams)
        .headers(addCustomHeaders(path, requestOptions))
        .connectionRequestTimeout(addConnectionRequestTimeout(null, requestOptions))
        .connectionTimeout(addConnectionTimeout(null, requestOptions))
        .socketTimeout(addSocketTimeout(null, requestOptions))
        .build();
  }

  private int addSocketTimeout(MPRequest request, MPRequestOptions requestOptions) {
    if (Objects.nonNull(requestOptions) && requestOptions.getSocketTimeout() > 0) {
      return requestOptions.getSocketTimeout();
    }

    if (Objects.nonNull(request) && request.getSocketTimeout() > 0) {
      return request.getSocketTimeout();
    }

    return MercadoPagoConfig.getSocketTimeout();
  }

  private int addConnectionTimeout(MPRequest request, MPRequestOptions requestOptions) {
    if (Objects.nonNull(requestOptions) && requestOptions.getConnectionTimeout() > 0) {
      return requestOptions.getConnectionTimeout();
    }

    if (Objects.nonNull(request) && request.getConnectionTimeout() > 0) {
      return request.getConnectionTimeout();
    }

    return MercadoPagoConfig.getConnectionTimeout();
  }

  private int addConnectionRequestTimeout(MPRequest request, MPRequestOptions requestOptions) {
    if (Objects.nonNull(requestOptions) && requestOptions.getConnectionRequestTimeout() > 0) {
      return requestOptions.getConnectionRequestTimeout();
    }

    if (Objects.nonNull(request) && request.getConnectionRequestTimeout() > 0) {
      return request.getConnectionRequestTimeout();
    }

    return MercadoPagoConfig.getConnectionRequestTimeout();
  }

  private Map<String, String> addCustomHeaders(String uri, MPRequestOptions requestOptions) {
    Map<String, String> headers = new HashMap<>();

    if (Objects.nonNull(requestOptions) && Objects.nonNull(requestOptions.getCustomHeaders())) {
      for (Map.Entry<String, String> entry : requestOptions.getCustomHeaders().entrySet()) {
        headers.put(entry.getKey(), entry.getValue());
      }
    }

    if (!uri.contains("/oauth/token")) {
      headers.put("Authorization", String.format("Bearer %s", getAccessToken(requestOptions)));
    }
    return headers;
  }

  private Map<String, String> addDefaultHeaders(MPRequest request) {
    Map<String, String> headers =
        Objects.nonNull(request.getHeaders()) ? request.getHeaders() : new HashMap<>();

    for (Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
      headers.put(entry.getKey(), entry.getValue());
    }

    if (shouldAddIdempotencyKey(request)) {
      headers.put(Headers.IDEMPOTENCY_KEY, request.createIdempotencyKey());
    }

    if (!request.getUri().contains("/oauth/token") && !headers.containsKey("Authorization")) {
      headers.put("Authorization", String.format("Bearer %s", getAccessToken(null)));
    }

    return headers;
  }

  private boolean shouldAddIdempotencyKey(MPRequest request) {
    return request.getMethod() == HttpMethod.POST;
  }

  private String getAccessToken(MPRequestOptions requestOptions) {
    return Objects.nonNull(requestOptions)
            && Objects.nonNull(requestOptions.getAccessToken())
            && !requestOptions.getAccessToken().isEmpty()
        ? requestOptions.getAccessToken()
        : MercadoPagoConfig.getAccessToken();
  }
}
