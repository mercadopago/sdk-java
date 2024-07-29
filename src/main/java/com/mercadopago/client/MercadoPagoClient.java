package com.mercadopago.client;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

/** Mercado Pago client class. */
public abstract class MercadoPagoClient {
  private static final String ACCEPT_HEADER_VALUE = "application/json";

  private static final String CONTENT_TYPE_HEADER_VALUE = "application/json; charset=UTF-8";
  private static final String BEARER = "Bearer %s";
  private static final String OAUTH_TOKEN = "/oauth/token";

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
    return this.send(request, null);
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
    String uri = UrlFormatter.format(request.getUri(), request.getQueryParams());

    return httpClient.send(
        MPRequest.builder()
            .uri(uri)
            .accessToken(getAccessToken(requestOptions))
            .method(request.getMethod())
            .headers(addRequestHeaders(request, requestOptions))
            .payload(request.getPayload())
            .connectionRequestTimeout(addConnectionRequestTimeout(request, requestOptions))
            .connectionTimeout(addConnectionTimeout(request, requestOptions))
            .socketTimeout(addSocketTimeout(request, requestOptions))
            .build());
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
        nonNull(searchRequest) ? searchRequest.getParameters() : null;

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
    if (nonNull(requestOptions) && requestOptions.getSocketTimeout() > 0) {
      return requestOptions.getSocketTimeout();
    }

    if (nonNull(request) && request.getSocketTimeout() > 0) {
      return request.getSocketTimeout();
    }

    return MercadoPagoConfig.getSocketTimeout();
  }

  private int addConnectionTimeout(MPRequest request, MPRequestOptions requestOptions) {
    if (nonNull(requestOptions) && requestOptions.getConnectionTimeout() > 0) {
      return requestOptions.getConnectionTimeout();
    }

    if (nonNull(request) && request.getConnectionTimeout() > 0) {
      return request.getConnectionTimeout();
    }

    return MercadoPagoConfig.getConnectionTimeout();
  }

  private int addConnectionRequestTimeout(MPRequest request, MPRequestOptions requestOptions) {
    if (nonNull(requestOptions) && requestOptions.getConnectionRequestTimeout() > 0) {
      return requestOptions.getConnectionRequestTimeout();
    }

    if (nonNull(request) && request.getConnectionRequestTimeout() > 0) {
      return request.getConnectionRequestTimeout();
    }

    return MercadoPagoConfig.getConnectionRequestTimeout();
  }

  private Map<String, String> addRequestHeaders(MPRequest request, MPRequestOptions requestOptions) {

    Map<String, String> headers =
        nonNull(request.getHeaders()) ? request.getHeaders() : new HashMap<>();

    headers.putAll(addDefaultHeaders(request));

    if (isNotBlank(MercadoPagoConfig.getCorporationId())) {
      headers.put(Headers.CORPORATION_ID, MercadoPagoConfig.getCorporationId());
    }

    if (isNotBlank(MercadoPagoConfig.getIntegratorId())) {
      headers.put(Headers.INTEGRATOR_ID, MercadoPagoConfig.getIntegratorId());
    }

    if (isNotBlank(MercadoPagoConfig.getPlatformId())) {
      headers.put(Headers.PLATFORM_ID, MercadoPagoConfig.getPlatformId());
    }

    if (nonNull(requestOptions) && isNotEmpty(requestOptions.getCustomHeaders()) ) {
      for (Map.Entry<String, String> header : requestOptions.getCustomHeaders().entrySet()) {
        if (!headers.containsKey(header.getKey()) && !Headers.CONTENT_TYPE.equalsIgnoreCase(header.getKey())) {
          headers.put(header.getKey().toLowerCase(), header.getValue());
        }
      }
    }

    headers.putAll(addCustomHeaders(request.getUri(), requestOptions));
    return headers;
  }

  private Map<String, String> addDefaultHeaders(MPRequest request) {
    Map<String, String> headers = new HashMap<>(defaultHeaders);
    if (shouldAddIdempotencyKey(request)) {
      headers.put(Headers.IDEMPOTENCY_KEY, request.createIdempotencyKey());
    }

    if (nonNull(request) && !request.getUri().contains(OAUTH_TOKEN) && !headers.containsKey(Headers.AUTHORIZATION)) {
      headers.put(Headers.AUTHORIZATION, String.format(BEARER, chooseAccessToken(request)));
    }

    return headers;
  }

  private String chooseAccessToken(MPRequest request) {
    return request.getAccessToken() != null ? request.getAccessToken() : MercadoPagoConfig.getAccessToken();
  }

  private Map<String, String> addCustomHeaders(String uri, MPRequestOptions requestOptions) {
    Map<String, String> headers = new HashMap<>();
    if (nonNull(requestOptions) && nonNull(requestOptions.getCustomHeaders())) {
      for (Map.Entry<String, String> entry : requestOptions.getCustomHeaders().entrySet()) {
        headers.put(entry.getKey().toLowerCase(), entry.getValue());
      }
    }

    if (requestOptions!= null && !uri.contains(OAUTH_TOKEN)) {
      headers.put(Headers.AUTHORIZATION, String.format(BEARER, getAccessToken(requestOptions)));
    }

    return headers;
  }

  private boolean shouldAddIdempotencyKey(MPRequest request) {

    boolean containsIdempotency = false;
    if (nonNull(request) && nonNull(request.getHeaders())) {
      containsIdempotency = request.getHeaders().containsKey(Headers.IDEMPOTENCY_KEY.toLowerCase());
    }

    if (containsIdempotency) return false;

    return request.getMethod() == HttpMethod.POST ||
        request.getMethod() == HttpMethod.PUT ||
        request.getMethod() == HttpMethod.PATCH;
  }

  private String getAccessToken(MPRequestOptions requestOptions) {
    return nonNull(requestOptions)
            && nonNull(requestOptions.getAccessToken())
            && !requestOptions.getAccessToken().isEmpty()
        ? requestOptions.getAccessToken()
        : MercadoPagoConfig.getAccessToken();
  }
}
