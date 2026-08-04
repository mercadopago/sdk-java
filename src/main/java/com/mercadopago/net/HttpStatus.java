package com.mercadopago.net;

/**
 * Defines commonly used HTTP status code constants for the MercadoPago SDK.
 *
 * <p>These constants are used internally by {@link MPDefaultHttpClient} to evaluate API responses
 * and to map low-level HTTP exceptions (such as protocol or SSL errors) to appropriate status
 * codes.
 *
 * @see MPDefaultHttpClient
 * @see MPResponse
 */
public class HttpStatus {

  /**
   * HTTP 200 OK. Indicates that the request has succeeded and the response body contains the
   * requested resource.
   */
  public static final int OK = 200;

  /**
   * HTTP 201 Created. Indicates that a new resource has been successfully created as a result of
   * the request (typically returned for POST operations).
   */
  public static final int CREATED = 201;

  /**
   * HTTP 204 No Content. Indicates that the request has succeeded but the response body is empty
   * (typically returned for DELETE or update operations that produce no content).
   */
  public static final int NO_CONTENT = 204;

  /**
   * HTTP 400 Bad Request. Indicates that the server could not understand the request due to
   * invalid syntax or malformed parameters. Also used internally when a
   * {@link org.apache.http.client.ClientProtocolException} occurs.
   */
  public static final int BAD_REQUEST = 400;

  /** HTTP 401 Unauthorized. Indicates that the request lacks valid authentication credentials. */
  public static final int UNAUTHORIZED = 401;

  /** HTTP 402 Payment Required. Indicates a payment is required to process the request. */
  public static final int PAYMENT_REQUIRED = 402;

  /**
   * HTTP 403 Forbidden. Indicates that the server understood the request but refuses to
   * authorize it. Also used internally when an
   * {@link javax.net.ssl.SSLPeerUnverifiedException} occurs during TLS verification.
   */
  public static final int FORBIDDEN = 403;

  /** HTTP 404 Not Found. Indicates that the requested resource does not exist. */
  public static final int NOT_FOUND = 404;

  /** HTTP 409 Conflict. Covers idempotency-key conflicts and state-machine conflicts. */
  public static final int CONFLICT = 409;

  /** HTTP 422 Unprocessable Entity. Business-rule violation. */
  public static final int UNPROCESSABLE_ENTITY = 422;

  /** HTTP 423 Locked. Idempotency key temporarily locked (retryable). */
  public static final int LOCKED = 423;

  /** HTTP 424 Failed Dependency. Internal dependency failure (retryable). */
  public static final int FAILED_DEPENDENCY = 424;

  /** HTTP 429 Too Many Requests. Check the {@code Retry-After} response header. */
  public static final int TOO_MANY_REQUESTS = 429;

  /**
   * HTTP 500 Internal Server Error. Indicates an unexpected condition on the server side. Also
   * used internally when a generic {@link java.io.IOException} occurs during request execution.
   */
  public static final int INTERNAL_SERVER_ERROR = 500;
}
