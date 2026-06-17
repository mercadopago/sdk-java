package com.mercadopago.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.mercadopago.exceptions.MPInvalidWebhookSignatureException;
import com.mercadopago.exceptions.SignatureFailureReason;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link WebhookSignatureValidator}. Self-contained; no network access. */
class WebhookSignatureValidatorTest {

  private static final String SECRET = "your_secret_key_here";
  private static final String REQUEST_ID = "2066ca19-c6f1-498a-be75-1923005edd06";
  private static final String DATA_ID_RAW = "ORD01JQ4S4KY8HWQ6NA5PXB65B3D3";
  private static final String DATA_ID_LOWER = "ord01jq4s4ky8hwq6na5pxb65b3d3";
  private static final String TS = "1742505638683";

  private static String computeHash(String dataId, String requestId, String ts, String secret) {
    try {
      StringBuilder mb = new StringBuilder();
      if (dataId != null && !dataId.isEmpty()) {
        mb.append("id:").append(dataId).append(';');
      }
      if (requestId != null && !requestId.isEmpty()) {
        mb.append("request-id:").append(requestId).append(';');
      }
      mb.append("ts:").append(ts).append(';');

      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      byte[] hashBytes = mac.doFinal(mb.toString().getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(hashBytes.length * 2);
      for (byte b : hashBytes) {
        sb.append(String.format("%02x", b & 0xff));
      }
      return sb.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String buildHeader(String hash) {
    return "ts=" + TS + ",v1=" + hash;
  }

  private static String buildHeader(String hash, String ts) {
    return "ts=" + ts + ",v1=" + hash;
  }

  private static String validHash() {
    return computeHash(DATA_ID_LOWER, REQUEST_ID, TS, SECRET);
  }

  // case 1
  @Test
  void happyPathLowercase() {
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(buildHeader(validHash()), REQUEST_ID, DATA_ID_LOWER, SECRET));
  }

  // case 2
  @Test
  void uppercaseDataIdIsPreserved() {
    String upperHash = computeHash(DATA_ID_RAW, REQUEST_ID, TS, SECRET);
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(buildHeader(upperHash), REQUEST_ID, DATA_ID_RAW, SECRET));
  }

  // case 3
  @Test
  void malformedHeaderThrowsMalformed() {
    MPInvalidWebhookSignatureException ex = assertThrows(MPInvalidWebhookSignatureException.class,
        () -> WebhookSignatureValidator.validate("this-is-garbage", REQUEST_ID, DATA_ID_LOWER, SECRET));
    assertEquals(SignatureFailureReason.MALFORMED_SIGNATURE_HEADER, ex.getReason());
    assertEquals(REQUEST_ID, ex.getRequestId());
  }

  // case 4
  @Test
  void missingHeaderThrowsMissingHeader() {
    MPInvalidWebhookSignatureException ex = assertThrows(MPInvalidWebhookSignatureException.class,
        () -> WebhookSignatureValidator.validate(null, REQUEST_ID, DATA_ID_LOWER, SECRET));
    assertEquals(SignatureFailureReason.MISSING_SIGNATURE_HEADER, ex.getReason());
  }

  // case 5
  @Test
  void missingTsThrowsMissingTimestamp() {
    MPInvalidWebhookSignatureException ex = assertThrows(MPInvalidWebhookSignatureException.class,
        () -> WebhookSignatureValidator.validate("v1=" + validHash(), REQUEST_ID, DATA_ID_LOWER, SECRET));
    assertEquals(SignatureFailureReason.MISSING_TIMESTAMP, ex.getReason());
  }

  // case 6
  @Test
  void missingV1ThrowsMissingHash() {
    MPInvalidWebhookSignatureException ex = assertThrows(MPInvalidWebhookSignatureException.class,
        () -> WebhookSignatureValidator.validate("ts=" + TS, REQUEST_ID, DATA_ID_LOWER, SECRET));
    assertEquals(SignatureFailureReason.MISSING_HASH, ex.getReason());
    assertEquals(TS, ex.getTimestamp());
  }

  // case 7
  @Test
  void tamperedHashThrowsSignatureMismatch() {
    String h = validHash();
    String tampered = h.substring(0, h.length() - 2) + (h.endsWith("00") ? "ff" : "00");
    MPInvalidWebhookSignatureException ex = assertThrows(MPInvalidWebhookSignatureException.class,
        () -> WebhookSignatureValidator.validate(buildHeader(tampered), REQUEST_ID, DATA_ID_LOWER, SECRET));
    assertEquals(SignatureFailureReason.SIGNATURE_MISMATCH, ex.getReason());
  }

  // case 8
  @Test
  void timestampOutsideToleranceThrows() {
    String staleTs = String.valueOf(Instant.now().toEpochMilli() - 30 * 60 * 1000L);
    String h = computeHash(DATA_ID_LOWER, REQUEST_ID, staleTs, SECRET);
    MPInvalidWebhookSignatureException ex = assertThrows(MPInvalidWebhookSignatureException.class,
        () -> WebhookSignatureValidator.validate(
            buildHeader(h, staleTs), REQUEST_ID, DATA_ID_LOWER, SECRET, Duration.ofMinutes(5)));
    assertEquals(SignatureFailureReason.TIMESTAMP_OUT_OF_TOLERANCE, ex.getReason());
  }

  @Test
  void timestampWithinTolerancePasses() {
    String currentTs = String.valueOf(Instant.now().toEpochMilli());
    String h = computeHash(DATA_ID_LOWER, REQUEST_ID, currentTs, SECRET);
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(
            buildHeader(h, currentTs), REQUEST_ID, DATA_ID_LOWER, SECRET, Duration.ofMinutes(5)));
  }

