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

/**
 * Abstract base client for all MercadoPago API interactions.
 *
 * <p>This class provides the foundation for every specialized API client in the SDK. It handles
 * HTTP request execution, default and custom header management (including authorization via Bearer
 * tokens), timeout resolution with a three-level priority (request options, request object, global
 * config), and automatic idempotency key generation for {@code POST}, {@code PUT}, and
 * {@code PATCH} requests.
 *
 * <p>Subclasses should use the protected {@code send()}, {@code search()}, and {@code list()}
 * helper methods to communicate with the MercadoPago REST API.
 *
 * @see com.mercadopago.MercadoPagoConfig
 * @see MPHttpClient
 * @see MPRequestOptions
 */
public abstract class MercadoPagoClient {

  /** Default value for the {@code Accept} HTTP header. */
  private static final String ACCEPT_HEADER_VALUE = "application/json";

  /** Default value for the {@code Content-Type} HTTP header, including UTF-8 charset. */
  private static final String CONTENT_TYPE_HEADER_VALUE = "application/json; charset=UTF-8";

  /** Format string used to build the {@code Authorization: Bearer} header value. */
  private static final String BEARER = "Bearer %s";

  /** Path segment used to detect OAuth token requests and skip the Bearer header. */
  private static final String OAUTH_TOKEN = "/oauth/token";

  /** HTTP client used to execute every request dispatched by this client instance. */
  protected final MPHttpClient httpClient;

  /**
   * Default HTTP headers sent with every request. Populated during construction with
   * {@code Accept}, {@code Content-Type}, {@code User-Agent}, {@code Product-Id}, and
   * {@code Tracking-Id}.
   */
  protected Map<String, String> defaultHeaders;

  /**
   * Constructs a new {@code MercadoPagoClient} with the given HTTP client.
   *
   * <p>Initialises the default headers map with {@code Accept}, {@code Content-Type},
   * {@code User-Agent}, {@code Product-Id}, and {@code Tracking-Id} values taken from
   * {@link MercadoPagoConfig}.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
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
   * Sends an HTTP request using the pre-built {@link MPRequest} object with default configuration.
   *
   * <p>This is a convenience overload that delegates to {@link #send(MPRequest, MPRequestOptions)}
   * with {@code null} request options, meaning global configuration from
   * {@link MercadoPagoConfig} is used for timeouts and access tokens.
   *
   * @param request the {@link MPRequest} containing URI, HTTP method, payload, and query parameters
   * @return the {@link MPResponse} with status code, headers, and body returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  protected MPResponse send(MPRequest request) throws MPException, MPApiException {
    return this.send(request, null);
  }

  /**
   * Sends an HTTP request using the pre-built {@link MPRequest} object, applying optional
   * per-request overrides.
   *
   * <p>This method resolves the final URL (formatting query parameters), selects the access token
   * (preferring {@code requestOptions} over global config), merges default and custom headers,
   * and resolves timeouts with a three-level priority: request options &gt; request object &gt;
   * {@link MercadoPagoConfig} defaults. An idempotency key is automatically generated for
   * {@code POST}, {@code PUT}, and {@code PATCH} methods unless one is already present.
   *
   * @param request the {@link MPRequest} containing URI, HTTP method, payload, and query parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} with status code, headers, and body returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
   * Builds and sends an HTTP request from individual components with default configuration.
   *
   * <p>This is a convenience overload that delegates to
   * {@link #send(String, HttpMethod, JsonObject, Map, MPRequestOptions)} with {@code null}
   * request options.
   *
   * @param path the relative API path (e.g. {@code "/v1/payments"})
   * @param method the {@link HttpMethod} to use (GET, POST, PUT, DELETE, PATCH)
   * @param payload the JSON request body, or {@code null} for body-less requests
   * @param queryParams a map of query-string parameters, or {@code null} if none
   * @return the {@link MPResponse} with status code, headers, and body returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  protected MPResponse send(
      String path, HttpMethod method, JsonObject payload, Map<String, Object> queryParams)
      throws MPException, MPApiException {
    return this.send(path, method, payload, queryParams, null);
  }

  /**
   * Builds and sends an HTTP request from individual components, applying optional per-request
   * overrides.
   *
   * <p>Internally constructs an {@link MPRequest} via {@code buildRequest()}, then delegates to
   * {@link #send(MPRequest)}. This is the most flexible {@code send()} variant and is the
   * ultimate target of all other convenience overloads.
   *
   * @param path the relative API path (e.g. {@code "/v1/payments"})
   * @param method the {@link HttpMethod} to use (GET, POST, PUT, DELETE, PATCH)
   * @param payload the JSON request body, or {@code null} for body-less requests
   * @param queryParams a map of query-string parameters, or {@code null} if none
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} with status code, headers, and body returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
   * Performs a search request against the given API path using default configuration.
   *
   * <p>Delegates to {@link #search(String, MPSearchRequest, MPRequestOptions)} with {@code null}
   * request options.
   *
   * @param path the relative API path for the search endpoint (e.g. {@code "/v1/payments/search"})
   * @param request the {@link MPSearchRequest} containing search/filter/pagination parameters, or
   *     {@code null} for an unfiltered search
   * @return the {@link MPResponse} with the search results returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  protected MPResponse search(String path, MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(path, request, null);
  }

  /**
   * Performs a search request against the given API path, applying optional per-request overrides.
   *
   * <p>Extracts query parameters from the {@link MPSearchRequest} and issues an HTTP {@code GET}
   * via the underlying {@code send()} method. If {@code searchRequest} is {@code null}, no
   * query parameters are appended.
   *
   * @param path the relative API path for the search endpoint (e.g. {@code "/v1/payments/search"})
   * @param searchRequest the {@link MPSearchRequest} containing search/filter/pagination
   *     parameters, or {@code null} for an unfiltered search
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} with the search results returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  protected MPResponse search(
      String path, MPSearchRequest searchRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    Map<String, Object> queryParams =
        nonNull(searchRequest) ? searchRequest.getParameters() : null;

    return this.send(path, HttpMethod.GET, null, queryParams, requestOptions);
  }

  /**
   * Performs an HTTP {@code GET} request that returns a list of resources from the given API path.
   *
   * <p>This is a convenience overload that delegates to
   * {@link #list(String, HttpMethod, JsonObject, Map, MPRequestOptions)} using {@code GET} with
   * no payload or query parameters.
   *
   * @param path the relative API path for the list endpoint
   *     (e.g. {@code "/v1/customers/123/cards"})
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} containing the list of resources returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  protected MPResponse list(String path, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.list(path, HttpMethod.GET, null, null, requestOptions);
  }

  /**
   * Performs an HTTP request that returns a list of resources, with full control over all request
   * parameters.
   *
   * <p>This method is functionally identical to
   * {@link #send(String, HttpMethod, JsonObject, Map, MPRequestOptions)} and exists as a
   * semantic alias to improve readability in subclasses that list resources.
   *
   * @param path the relative API path for the list endpoint
   * @param method the {@link HttpMethod} to use (typically {@code GET})
   * @param payload the JSON request body, or {@code null} for body-less requests
   * @param queryParams a map of query-string parameters, or {@code null} if none
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link MPResponse} containing the list of resources returned by the API
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
