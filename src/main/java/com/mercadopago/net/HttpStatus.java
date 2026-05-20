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

  /**
   * HTTP 403 Forbidden. Indicates that the server understood the request but refuses to
   * authorize it. Also used internally when an
   * {@link javax.net.ssl.SSLPeerUnverifiedException} occurs during TLS verification.
   */
  public static final int FORBIDDEN = 403;

  /**
   * HTTP 500 Internal Server Error. Indicates an unexpected condition on the server side. Also
   * used internally when a generic {@link java.io.IOException} occurs during request execution.
   */
  public static final int INTERNAL_SERVER_ERROR = 500;
}
