# Request options, retries, and idempotency

Use `MPRequestOptions` when one call needs stricter limits or a stable idempotency key without changing global client behavior:

```java
MPRequestOptions options = MPRequestOptions.builder()
    .connectionTimeout(5_000)
    .maxRetries(3)
    .customHeaders(Map.of("X-Idempotency-Key", "order-2026-0001"))
    .build();
```

By default, the SDK allows three retry attempts and a 60-second connection timeout. The retry policy covers HTTP `429`, `500`, `502`, `503`, and `504`, uses bounded exponential backoff with optional jitter, and honors `Retry-After` for rate limits. `onRetry` can emit sanitized metrics for each attempt.

Keep retry layers coordinated. If a queue or framework also retries, the combined attempt count and elapsed time can become much larger than expected. Choose an end-to-end deadline and make SDK retry delays fit inside it.

For writes, generate an idempotency key when the business operation begins, persist it beside your internal order, and reuse it only for attempts of the same payload. After a timeout or connection failure, the outcome may be unknown: query the resource using its ID or external reference before repeating the write. A new business operation requires a new key.

Do not place credentials in `customHeaders`, create a new idempotency key inside a retry callback, or retry validation and authentication failures unchanged.
