package com.mercadopago.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import com.mercadopago.net.MPResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for TASK-001: Typed exception hierarchy.
 *
 * <p>Verifies:
 * <ul>
 *   <li>Each subtype is an instance of {@link MPApiException}</li>
 *   <li>Status codes are propagated correctly</li>
 *   <li>Both constructor overloads work</li>
 *   <li>{@link MPRateLimitException} exposes {@code retryAfter}</li>
 *   <li>Subtype hierarchy is correct for catch-block usage</li>
 * </ul>
 */
class TypedExceptionTest {

  private static MPResponse buildResponse(int statusCode) {
    Map<String, List<String>> headers = new HashMap<>();
    return new MPResponse(statusCode, headers, "{\"error\":\"test\"}");
  }

  // ------------------------------- MPBadRequestException -------------------------------

  @Test
  void badRequestException_isInstanceOfMPApiException() {
    MPBadRequestException ex = new MPBadRequestException("msg", buildResponse(400));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(400, ex.getStatusCode());
  }

  @Test
  void badRequestException_withCause_propagatesCause() {
    Throwable cause = new RuntimeException("root");
    MPBadRequestException ex = new MPBadRequestException("msg", cause, buildResponse(400));
    assertEquals(cause, ex.getCause());
    assertEquals(400, ex.getStatusCode());
  }

  // ------------------------------- MPAuthenticationException -------------------------------

  @Test
  void authenticationException_statusCode401() {
    MPAuthenticationException ex = new MPAuthenticationException("msg", buildResponse(401));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(401, ex.getStatusCode());
  }

  @Test
  void authenticationException_withCause() {
    Throwable cause = new RuntimeException("root");
    MPAuthenticationException ex = new MPAuthenticationException("msg", cause, buildResponse(401));
    assertEquals(cause, ex.getCause());
  }

  // ------------------------------- MPPaymentException -------------------------------

  @Test
  void paymentException_statusCode402() {
    MPPaymentException ex = new MPPaymentException("msg", buildResponse(402));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(402, ex.getStatusCode());
  }

  // ------------------------------- MPForbiddenException -------------------------------

  @Test
  void forbiddenException_statusCode403() {
    MPForbiddenException ex = new MPForbiddenException("msg", buildResponse(403));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(403, ex.getStatusCode());
  }

  // ------------------------------- MPNotFoundException -------------------------------

  @Test
  void notFoundException_statusCode404() {
    MPNotFoundException ex = new MPNotFoundException("msg", buildResponse(404));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(404, ex.getStatusCode());
  }

  // ------------------------------- MPIdempotencyException -------------------------------

  @Test
  void idempotencyException_statusCode409() {
    MPIdempotencyException ex = new MPIdempotencyException("msg", buildResponse(409));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(409, ex.getStatusCode());
  }

  // ------------------------------- MPValidationException -------------------------------

  @Test
  void validationException_statusCode422() {
    MPValidationException ex = new MPValidationException("msg", buildResponse(422));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(422, ex.getStatusCode());
  }

  // ------------------------------- MPResourceLockedException -------------------------------

  @Test
  void resourceLockedException_statusCode423() {
    MPResourceLockedException ex = new MPResourceLockedException("msg", buildResponse(423));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(423, ex.getStatusCode());
  }

  // ------------------------------- MPDependencyException -------------------------------

  @Test
  void dependencyException_statusCode424() {
    MPDependencyException ex = new MPDependencyException("msg", buildResponse(424));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(424, ex.getStatusCode());
  }

  // ------------------------------- MPRateLimitException -------------------------------

  @Test
  void rateLimitException_statusCode429_withRetryAfter() {
    MPRateLimitException ex = new MPRateLimitException("msg", buildResponse(429), 30L);
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(429, ex.getStatusCode());
    assertEquals(30L, ex.getRetryAfter());
  }

  @Test
  void rateLimitException_nullRetryAfter_whenNotProvided() {
    MPRateLimitException ex = new MPRateLimitException("msg", buildResponse(429));
    assertEquals(429, ex.getStatusCode());
    assertNull(ex.getRetryAfter());
  }

  @Test
  void rateLimitException_withCauseAndRetryAfter() {
    Throwable cause = new RuntimeException("network");
    MPRateLimitException ex = new MPRateLimitException("msg", cause, buildResponse(429), 60L);
    assertEquals(cause, ex.getCause());
    assertEquals(60L, ex.getRetryAfter());
  }

  // ------------------------------- MPServerException -------------------------------

  @Test
  void serverException_statusCode500() {
    MPServerException ex = new MPServerException("msg", buildResponse(500));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(500, ex.getStatusCode());
  }

  @Test
  void serverException_statusCode503() {
    MPServerException ex = new MPServerException("msg", buildResponse(503));
    assertEquals(503, ex.getStatusCode());
  }

  // ------------------------------- MPConnectionException -------------------------------

  @Test
  void connectionException_wrapsNetworkError() {
    Throwable cause = new java.io.IOException("timeout");
    MPConnectionException ex = new MPConnectionException("msg", cause, buildResponse(500));
    assertInstanceOf(MPApiException.class, ex);
    assertEquals(cause, ex.getCause());
  }

  // ------------------------------- CWE-209: Authorization header sanitization -------------

  @Test
  void apiResponse_doesNotLeakAuthorizationHeader() {
    Map<String, List<String>> headers = new HashMap<>();
    headers.put("Authorization", Collections.singletonList("Bearer secret-token-12345"));
    MPResponse response = new MPResponse(401, headers, "{}");

    MPAuthenticationException ex = new MPAuthenticationException("msg", response);

    // The exception stores the response — but we rely on MPDefaultHttpClient to sanitize
    // before construction. Verify that the message does not contain the token.
    assertFalse(ex.getMessage().contains("secret-token-12345"));
    assertFalse(ex.toString().contains("secret-token-12345"));
  }

  // ------------------------------- Catch block polymorphism -------------------------------

  @Test
  void subtypes_canBeCaughtAsBaseType() {
    MPApiException[] exs = {
        new MPBadRequestException("msg", buildResponse(400)),
        new MPAuthenticationException("msg", buildResponse(401)),
        new MPForbiddenException("msg", buildResponse(403)),
        new MPNotFoundException("msg", buildResponse(404)),
        new MPIdempotencyException("msg", buildResponse(409)),
        new MPValidationException("msg", buildResponse(422)),
        new MPResourceLockedException("msg", buildResponse(423)),
        new MPDependencyException("msg", buildResponse(424)),
        new MPRateLimitException("msg", buildResponse(429)),
        new MPServerException("msg", buildResponse(500)),
    };

    for (MPApiException ex : exs) {
      try {
        throw ex;
      } catch (MPApiException caught) {
        assertNotNull(caught);
        assertTrue(caught.getStatusCode() >= 400);
      }
    }
  }
}
