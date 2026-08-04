package com.mercadopago.net;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for TASK-001 (exception factory) and TASK-003 (retry + timeout)
 * exercised through {@link MPDefaultHttpClient}.
 */
class MPDefaultHttpClientTypedExceptionTest extends BaseClientTest {

  private MPDefaultHttpClient client;

  @BeforeEach
  void setUp() {
    client = new MPDefaultHttpClient(HTTP_CLIENT_MOCK.getHttpClient());
  }

  private static MPRequest buildGetRequest() {
    Map<String, String> headers = new HashMap<>();
    return MPRequest.builder()
        .method(HttpMethod.GET)
        .uri("http://test.com/resource")
        .headers(headers)
        .build();
  }

  private static HttpResponse buildResponse(int statusCode, String body) {
    BasicHttpResponse response = new BasicHttpResponse(
        new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, "Reason"));
    if (body != null) {
      BasicHttpEntity entity = new BasicHttpEntity();
      entity.setContent(new ByteArrayInputStream(body.getBytes()));
      response.setEntity(entity);
    }
    return response;
  }

  private static HttpResponse buildResponseWithHeader(int statusCode, String headerName, String headerValue) {
    HttpResponse response = buildResponse(statusCode, "{}");
    response.setHeader(headerName, headerValue);
    return response;
  }

  // ------------------------------- Exception factory tests (TASK-001) -------------------------------

  @Test
  void send_400_throwsMPBadRequestException() throws IOException {
    doReturn(buildResponse(400, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPBadRequestException.class, ex);
    assertEquals(400, ex.getStatusCode());
  }

  @Test
  void send_401_throwsMPAuthenticationException() throws IOException {
    doReturn(buildResponse(401, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPAuthenticationException.class, ex);
    assertEquals(401, ex.getStatusCode());
  }

  @Test
  void send_402_throwsMPPaymentException() throws IOException {
    doReturn(buildResponse(402, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPPaymentException.class, ex);
    assertEquals(402, ex.getStatusCode());
  }

  @Test
  void send_403_throwsMPForbiddenException() throws IOException {
    doReturn(buildResponse(403, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPForbiddenException.class, ex);
    assertEquals(403, ex.getStatusCode());
  }

  @Test
  void send_404_throwsMPNotFoundException() throws IOException {
    doReturn(buildResponse(404, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPNotFoundException.class, ex);
    assertEquals(404, ex.getStatusCode());
  }

  @Test
  void send_409_throwsMPIdempotencyException() throws IOException {
    doReturn(buildResponse(409, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPIdempotencyException.class, ex);
    assertEquals(409, ex.getStatusCode());
  }

  @Test
  void send_422_throwsMPValidationException() throws IOException {
    doReturn(buildResponse(422, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPValidationException.class, ex);
    assertEquals(422, ex.getStatusCode());
  }

  @Test
  void send_423_throwsMPResourceLockedException() throws IOException {
    doReturn(buildResponse(423, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPResourceLockedException.class, ex);
    assertEquals(423, ex.getStatusCode());
  }

  @Test
  void send_424_throwsMPDependencyException() throws IOException {
    doReturn(buildResponse(424, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPDependencyException.class, ex);
    assertEquals(424, ex.getStatusCode());
  }

  @Test
  void send_429_throwsMPRateLimitException() throws IOException {
    doReturn(buildResponse(429, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPRateLimitException.class, ex);
    assertEquals(429, ex.getStatusCode());
  }

  @Test
  void send_429_withRetryAfterHeader_propagatesRetryAfter() throws IOException {
    HttpResponse response = buildResponseWithHeader(429, "Retry-After", "45");
    doReturn(response)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPRateLimitException.class, ex);
    MPRateLimitException rateLimitEx = (MPRateLimitException) ex;
    assertEquals(45L, rateLimitEx.getRetryAfter());
  }

  @Test
  void send_500_throwsMPServerException() throws IOException {
    doReturn(buildResponse(500, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPServerException.class, ex);
    assertEquals(500, ex.getStatusCode());
  }

  @Test
  void send_503_throwsMPServerException() throws IOException {
    doReturn(buildResponse(503, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertInstanceOf(MPServerException.class, ex);
    assertEquals(503, ex.getStatusCode());
  }

  @Test
  void send_unknownClientError_throwsBaseApiException() throws IOException {
    doReturn(buildResponse(418, "{}"))  // I'm a Teapot
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));
    assertEquals(MPApiException.class, ex.getClass()); // exact base type, not a subclass
    assertEquals(418, ex.getStatusCode());
  }

  // ------------------------------- CWE-209 Authorization header sanitization -------------------

  @Test
  void send_errorResponse_authorizationHeaderSanitized() throws IOException {
    HttpResponse response = buildResponse(401, "{}");
    response.setHeader("Authorization", "Bearer super-secret-token-abc123");
    doReturn(response)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPApiException ex = assertThrows(MPApiException.class, () -> client.send(buildGetRequest()));

    // Authorization value must be redacted in the stored response
    Map<String, java.util.List<String>> headers = ex.getApiResponse().getHeaders();
    boolean authPresent = headers.entrySet().stream()
        .anyMatch(e -> "Authorization".equalsIgnoreCase(e.getKey()));
    if (authPresent) {
      headers.entrySet().stream()
          .filter(e -> "Authorization".equalsIgnoreCase(e.getKey()))
          .flatMap(e -> e.getValue().stream())
          .forEach(v -> assertFalse(v.contains("super-secret-token-abc123"),
              "Authorization header value leaked in exception: " + v));
    }
    assertFalse(ex.toString().contains("super-secret-token-abc123"));
  }

  // ------------------------------- DEFAULT constants (TASK-003) -------------------------------

  @Test
  void defaultConstants_haveCorrectValues() {
    assertEquals(3, MPRequestOptions.DEFAULT_MAX_RETRIES);
    assertEquals(60_000L, MPRequestOptions.DEFAULT_TIMEOUT_MS);
    assertEquals(30_000L, MPRequestOptions.DEFAULT_MAX_DELAY_MS);
    assertTrue(MPRequestOptions.DEFAULT_RETRY_ON.contains(429));
    assertTrue(MPRequestOptions.DEFAULT_RETRY_ON.contains(500));
    assertTrue(MPRequestOptions.DEFAULT_RETRY_ON.contains(502));
    assertTrue(MPRequestOptions.DEFAULT_RETRY_ON.contains(503));
    assertTrue(MPRequestOptions.DEFAULT_RETRY_ON.contains(504));
    assertFalse(MPRequestOptions.DEFAULT_RETRY_ON.contains(400));
    assertFalse(MPRequestOptions.DEFAULT_RETRY_ON.contains(404));
  }

  // ------------------------------- Retry logic tests (TASK-003) -------------------------------

  @Test
  void sendWithRetry_success_onFirstAttempt() throws IOException, MPException, MPApiException {
    doReturn(buildResponse(200, "{\"success\":true}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPRequestOptions opts = MPRequestOptions.builder()
        .maxRetries(3)
        .build();

    MPResponse response = client.sendWithRetry(buildGetRequest(), opts);
    assertEquals(200, response.getStatusCode());
  }

  @Test
  void sendWithRetry_500_exhaustsRetries_throwsServerException() throws IOException {
    doReturn(buildResponse(500, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPRequestOptions opts = MPRequestOptions.builder()
        .maxRetries(2)
        .initialDelayMs(0L)
        .build();

    MPApiException ex = assertThrows(MPApiException.class,
        () -> client.sendWithRetry(buildGetRequest(), opts));
    assertInstanceOf(MPServerException.class, ex);
    assertEquals(500, ex.getStatusCode());
  }

  @Test
  void sendWithRetry_4xxNotRetried() throws IOException {
    // 404 should not be retried even with maxRetries > 0
    doReturn(buildResponse(404, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    AtomicInteger callCount = new AtomicInteger(0);
    MPRequestOptions opts = MPRequestOptions.builder()
        .maxRetries(3)
        .retryOn(Collections.singletonList(500)) // only retry 500, not 404
        .onRetry((attempt, e) -> callCount.incrementAndGet())
        .initialDelayMs(0L)
        .build();

    assertThrows(MPNotFoundException.class,
        () -> client.sendWithRetry(buildGetRequest(), opts));
    // onRetry should NOT have been called since 404 is not in retryOn
    assertEquals(0, callCount.get());
  }

  @Test
  void sendWithRetry_onRetryCallbackInvoked() throws IOException {
    doReturn(buildResponse(503, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    AtomicInteger retryCount = new AtomicInteger(0);
    MPRequestOptions opts = MPRequestOptions.builder()
        .maxRetries(2)
        .retryOn(Collections.singletonList(503))
        .onRetry((attempt, e) -> retryCount.incrementAndGet())
        .initialDelayMs(0L)
        .build();

    assertThrows(MPServerException.class,
        () -> client.sendWithRetry(buildGetRequest(), opts));
    // 2 retries means onRetry called twice
    assertEquals(2, retryCount.get());
  }

  @Test
  void sendWithRetry_noOptions_noRetries() throws IOException {
    doReturn(buildResponse(500, "{}"))
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    // No retry options — should throw immediately without retrying
    MPApiException ex = assertThrows(MPApiException.class,
        () -> client.sendWithRetry(buildGetRequest(), null));
    assertInstanceOf(MPServerException.class, ex);
  }

  // ------------------------------- Input validation tests (TASK-003) -----------------------

  @Test
  void requestOptions_negativeMaxRetries_throwsIllegalArgument() {
    MPRequestOptions opts = MPRequestOptions.createDefault();
    assertThrows(IllegalArgumentException.class, () -> opts.setMaxRetries(-1));
  }

  @Test
  void requestOptions_negativeInitialDelayMs_throwsIllegalArgument() {
    MPRequestOptions opts = MPRequestOptions.createDefault();
    assertThrows(IllegalArgumentException.class, () -> opts.setInitialDelayMs(-100L));
  }

  @Test
  void requestOptions_negativeMaxDelayMs_throwsIllegalArgument() {
    MPRequestOptions opts = MPRequestOptions.createDefault();
    assertThrows(IllegalArgumentException.class, () -> opts.setMaxDelayMs(-1L));
  }

  @Test
  void requestOptions_invalidStatusCodeInRetryOn_throwsIllegalArgument() {
    MPRequestOptions opts = MPRequestOptions.createDefault();
    assertThrows(IllegalArgumentException.class,
        () -> opts.setRetryOn(Collections.singletonList(99)));
  }

  @Test
  void requestOptions_validValues_noException() {
    MPRequestOptions opts = MPRequestOptions.createDefault();
    assertDoesNotThrow(() -> opts.setMaxRetries(0));
    assertDoesNotThrow(() -> opts.setInitialDelayMs(0L));
    assertDoesNotThrow(() -> opts.setMaxDelayMs(0L));
    assertDoesNotThrow(() -> opts.setRetryOn(MPRequestOptions.DEFAULT_RETRY_ON));
  }
}
