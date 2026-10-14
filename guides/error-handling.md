# Handle API and client errors

`MPApiException` represents a response returned by the API. `MPException` covers client-side failures such as transport or serialization problems.

```java
try {
  Order order = client.create(request, options);
} catch (MPRateLimitException error) {
  System.err.println("Rate limited: " + error.getMessage());
} catch (MPApiException error) {
  System.err.println("Status: " + error.getStatusCode());
  System.err.println(error.getMessage());
} catch (MPException error) {
  System.err.println("SDK failure: " + error.getMessage());
}
```

Catch only the errors on which the application can make a distinct decision:

| Category | Application decision |
|---|---|
| Authentication or forbidden | Stop; correct credentials or permissions. |
| Bad request or validation | Correct the payload; an identical retry will fail. |
| Idempotency | Keep the original key and reconcile the original operation. |
| Rate limit | Respect server timing and reduce concurrency. |
| Dependency, locked resource, or server | Retry within a bounded policy. |
| `MPConnectionException` | Treat a write as potentially completed and reconcile before retrying. |

The SDK exposes typed exceptions for these common API categories, while `MPApiException` remains the common fallback. Log exception type, HTTP status, request ID when available, operation name, and an application correlation ID. Never log access tokens, card data, full payloads, or complete API responses.
