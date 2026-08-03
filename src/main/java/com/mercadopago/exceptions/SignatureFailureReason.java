package com.mercadopago.exceptions;

/**
 * Enumerates the reasons why
 * {@link com.mercadopago.webhook.WebhookSignatureValidator} may reject a
 * MercadoPago webhook notification.
 *
 * <p>Integrators are encouraged to log this value alongside the {@code x-request-id}
 * for correlation against the MercadoPago notifications dashboard.
 */
public enum SignatureFailureReason {

  /** The {@code x-signature} header was missing, empty, or whitespace. */
  MISSING_SIGNATURE_HEADER,

  /** The header did not match the expected {@code ts=...,vN=...} format. */
  MALFORMED_SIGNATURE_HEADER,

  /** The header parsed correctly but no {@code ts=} component was present. */
  MISSING_TIMESTAMP,

  /**
   * No hash was found in the header for any of the supported versions. Typically
   * indicates MercadoPago has migrated to a new signature version and the SDK
   * needs to be upgraded.
   */
  MISSING_HASH,

  /**
   * The computed HMAC did not match the value in the header. Most often caused by
   * an incorrect secret signature or a forged request.
   */
  SIGNATURE_MISMATCH,

  /**
   * The header timestamp fell outside the configured tolerance window against the
   * current clock. May indicate clock drift on the integrator's server or a replay
   * attack.
   */
  TIMESTAMP_OUT_OF_TOLERANCE,
}
