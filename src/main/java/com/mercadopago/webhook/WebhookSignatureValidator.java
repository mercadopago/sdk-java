package com.mercadopago.webhook;

import com.mercadopago.exceptions.MPInvalidWebhookSignatureException;
import com.mercadopago.exceptions.SignatureFailureReason;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Verifies the authenticity of an incoming MercadoPago webhook notification by
 * recomputing the HMAC-SHA256 signature locally and comparing it against the value
 * carried in the {@code x-signature} header.
 *
 * <p>This is a stateless, CPU-only utility. It performs no outbound HTTP calls and
 * does not depend on {@link com.mercadopago.MercadoPagoConfig}; the integrator
 * passes the secret signature explicitly on every call. The comparison is performed
 * in constant time (via {@link MessageDigest#isEqual(byte[], byte[])}) to mitigate
 * timing attacks.
 *
 * <p>On failure, the validator throws {@link MPInvalidWebhookSignatureException}
 * with a specific {@link SignatureFailureReason}. The integrator should respond with
 * HTTP 401 to MercadoPago in all failure cases, log the request id for correlation,
 * and not expose the failure reason in the HTTP response body.
 *
 * <p><b>QR Code notifications are not signed by MercadoPago</b> — do not call this
 * validator for those events; they will always fail signature verification.
 */
public final class WebhookSignatureValidator {

  private static final List<String> DEFAULT_SUPPORTED_VERSIONS = List.of("v1");
  private static final Pattern VERSION_KEY_REGEX = Pattern.compile("^v\\d+$");
  private static final Pattern DIGITS_REGEX = Pattern.compile("^\\d+$");

  private WebhookSignatureValidator() {
    // Static-only class — prevent instantiation.
  }

  /**
   * Validates the signature of a MercadoPago webhook notification.
   *
   * @param xSignature raw value of the {@code x-signature} request header
   * @param xRequestId value of the {@code x-request-id} request header; may be {@code null}
   *                   or blank, in which case the {@code request-id:} pair is omitted from the
   *                   manifest before computing the HMAC
   * @param dataId     value of the {@code data.id} query parameter; may be {@code null} or
   *                   blank, in which case the {@code id:} pair is omitted. When present,
   *                   it is lowercased before being included in the manifest
   * @param secret     secret signature configured for the application in Tus Integraciones,
   *                   used as the HMAC key
   * @throws MPInvalidWebhookSignatureException when the signature is missing, malformed, or
   *                                          does not match the expected HMAC
   * @throws NullPointerException             when {@code secret} is {@code null}
   */
  public static void validate(String xSignature, String xRequestId, String dataId, String secret)
      throws MPInvalidWebhookSignatureException {
    validate(xSignature, xRequestId, dataId, secret, null, null, null);
  }

  /**
   * Validates the signature with an optional replay-window tolerance. See
   * {@link #validate(String, String, String, String, Duration, List, Supplier)} for the full
   * documentation.
   */
  public static void validate(
      String xSignature, String xRequestId, String dataId, String secret, Duration tolerance)
      throws MPInvalidWebhookSignatureException {
    validate(xSignature, xRequestId, dataId, secret, tolerance, null, null);
  }

  /**
   * Full-featured overload that exposes all options.
   *
   * @param xSignature        raw value of the {@code x-signature} request header
   * @param xRequestId        value of the {@code x-request-id} request header; may be {@code null}
   * @param dataId            value of the {@code data.id} query parameter; may be {@code null}
   * @param secret            secret signature configured for the application
   * @param tolerance         optional maximum allowed drift between the timestamp in the header
   *                          and the current clock; {@code null} disables the check
   * @param supportedVersions optional ordered list of signature versions to accept; defaults
   *                          to {@code ["v1"]}. The first version found in the header is used
   * @param clock             optional clock supplier used for the tolerance check; intended for
   *                          tests. Defaults to {@link Instant#now()}
   * @throws MPInvalidWebhookSignatureException when the signature is missing, malformed, or
   *                                          does not match the expected HMAC
   * @throws NullPointerException             when {@code secret} is {@code null}
   */
  public static void validate(
      String xSignature,
      String xRequestId,
      String dataId,
      String secret,
      Duration tolerance,
      List<String> supportedVersions,
      Supplier<Instant> clock)
      throws MPInvalidWebhookSignatureException {
    Objects.requireNonNull(secret, "secret must not be null");

    String signature = normalize(xSignature);
    String requestId = normalize(xRequestId);
    String normalizedDataId = normalize(dataId);
    List<String> versions =
        (supportedVersions == null || supportedVersions.isEmpty())
            ? DEFAULT_SUPPORTED_VERSIONS
            : supportedVersions;
    Supplier<Instant> nowSupplier = clock != null ? clock : Instant::now;

    if (signature == null) {
      throw new MPInvalidWebhookSignatureException(
          SignatureFailureReason.MISSING_SIGNATURE_HEADER, requestId, null);
    }

    ParsedSignature parsed = parseSignatureHeader(signature);

    if (parsed.timestamp == null && parsed.hashes.isEmpty()) {
      throw new MPInvalidWebhookSignatureException(
          SignatureFailureReason.MALFORMED_SIGNATURE_HEADER, requestId, null);
    }

    if (parsed.timestamp == null) {
      throw new MPInvalidWebhookSignatureException(
          SignatureFailureReason.MISSING_TIMESTAMP, requestId, null);
    }

    if (!DIGITS_REGEX.matcher(parsed.timestamp).matches()) {
      throw new MPInvalidWebhookSignatureException(
          SignatureFailureReason.MALFORMED_SIGNATURE_HEADER, requestId, parsed.timestamp);
    }

    String receivedHash = null;
    for (String version : versions) {
      String hash = parsed.hashes.get(version);
      if (hash != null) {
        receivedHash = hash;
        break;
      }
    }

    if (receivedHash == null) {
      throw new MPInvalidWebhookSignatureException(
          SignatureFailureReason.MISSING_HASH, requestId, parsed.timestamp);
    }

    String manifest = buildManifest(normalizedDataId, requestId, parsed.timestamp);
    String computed = computeHmacHex(secret, manifest);

    if (!constantTimeEquals(computed, receivedHash)) {
      throw new MPInvalidWebhookSignatureException(
          SignatureFailureReason.SIGNATURE_MISMATCH, requestId, parsed.timestamp);
    }

    if (tolerance != null) {
      long tsMs = Long.parseLong(parsed.timestamp);
      long nowMs = nowSupplier.get().toEpochMilli();
      long drift = Math.abs(nowMs - tsMs);
      if (drift > tolerance.toMillis()) {
        throw new MPInvalidWebhookSignatureException(
            SignatureFailureReason.TIMESTAMP_OUT_OF_TOLERANCE, requestId, parsed.timestamp);
      }
    }
  }

  private static String normalize(String value) {
    if (value == null) {
      return null;
    }
    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }

  private static ParsedSignature parseSignatureHeader(String header) {
    Map<String, String> hashes = new HashMap<>();
    String ts = null;
    for (String part : header.split(",")) {
      int eq = part.indexOf('=');
      if (eq <= 0 || eq == part.length() - 1) {
        continue;
      }
      String key = part.substring(0, eq).trim().toLowerCase();
      String value = part.substring(eq + 1).trim();
      if (key.isEmpty() || value.isEmpty()) {
        continue;
      }
      if ("ts".equals(key)) {
        ts = value;
      } else if (VERSION_KEY_REGEX.matcher(key).matches()) {
        hashes.put(key, value);
      }
    }
    return new ParsedSignature(ts, hashes);
  }

  private static String buildManifest(String dataId, String requestId, String timestamp) {
    StringBuilder sb = new StringBuilder();
    if (dataId != null) {
      sb.append("id:").append(dataId.toLowerCase()).append(';');
    }
    if (requestId != null) {
      sb.append("request-id:").append(requestId).append(';');
    }
    sb.append("ts:").append(timestamp).append(';');
    return sb.toString();
  }

  private static String computeHmacHex(String secret, String message) {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      byte[] hashBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(hashBytes.length * 2);
      for (byte b : hashBytes) {
        sb.append(String.format("%02x", b & 0xff));
      }
      return sb.toString();
    } catch (Exception e) {
      // HmacSHA256 is mandated by every JRE; failures here indicate a broken runtime.
      throw new IllegalStateException("HMAC-SHA256 not available in this JVM", e);
    }
  }

  private static boolean constantTimeEquals(String a, String b) {
    if (a == null || b == null || a.length() != b.length()) {
      return false;
    }
    return MessageDigest.isEqual(
        a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
  }

  /** Internal carrier for the parsed {@code x-signature} components. */
  private static final class ParsedSignature {
    final String timestamp;
    final Map<String, String> hashes;

    ParsedSignature(String timestamp, Map<String, String> hashes) {
      this.timestamp = timestamp;
      this.hashes = hashes;
    }
  }
}
