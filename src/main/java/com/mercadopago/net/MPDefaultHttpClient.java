package com.mercadopago.net;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.net.HttpStatus.BAD_REQUEST;
import static com.mercadopago.net.HttpStatus.FORBIDDEN;
import static com.mercadopago.net.HttpStatus.INTERNAL_SERVER_ERROR;
import static com.mercadopago.net.HttpStatus.TOO_MANY_REQUESTS;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPAuthenticationException;
import com.mercadopago.exceptions.MPBadRequestException;
import com.mercadopago.exceptions.MPConnectionException;
import com.mercadopago.exceptions.MPDependencyException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPForbiddenException;
import com.mercadopago.exceptions.MPIdempotencyException;
import com.mercadopago.exceptions.MPMalformedRequestException;
import com.mercadopago.exceptions.MPNotFoundException;
import com.mercadopago.exceptions.MPPaymentException;
import com.mercadopago.exceptions.MPRateLimitException;
import com.mercadopago.exceptions.MPResourceLockedException;
import com.mercadopago.exceptions.MPServerException;
import com.mercadopago.exceptions.MPValidationException;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/** Mercado Pago default Http Client class. */
public class MPDefaultHttpClient implements MPHttpClient {
  private static final int VALIDATE_INACTIVITY_INTERVAL_MS = 30000;

  private static final int DEFAULT_RETRIES = 3;

  private static final String UTF_8 = "UTF-8";

  private static final String PAYLOAD_NOT_SUPPORTED_MESSAGE =
      "Payload not supported for this method.";

  private static final String HEADER_LOG_FORMAT = "%s: %s%s";

  private static final String API_ERROR_MESSAGE = "Api error. Check response for details";

  private static final String RETRY_AFTER_HEADER = "Retry-After";

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String REDACTED = "***";

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private static final Logger LOGGER = Logger.getLogger(MPDefaultHttpClient.class.getName());

  private final HttpClient httpClient;

  /** MPDefaultHttpClient constructor. */
  public MPDefaultHttpClient() {
    this(null);
  }

  /** MPDefaultHttpClient constructor for testing only. */
  protected MPDefaultHttpClient(HttpClient httpClient) {
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());

