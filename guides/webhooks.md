# Validate and process webhooks

Validate signed notifications before trusting their contents:

```java
try {
  WebhookSignatureValidator.validate(
      xSignature,
      xRequestId,
      dataId,
      webhookSecret,
      Duration.ofMinutes(5));
} catch (MPInvalidWebhookSignatureException error) {
  // Reject the notification and record only sanitized diagnostics.
}
```

Pass `x-signature`, `x-request-id`, and `data.id` exactly as received. Decoding, trimming, or normalizing those values before validation can change the signed manifest. QR Code notifications are not signed and must not use this validator.

A reliable handler follows this sequence:

1. Extract the raw validation inputs.
2. Validate the signature and timestamp tolerance.
3. Deduplicate by notification or resource identifier.
4. Enqueue the identifier and acknowledge promptly.
5. Fetch the current resource from Mercado Pago in the worker.
6. Apply transitions idempotently because delivery can repeat or arrive out of order.

The notification signals that something changed; the fetched API resource is the current state. Keep system clocks synchronized and choose a tolerance that covers normal delivery latency without accepting stale replays. Never log the secret or complete signature.
