# Troubleshooting

## A request takes longer than expected

The configured timeout applies per connection attempt, while retries add delay. Review `connectionTimeout`, `maxRetries`, initial and maximum retry delays, and any outer framework retry. Keep their total below the request or job deadline.

## A write failed with a connection error

Do not assume it was rejected. Reconcile using the returned resource ID, your external reference, or another supported lookup before retrying with the same idempotency key.

## The service returns repeated `429` responses

Honor the SDK/server backoff, reduce parallel requests, and avoid an immediate application loop around the built-in retry policy. Use `onRetry` for metrics, not for issuing another request.

## A webhook signature is rejected

Verify that the raw `x-request-id` and `data.id` reach the validator unchanged, check clock synchronization, and confirm the endpoint secret. Do not route unsigned QR Code notifications through the validator.

## Escalating an issue

Include the SDK and Java versions, operation, HTTP status, request ID, sanitized exception type/message, and a minimal example. Open a repository issue without credentials, card data, webhook secrets, or exploit details.