    if (Objects.isNull(httpClient)) {
      this.httpClient = createHttpClient();
    } else {
      this.httpClient = httpClient;
    }
  }

  private HttpClient createHttpClient() {
    SSLContext sslContext = SSLContexts.createDefault();
    SSLConnectionSocketFactory sslConnectionSocketFactory =
        new SSLConnectionSocketFactory(
            sslContext,
            new String[] {"TLSv1.2"},
            null,
            SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    Registry<ConnectionSocketFactory> registry =
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register("https", sslConnectionSocketFactory)
            .build();

    PoolingHttpClientConnectionManager connectionManager =
        new PoolingHttpClientConnectionManager(registry);
    connectionManager.setMaxTotal(MercadoPagoConfig.getMaxConnections());
    connectionManager.setDefaultMaxPerRoute(MercadoPagoConfig.getMaxConnections());
    connectionManager.setValidateAfterInactivity(VALIDATE_INACTIVITY_INTERVAL_MS);

    HttpClientBuilder httpClientBuilder =
        HttpClients.custom()
            .setConnectionManager(connectionManager)
            .setKeepAliveStrategy(new KeepAliveStrategy())
            .disableCookieManagement()
            .disableRedirectHandling();

    if (Objects.nonNull(MercadoPagoConfig.getProxy())) {
      httpClientBuilder.setProxy(MercadoPagoConfig.getProxy());
    }

    if (Objects.nonNull(MercadoPagoConfig.getRetryHandler())) {
      httpClientBuilder.setRetryHandler(MercadoPagoConfig.getRetryHandler());
    } else {
      DefaultHttpRequestRetryHandler retryHandler =
          new DefaultHttpRequestRetryHandler(DEFAULT_RETRIES, false);
      httpClientBuilder.setRetryHandler(retryHandler);
    }

    return httpClientBuilder.build();
  }

  @Override
  public MPResponse send(MPRequest mpRequest) throws MPException, MPApiException {
    return sendWithRetry(mpRequest, null);
  }

  /**
   * Sends the request with an optional retry policy from request options.
   *
   * <p>When {@code requestOptions} is {@code null} or has no retry configuration, the request is
   * executed exactly once — identical behavior to the original {@link #send(MPRequest)}.
   *
   * @param mpRequest      the request to execute
   * @param requestOptions optional per-request overrides including retry configuration
   * @return the successful MPResponse
   * @throws MPException    on transport-level failures
   * @throws MPApiException on API error after retries are exhausted
   */
  public MPResponse sendWithRetry(MPRequest mpRequest, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    int maxRetries = resolveMaxRetries(requestOptions);
    List<Integer> retryOn = resolveRetryOn(requestOptions);
    long initialDelayMs = resolveInitialDelayMs(requestOptions);
    long maxDelayMs = resolveMaxDelayMs(requestOptions);
    boolean useJitter = resolveJitter(requestOptions);

    MPApiException lastException = null;

    for (int attempt = 0; attempt <= maxRetries; attempt++) {
      try {
        HttpRequestBase completeRequest = createHttpRequest(mpRequest);
        HttpClientContext context = HttpClientContext.create();

        HttpResponse response = executeHttpRequest(mpRequest, completeRequest, context);

        String responseBody = "";
        if (Objects.nonNull(response.getEntity())) {
          responseBody = EntityUtils.toString(response.getEntity(), UTF_8);
        }

        Map<String, List<String>> headers = getHeaders(response);
        int statusCode = response.getStatusLine().getStatusCode();
        MPResponse mpResponse = new MPResponse(statusCode, headers, responseBody);

        if (statusCode > 299) {
          MPApiException ex = buildApiException(statusCode, mpResponse);
          if (attempt < maxRetries && retryOn.contains(statusCode)) {
            lastException = ex;
            invokeOnRetry(requestOptions, attempt + 1, ex);
            Long retryAfterMs = statusCode == TOO_MANY_REQUESTS
              ? extractRetryAfterMs(mpResponse) : null;
            sleepQuietly(computeDelay(attempt, initialDelayMs, maxDelayMs, useJitter, retryAfterMs));
            continue;
          }
          throw ex;
        }

        logSuccessResponse(response, responseBody);
        return mpResponse;

      } catch (MPMalformedRequestException ex) {
        throw ex;
      } catch (MPApiException ex) {
        if (attempt < maxRetries && retryOn.contains(ex.getStatusCode())) {
          lastException = ex;
          invokeOnRetry(requestOptions, attempt + 1, ex);
          sleepQuietly(computeDelay(attempt, initialDelayMs, maxDelayMs, useJitter, null));
          continue;
        }
        throw ex;
      } catch (Exception ex) {
        throw new MPException(ex);
      }
    }

    if (lastException != null) {
      throw lastException;
    }
    throw new MPException(new IllegalStateException("Retry loop exited without result"));
  }

  // --- Exception factory (TASK-001) ---

  private static MPApiException buildApiException(int status, MPResponse response) {
    MPResponse sanitized = sanitizeResponse(response);
    switch (status) {
      case 400: return new MPBadRequestException(API_ERROR_MESSAGE, sanitized);
      case 401: return new MPAuthenticationException(API_ERROR_MESSAGE, sanitized);
      case 402: return new MPPaymentException(API_ERROR_MESSAGE, sanitized);
      case 403: return new MPForbiddenException(API_ERROR_MESSAGE, sanitized);
      case 404: return new MPNotFoundException(API_ERROR_MESSAGE, sanitized);
      case 409: return new MPIdempotencyException(API_ERROR_MESSAGE, sanitized);
      case 422: return new MPValidationException(API_ERROR_MESSAGE, sanitized);
      case 423: return new MPResourceLockedException(API_ERROR_MESSAGE, sanitized);
      case 424: return new MPDependencyException(API_ERROR_MESSAGE, sanitized);
      case 429: return new MPRateLimitException(API_ERROR_MESSAGE, sanitized,
          extractRetryAfterSeconds(response));
      default:
        return status >= 500
            ? new MPServerException(API_ERROR_MESSAGE, sanitized)
            : new MPApiException(API_ERROR_MESSAGE, sanitized);
    }
  }

  /** Replaces the Authorization header value with "***" to prevent credential leakage (CWE-209). */
  private static MPResponse sanitizeResponse(MPResponse response) {
    if (response == null || response.getHeaders() == null) {
      return response;
    }
    Map<String, List<String>> sanitized = new HashMap<>(response.getHeaders());
    for (String key : new ArrayList<>(sanitized.keySet())) {
      if (AUTHORIZATION_HEADER.equalsIgnoreCase(key)) {
        sanitized.put(key, Collections.singletonList(REDACTED));
        break;
      }
    }
    return new MPResponse(response.getStatusCode(), sanitized, response.getContent());
  }

  /** Returns the Retry-After header value in SECONDS (for storage in MPRateLimitException). */
  private static Long extractRetryAfterSeconds(MPResponse response) {
    if (response == null || response.getHeaders() == null) {
      return null;
    }
    for (Map.Entry<String, List<String>> entry : response.getHeaders().entrySet()) {
      if (RETRY_AFTER_HEADER.equalsIgnoreCase(entry.getKey())
          && entry.getValue() != null && !entry.getValue().isEmpty()) {
        try {
          return Long.parseLong(entry.getValue().get(0).trim());
        } catch (NumberFormatException e) {
          LOGGER.fine("Could not parse Retry-After header: " + entry.getValue().get(0));
        }
      }
    }
    return null;
  }

  /** Returns the Retry-After header value in MILLISECONDS (for use in Thread.sleep). */
  private static Long extractRetryAfterMs(MPResponse response) {
    Long seconds = extractRetryAfterSeconds(response);
    return seconds != null ? seconds * 1000L : null;
  }

  // --- Retry helpers (TASK-003) ---

  private static int resolveMaxRetries(MPRequestOptions opts) {
    if (opts != null && opts.getMaxRetries() != null) {
      return opts.getMaxRetries();
    }
    return 0;
  }

  private static List<Integer> resolveRetryOn(MPRequestOptions opts) {
    if (opts != null && opts.getRetryOn() != null) {
      return opts.getRetryOn();
    }
    return MPRequestOptions.DEFAULT_RETRY_ON;
  }

  private static long resolveInitialDelayMs(MPRequestOptions opts) {
    if (opts != null && opts.getInitialDelayMs() != null) {
      return opts.getInitialDelayMs();
    }
    return 1000L;
  }

  private static long resolveMaxDelayMs(MPRequestOptions opts) {
    if (opts != null && opts.getMaxDelayMs() != null) {
      return opts.getMaxDelayMs();
    }
    return MPRequestOptions.DEFAULT_MAX_DELAY_MS;
  }

  private static boolean resolveJitter(MPRequestOptions opts) {
    return opts != null && Boolean.TRUE.equals(opts.getJitter());
  }

  private static long computeDelay(int attempt, long initialDelayMs, long maxDelayMs,
      boolean useJitter, Long retryAfterMs) {
    if (retryAfterMs != null) {
      return Math.min(retryAfterMs, maxDelayMs);
    }
    long exponential = (long) (initialDelayMs * Math.pow(2, attempt));
    long capped = Math.min(exponential, maxDelayMs);
    if (useJitter && capped > 0) {
      capped = (long) (SECURE_RANDOM.nextDouble() * capped);
    }
    return capped;
  }

  private static void sleepQuietly(long delayMs) {
    if (delayMs > 0) {
      try {
        Thread.sleep(delayMs);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private static void invokeOnRetry(MPRequestOptions opts, int attemptNumber, Exception ex) {
    if (opts != null && opts.getOnRetry() != null) {
      try {
        opts.getOnRetry().accept(attemptNumber, ex);
      } catch (Exception callbackEx) {
        LOGGER.fine("onRetry callback threw: " + callbackEx.getMessage());
      }
    }
  }

  private void logSuccessResponse(HttpResponse response, String responseBody) {
    StringBuilder responseHeaders =
        new StringBuilder(String.format("Response headers:%s", System.lineSeparator()));
    for (Header header : response.getAllHeaders()) {
      responseHeaders.append(
          String.format(
              HEADER_LOG_FORMAT, header.getName(), header.getValue(), System.lineSeparator()));
    }
    LOGGER.fine(responseHeaders.toString());
    LOGGER.fine(
        String.format("Response status code: %s", response.getStatusLine().getStatusCode()));
    LOGGER.fine(String.format("Response body: %s", responseBody));
  }

  private HttpRequestBase createHttpRequest(MPRequest mpRequest)
      throws MPMalformedRequestException {
    HttpEntity entity = normalizePayload(mpRequest.getPayload());
    HttpRequestBase request = getRequestBase(mpRequest.getMethod(), mpRequest.getUri(), entity);
    Map<String, String> headers = new HashMap<>(mpRequest.getHeaders());

    for (Map.Entry<String, String> header : headers.entrySet()) {
      request.addHeader(new BasicHeader(header.getKey(), header.getValue()));
    }

    int socketTimeout =
        mpRequest.getSocketTimeout() != 0
            ? mpRequest.getSocketTimeout()
            : MercadoPagoConfig.getSocketTimeout();
    int connectionTimeout =
        mpRequest.getConnectionTimeout() != 0
            ? mpRequest.getConnectionTimeout()
            : MercadoPagoConfig.getConnectionTimeout();
    int connectionRequestTimeout =
        mpRequest.getConnectionRequestTimeout() != 0
            ? mpRequest.getConnectionRequestTimeout()
            : MercadoPagoConfig.getConnectionRequestTimeout();
    RequestConfig.Builder requestConfigBuilder =
        RequestConfig.custom()
            .setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectionTimeout)
            .setConnectionRequestTimeout(connectionRequestTimeout);

    request.setConfig(requestConfigBuilder.build());
    return request;
  }

  private HttpResponse executeHttpRequest(
      MPRequest mpRequest, HttpRequestBase completeRequest, HttpClientContext context) {
    try {
      if (Objects.nonNull(mpRequest.getPayload())) {
        LOGGER.fine(String.format("Request body: %s", mpRequest.getPayload().toString()));
      }

      StringBuilder headersMessage =
          new StringBuilder(String.format("Request Headers:%s", System.lineSeparator()));
      for (Map.Entry<String, String> entry : mpRequest.getHeaders().entrySet()) {
        headersMessage.append(
            String.format(
                HEADER_LOG_FORMAT, entry.getKey(), entry.getValue(), System.lineSeparator()));
      }
      LOGGER.fine(headersMessage.toString());

      return httpClient.execute(completeRequest, context);
    } catch (ClientProtocolException e) {
      LOGGER.fine(String.format("ClientProtocolException: %s", e.getMessage()));
      return new BasicHttpResponse(
          new BasicStatusLine(completeRequest.getProtocolVersion(), BAD_REQUEST, null));
    } catch (SSLPeerUnverifiedException e) {
      LOGGER.fine(String.format("SSLException: %s", e.getMessage()));
      return new BasicHttpResponse(
          new BasicStatusLine(completeRequest.getProtocolVersion(), FORBIDDEN, null));
    } catch (IOException e) {
      LOGGER.fine(String.format("IOException: %s", e.getMessage()));
      return new BasicHttpResponse(
          new BasicStatusLine(completeRequest.getProtocolVersion(), INTERNAL_SERVER_ERROR, null));
    }
  }

  private Map<String, List<String>> getHeaders(HttpResponse response) {
    Map<String, List<String>> headers = new HashMap<>();
    for (Header header : response.getAllHeaders()) {
      if (!headers.containsKey(header.getName())) {
        headers.put(header.getName(), new ArrayList<>());
      }
      headers.get(header.getName()).add(header.getValue());
    }
    return headers;
  }

  private HttpRequestBase getRequestBase(HttpMethod method, String uri, HttpEntity entity)
      throws MPMalformedRequestException {
    if (Objects.isNull(method)) {
      throw new MPMalformedRequestException(
          "HttpMethod must be either \"GET\", \"POST\", \"PUT\", \"PATCH\" or \"DELETE\".");
    }

    if (StringUtils.isEmpty(uri)) {
      throw new MPMalformedRequestException("Uri can not be an empty String.");
    }

    if ((method.equals(HttpMethod.GET) || method.equals(HttpMethod.DELETE))
        && Objects.nonNull(entity)) {
      throw new MPMalformedRequestException(PAYLOAD_NOT_SUPPORTED_MESSAGE);
    }

    return getHttpRequestBase(method, uri, entity);
  }

  private HttpRequestBase getHttpRequestBase(HttpMethod method, String uri, HttpEntity entity) {
    if (method.equals(HttpMethod.GET)) {
      return new HttpGet(uri);
    }

    if (method.equals(HttpMethod.POST)) {
      HttpPost post = new HttpPost(uri);
      post.setEntity(entity);
      return post;
    }

    if (method.equals(HttpMethod.PUT)) {
      HttpPut put = new HttpPut(uri);
      put.setEntity(entity);
      return put;
    }

    if (method.equals(HttpMethod.PATCH)) {
      HttpPatch patch = new HttpPatch(uri);
      patch.setEntity(entity);
      return patch;
    }

    if (method.equals(HttpMethod.DELETE)) {
      return new HttpDelete(uri);
    }

    return null;
  }

  private HttpEntity normalizePayload(JsonObject payload) throws MPMalformedRequestException {
    if (payload != null && payload.size() != 0) {
      try {
        return new StringEntity(payload.toString(), UTF_8);
      } catch (Exception ex) {
        throw new MPMalformedRequestException(ex);
      }
    }
    return null;
  }
}
