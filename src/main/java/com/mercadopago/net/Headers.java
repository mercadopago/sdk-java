package com.mercadopago.net;

/**
 * Defines standard HTTP header name constants used throughout the MercadoPago SDK.
 *
 * <p>This class contains both standard HTTP headers (such as {@code Authorization} and
 * {@code Content-Type}) and MercadoPago-specific custom headers (prefixed with {@code X-}) used
 * for request identification, idempotency, and partner tracking.
 *
 * <p>These constants are used by {@link MPRequest} and {@link MPDefaultHttpClient} when
 * constructing and sending HTTP requests to the MercadoPago API.
 *
 * @see MPRequest
 * @see MPDefaultHttpClient
 */
public class Headers {

  /**
   * Standard HTTP {@code Authorization} header, used to carry the OAuth access token
   * (Bearer token) for authenticating requests against the MercadoPago API.
   */
  public static final String AUTHORIZATION = "Authorization";

  /**
   * Standard HTTP {@code Content-Type} header, indicating the media type of the request body.
   * Typically set to {@code application/json} for MercadoPago API requests.
   */
  public static final String CONTENT_TYPE = "Content-Type";

  /**
   * Standard HTTP {@code Accept} header, indicating the media types the client is willing to
   * receive in the response. Typically set to {@code application/json}.
   */
  public static final String ACCEPT = "Accept";

  /**
   * Standard HTTP {@code User-Agent} header, identifying the client software originating the
   * request. The SDK sets this to a value containing the SDK version and Java runtime information.
   */
  public static final String USER_AGENT = "User-Agent";

  /**
   * MercadoPago custom header {@code X-Idempotency-Key}, used to ensure that POST requests are
   * idempotent. Sending the same idempotency key on repeated requests prevents duplicate resource
   * creation on the server side.
   *
   * @see MPRequest#createIdempotencyKey()
   */
  public static final String IDEMPOTENCY_KEY = "X-Idempotency-Key";

  /**
   * MercadoPago custom header {@code X-Product-Id}, used internally to identify the SDK product.
   * The value is set from {@link com.mercadopago.MercadoPagoConfig#PRODUCT_ID}.
   */
  public static final String PRODUCT_ID = "X-Product-Id";

  /**
   * MercadoPago custom header {@code X-Tracking-Id}, used internally for request tracking and
   * telemetry. Contains platform and SDK version metadata.
   *
   * @see com.mercadopago.MercadoPagoConfig#TRACKING_ID
   */
  public static final String TRACKING_ID = "X-Tracking-Id";

  /**
   * MercadoPago custom header {@code X-Corporation-Id}, used to identify the corporation
   * associated with the API request. Set via
   * {@link com.mercadopago.MercadoPagoConfig#setCorporationId(String)}.
   */
  public static final String CORPORATION_ID = "X-Corporation-Id";

  /**
   * MercadoPago custom header {@code X-Integrator-Id}, used to identify the integrator (partner)
   * making the API request. Set via
   * {@link com.mercadopago.MercadoPagoConfig#setIntegratorId(String)}.
   */
  public static final String INTEGRATOR_ID = "X-Integrator-Id";

  /**
   * MercadoPago custom header {@code X-Platform-Id}, used to identify the e-commerce platform
   * through which the API request originates. Set via
   * {@link com.mercadopago.MercadoPagoConfig#setPlatformId(String)}.
   */
  public static final String PLATFORM_ID = "X-Platform-Id";
}