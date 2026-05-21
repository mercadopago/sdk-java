package com.mercadopago.exceptions;

import lombok.Getter;

/**
 * Exception thrown by {@link com.mercadopago.webhook.WebhookSignatureValidator}
 * when a webhook notification cannot be confirmed as originating from MercadoPago.
 *
 * <p>The exception carries enough context to support structured logging without
 * exposing internal details in the HTTP response. The {@link #getReason()}
 * indicates why validation failed, while {@link #getRequestId()} and
 * {@link #getTimestamp()} echo the inputs that were available at the point of
 * failure (when applicable) to correlate against MercadoPago's notifications
 * dashboard.
 */
@Getter
public class MPInvalidWebhookSignatureException extends MPException {

  private static final long serialVersionUID = 1L;

  private final SignatureFailureReason reason;
  private final String requestId;
  private final String timestamp;

  /**
   * Creates a new {@code MPInvalidWebhookSignatureException}.
   *
   * @param reason    the specific failure mode that triggered the exception
   * @param requestId the {@code x-request-id} header value, when available; may be {@code null}
   * @param timestamp the {@code ts} value extracted from the signature header, when
   *                  parsing reached that point; may be {@code null}
   */
  public MPInvalidWebhookSignatureException(
      SignatureFailureReason reason, String requestId, String timestamp) {
    super("Invalid webhook signature: " + reason);
    this.reason = reason;
    this.requestId = requestId;
    this.timestamp = timestamp;
  }
}