  // case 9
  @Test
  void dataIdAbsentExcludesIdPair() {
    String h = computeHash(null, REQUEST_ID, TS, SECRET);
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(buildHeader(h), REQUEST_ID, null, SECRET));
  }

  // case 10
  @Test
  void requestIdAbsentExcludesRequestIdPair() {
    String h = computeHash(DATA_ID_LOWER, null, TS, SECRET);
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(buildHeader(h), null, DATA_ID_LOWER, SECRET));
  }

  // case 11
  @Test
  void bothAbsentYieldsTsOnly() {
    String h = computeHash(null, null, TS, SECRET);
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(buildHeader(h), "", "  ", SECRET));
  }

  // case 12
  @Test
  void nonPaymentTopicUsesSameAlgorithm() {
    String orderId = "ord01abc123";
    String h = computeHash(orderId, REQUEST_ID, TS, SECRET);
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(buildHeader(h), REQUEST_ID, orderId, SECRET));
  }

  @Test
  void supportsV1WhenBothPresent() {
    String header = "ts=" + TS + ",v1=" + validHash() + ",v2=aaaa";
    assertDoesNotThrow(() ->
        WebhookSignatureValidator.validate(
            header, REQUEST_ID, DATA_ID_LOWER, SECRET, null, List.of("v1"), null));
  }

  @Test
  void onlyV2InHeaderOnlyV1SupportedThrowsMissingHash() {
    String header = "ts=" + TS + ",v2=somehash";
    MPInvalidWebhookSignatureException ex = assertThrows(MPInvalidWebhookSignatureException.class,
        () -> WebhookSignatureValidator.validate(
            header, REQUEST_ID, DATA_ID_LOWER, SECRET, null, List.of("v1"), null));
    assertEquals(SignatureFailureReason.MISSING_HASH, ex.getReason());
  }

  @Test
  void nullSecretThrowsNpe() {
    assertThrows(NullPointerException.class, () ->
        WebhookSignatureValidator.validate(buildHeader(validHash()), REQUEST_ID, DATA_ID_LOWER, null));
  }
}
